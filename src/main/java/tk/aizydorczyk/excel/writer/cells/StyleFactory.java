package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.api.SpreadSheetStyle;
import tk.aizydorczyk.excel.common.exceptions.ExcelWriterException;
import tk.aizydorczyk.excel.common.messages.Messages;
import tk.aizydorczyk.excel.common.model.Style;

import java.util.HashMap;
import java.util.Map;

import static tk.aizydorczyk.excel.common.messages.Messages.STYLE_INITIALIZATION_FAIL;
import static tk.aizydorczyk.excel.common.model.Style.DEFAULT_HEADER_STYLE;
import static tk.aizydorczyk.excel.common.utility.ExceptionHelper.getOrRethrowException;

final class StyleFactory {

	private final Map<Class<? extends SpreadSheetStyle>, Style> stylesMap;

	StyleFactory() {
		stylesMap = new HashMap<>();
		stylesMap.put(SpreadSheetStyle.class, DEFAULT_HEADER_STYLE);
	}

	Style getStyle(Class<? extends SpreadSheetStyle> styleClass) {
		return stylesMap.computeIfAbsent(styleClass, this::createStyle);
	}

	private Style createStyle(Class<? extends SpreadSheetStyle> styleClass) {
		final SpreadSheetStyle spreadSheetStyle = instantiateStyle(styleClass);
		final ExcelStyleConfigurator excelStyleConfigurator = new ExcelStyleConfigurator();

		spreadSheetStyle.configureStyle(excelStyleConfigurator);

		return excelStyleConfigurator.getStyle();
	}

	private SpreadSheetStyle instantiateStyle(Class<? extends SpreadSheetStyle> styleClass) {
		return getOrRethrowException(
				styleClass::newInstance,
				() -> new StyleFactoryException(STYLE_INITIALIZATION_FAIL)
		);
	}

	private final class StyleFactoryException extends ExcelWriterException {
		StyleFactoryException(Messages message) {
			super(message.getMessage());
		}
	}
}
