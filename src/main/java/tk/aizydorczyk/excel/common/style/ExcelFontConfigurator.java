package tk.aizydorczyk.excel.common.style;

import org.apache.poi.ss.usermodel.IndexedColors;
import tk.aizydorczyk.excel.common.model.Style;

import java.awt.Color;

public class ExcelFontConfigurator {

	private Style style;
	private ExcelCellStyleConfigurator headerStyleConfigurator;

	public ExcelFontConfigurator(ExcelCellStyleConfigurator cellsStyleConfigurator, Style style) {
		this.style = style;
		this.headerStyleConfigurator = cellsStyleConfigurator;
	}

	public ExcelFontConfigurator height(int height) {
		style.setFontHeight((short) height);
		return this;
	}
	
	public ExcelFontConfigurator name(String fontName) {
		style.setFontName(fontName);
		return this;
	}
	
	public ExcelFontConfigurator color(int red, int green, int blue) {
		style.setFontColor(new Color(red, green, blue));
		return this;
	}
	
	public ExcelFontConfigurator oldFormatColor(IndexedColors color) {
		style.setOldFormatFontColorIndex(color.getIndex());
		return this;
	}
	
	public ExcelFontConfigurator bold(boolean isBold) {
		style.setIsFontBold(isBold);
		return this;
	}
	
	public ExcelFontConfigurator italic(boolean isItalic) {
		style.setIsFontItalic(isItalic);
		return this;
	}
	
	public ExcelFontConfigurator underline(byte underline) {
		style.setFontUnderline(underline);
		return this;
	}


	public ExcelCellStyleConfigurator end() {
		return headerStyleConfigurator;
	}

}
