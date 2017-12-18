package tk.aizydorczyk.excel.common.model.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import tk.aizydorczyk.excel.api.SpreadSheetStyle;
import tk.aizydorczyk.excel.common.style.StyleConfigurator;

public class NonDataHeaderStyle implements SpreadSheetStyle {

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
				.foregroundColor(255, 175, 175)
				.oldFormatForegroundColor(IndexedColors.ORANGE)
				.fontConfig()
				.color(100, 100, 100)
				.oldFormatColor(IndexedColors.GREY_50_PERCENT)
				.height(14)
				.name("Arial")
				.bold(true)
				.italic(false)
				.underline(Font.U_SINGLE)
				.end()
				.end()
				.end();
	}
}
