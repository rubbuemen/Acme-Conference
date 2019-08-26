
package services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.Message;
import domain.Paper;
import domain.Submission;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	// Supporting services
	@Autowired
	private ActorService					actorService;

	@Autowired
	private SubmissionService				submissionService;

	@Autowired
	private MessageService					messageService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private TopicService					topicService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private PaperService					paperService;


	// Simple CRUD methods
	public SystemConfiguration create() {
		SystemConfiguration result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new SystemConfiguration();

		return result;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		Assert.isTrue(systemConfigurationId != 0);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	// Other business methods
	public SystemConfiguration getConfiguration() {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.getConfiguration();
		Assert.notNull(result);

		return result;
	}

	//R14.5
	public void notificationProcedure() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Submission> submissionsAcceptedOrRejectedNotNotifiedNoDeadline = this.submissionService.findSubmissionsAcceptedOrRejectedNotNotifiedNoDeadline();

		for (final Submission s : submissionsAcceptedOrRejectedNotNotifiedNoDeadline) {
			final Author author = this.authorService.findAuthorBySubmissionId(s.getId());
			final Message result = this.messageService.create();
			final Actor system = this.actorService.getSystemActor();
			Topic topic = this.topicService.findTopicOther();
			if (topic == null) {
				topic = this.topicService.create();
				topic.setNameEnglish("OTHER");
				topic.setNameSpanish("OTRO");
				topic = this.topicService.save(topic);
			}
			result.setSubject("Your submission has been reviewed");
			result.setBody("We inform you that the submission with ticker " + s.getTicker() + " has been reviewed and its status has been '" + s.getStatus() + "'. You can see the reports in the corresponding section.");
			result.setTopic(topic);
			result.setSender(system);
			result.getRecipients().add(author);
			this.messageService.save(result);
			s.setIsNotified(true);
			this.submissionService.saveAuxiliar(s);
		}
	}

	private Pattern patternWords(final Collection<String> words) {
		String pattern = "";
		for (final String w : words)
			pattern = pattern + (w + "|");
		pattern = pattern.substring(0, pattern.length() - 1);
		final Pattern result = Pattern.compile(pattern);
		return result;
	}

	//R31.1
	public Map<Author, Double> mapAuthorScore() {
		final Map<Author, Double> result = new HashMap<>();

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		//Búsqueda de palabras buzz
		final Collection<Conference> conferences = this.conferenceService.findConferencesLastYearAndFuture();

		final Map<String, Integer> wordFrecuency = new HashMap<>();
		final Collection<String> voidWords = this.getConfiguration().getVoidWords();

		for (final Conference c : conferences) {
			final String contentConference = c.getTitle() + " " + c.getSummary();
			final List<String> nonVoidWordsConference = new ArrayList<>();
			for (String s : contentConference.split(" ")) {
				s = s.toLowerCase();
				final String firstCharacter = s.substring(0);
				final String lastCharacter = s.substring(s.length() - 1);
				if (firstCharacter.equals(",") || firstCharacter.equals(".") || firstCharacter.equals(":") || firstCharacter.equals(";") || firstCharacter.equals("?") || firstCharacter.equals("!") || firstCharacter.equals("¡")
					|| firstCharacter.equals("¿") || firstCharacter.equals("'") || firstCharacter.equals("\""))
					s = s.substring(1);
				if (lastCharacter.equals(",") || lastCharacter.equals(".") || lastCharacter.equals(":") || lastCharacter.equals(";") || lastCharacter.equals("?") || lastCharacter.equals("!") || firstCharacter.equals("¡") || firstCharacter.equals("¿")
					|| firstCharacter.equals("'") || firstCharacter.equals("\""))
					s = s.substring(0, s.length() - 1);
				if (!voidWords.contains(s))
					nonVoidWordsConference.add(s);
			}
			for (final String word : nonVoidWordsConference)
				if (wordFrecuency.containsKey(word)) {
					final Integer scoreWord = wordFrecuency.get(word);
					wordFrecuency.put(word, scoreWord + 1);
				} else
					wordFrecuency.put(word, 1);
		}

		Double maxFrecuency = 0.0;

		if (!wordFrecuency.values().isEmpty()) {
			maxFrecuency = Collections.max(wordFrecuency.values()).doubleValue();
			maxFrecuency = maxFrecuency - 0.20 * maxFrecuency;
		}

		final Collection<String> buzzWords = new HashSet<>();
		for (final String s : wordFrecuency.keySet())
			if (wordFrecuency.get(s) >= maxFrecuency)
				buzzWords.add(s);

		//Puntuaciones
		final Collection<Paper> papers = this.paperService.findPapersCameraReady();
		final Map<Author, Integer> authorPoints = new HashMap<>();
		Matcher matchBuzzWordTitle;
		Matcher matchBuzzWordSummary;

		for (final Paper p : papers) {
			Integer countScoreWords = 0;
			final Author author = this.authorService.findAuthorByPaper(p);
			matchBuzzWordTitle = this.patternWords(buzzWords).matcher(p.getTitle().toLowerCase());
			matchBuzzWordSummary = this.patternWords(buzzWords).matcher(p.getSummary().toLowerCase());
			while (matchBuzzWordTitle.find())
				countScoreWords++;
			while (matchBuzzWordSummary.find())
				countScoreWords++;
			if (authorPoints.containsKey(author)) {
				final Integer scoreWord = authorPoints.get(author);
				authorPoints.put(author, scoreWord + countScoreWords);
			} else
				authorPoints.put(author, countScoreWords);
		}

		Double maxScoreActors = 0.0;

		if (!authorPoints.values().isEmpty())
			maxScoreActors = Collections.max(authorPoints.values()).doubleValue();

		for (final Author a : authorPoints.keySet()) {
			final Double scoreActor = authorPoints.get(a).doubleValue();
			Double finalScore = 0.0;
			if (maxScoreActors != 0.0)
				finalScore = scoreActor / maxScoreActors;
			final DecimalFormat formatDecimals = new DecimalFormat(".##");
			final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			formatDecimals.setDecimalFormatSymbols(dfs);
			finalScore = Double.valueOf(formatDecimals.format(finalScore));
			result.put(a, finalScore);
		}
		return result;
	}

	//R31.1
	public void computeScore() {
		final Map<Author, Double> scoresMap = this.mapAuthorScore();

		for (final Author a : scoresMap.keySet()) {
			a.setScore(scoresMap.get(a));
			this.authorService.saveAuxiliar(a);
		}
	}
}
