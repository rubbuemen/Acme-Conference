
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Conference;

@Component
@Transactional
public class ConferenceToStringConverter implements Converter<Conference, String> {

	@Override
	public String convert(final Conference conference) {
		String result;

		if (conference == null)
			result = null;
		else
			result = String.valueOf(conference.getId());
		return result;
	}

}
