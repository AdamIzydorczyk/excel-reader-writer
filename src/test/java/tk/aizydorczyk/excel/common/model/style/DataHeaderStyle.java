package tk.aizydorczyk.excel.common.model.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import tk.aizydorczyk.excel.api.SpreadSheetStyle;
import tk.aizydorczyk.excel.common.style.StyleConfigurator;

public class DataHeaderStyle implements SpreadSheetStyle {

	@Override
	public void configureStyle(StyleConfigurator config) {
		config
				.headerConfig()
				.horizontalAlignment(HorizontalAlignment.CENTER)
				.verticalAlignment(VerticalAlignment.CENTER)
				.borderTop(BorderStyle.DOTTED)
				.borderBottom(BorderStyle.DOTTED)
				.borderLeft(BorderStyle.DOTTED)
				.borderRight(BorderStyle.DOTTED)
				.fillPattern(FillPatternType.SOLID_FOREGROUND)
				.foregroundColor(200, 255, 200)
				.oldFormatForegroundColor(IndexedColors.ORANGE)
				.fontConfig()
				.oldFormatColor(IndexedColors.GREY_50_PERCENT)
				.height(12)
				.name("Arial")
				.bold(true)
				.italic(false)
				.end()
				.end()
				.dataCellsConfig()
				.horizontalAlignment(HorizontalAlignment.LEFT)
				.verticalAlignment(VerticalAlignment.CENTER)
				.borderTop(BorderStyle.DOTTED)
				.borderBottom(BorderStyle.DOTTED)
				.borderLeft(BorderStyle.DOTTED)
				.borderRight(BorderStyle.DOTTED)
				.fillPattern(FillPatternType.SOLID_FOREGROUND)
				.foregroundColor(250, 250, 200)
				.oldFormatForegroundColor(IndexedColors.ORANGE)
				.fontConfig()
				.oldFormatColor(IndexedColors.GREY_50_PERCENT)
				.height(10)
				.name("Arial")
				.italic(true)
				.end()
				.end();
	}
}
