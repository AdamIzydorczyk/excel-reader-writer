package tk.aizydorczyk.excel.writer.cells;

import org.apache.poi.ss.usermodel.IndexedColors;
import tk.aizydorczyk.excel.common.model.Style;
import tk.aizydorczyk.excel.common.style.CellStyleConfigurator;
import tk.aizydorczyk.excel.common.style.FontStyleConfigurator;

import java.awt.Color;

final class ExcelFontStyleConfigurator implements FontStyleConfigurator {

	private final Style style;
	private final ExcelCellStyleConfigurator headerStyleConfigurator;

	public ExcelFontStyleConfigurator(ExcelCellStyleConfigurator cellsStyleConfigurator, Style style) {
		this.style = style;
		this.headerStyleConfigurator = cellsStyleConfigurator;
	}

	@Override
	public FontStyleConfigurator height(int height) {
		style.setFontHeight((short) height);
		return this;
	}

	@Override
	public FontStyleConfigurator name(String fontName) {
		style.setFontName(fontName);
		return this;
	}

	@Override
	public FontStyleConfigurator color(int red, int green, int blue) {
		style.setFontColor(new Color(red, green, blue));
		return this;
	}

	@Override
	public FontStyleConfigurator oldFormatColor(IndexedColors color) {
		style.setOldFormatFontColorIndex(color.getIndex());
		return this;
	}

	@Override
	public FontStyleConfigurator bold(boolean isBold) {
		style.setIsFontBold(isBold);
		return this;
	}

	@Override
	public FontStyleConfigurator italic(boolean isItalic) {
		style.setIsFontItalic(isItalic);
		return this;
	}

	@Override
	public FontStyleConfigurator underline(byte underline) {
		style.setFontUnderline(underline);
		return this;
	}


	@Override
	public CellStyleConfigurator end() {
		return headerStyleConfigurator;
	}
}