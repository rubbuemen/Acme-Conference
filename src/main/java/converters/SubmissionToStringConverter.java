
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Submission;

@Component
@Transactional
public class SubmissionToStringConverter implements Converter<Submission, String> {

	@Override
	public String convert(final Submission submission) {
		String result;

		if (submission == null)
			result = null;
		else
			result = String.valueOf(submission.getId());
		return result;
	}

}
