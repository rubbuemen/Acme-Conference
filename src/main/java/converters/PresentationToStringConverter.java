
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Presentation;

@Component
@Transactional
public class PresentationToStringConverter implements Converter<Presentation, String> {

	@Override
	public String convert(final Presentation presentation) {
		String result;

		if (presentation == null)
			result = null;
		else
			result = String.valueOf(presentation.getId());
		return result;
	}

}
