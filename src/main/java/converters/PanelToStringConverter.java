
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Panel;

@Component
@Transactional
public class PanelToStringConverter implements Converter<Panel, String> {

	@Override
	public String convert(final Panel panel) {
		String result;

		if (panel == null)
			result = null;
		else
			result = String.valueOf(panel.getId());
		return result;
	}

}
