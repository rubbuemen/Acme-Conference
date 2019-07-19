
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Topic;

@Component
@Transactional
public class TopicToStringConverter implements Converter<Topic, String> {

	@Override
	public String convert(final Topic topic) {
		String result;

		if (topic == null)
			result = null;
		else
			result = String.valueOf(topic.getId());
		return result;
	}

}
