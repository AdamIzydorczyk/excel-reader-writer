package tk.aizydorczyk.excel.writer.cells;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import tk.aizydorczyk.excel.common.model.Style;
import tk.aizydorczyk.excel.common.style.CellStyleConfigurator;
import tk.aizydorczyk.excel.common.style.FontStyleConfigurator;
import tk.aizydorczyk.excel.common.style.StyleConfigurator;

import java.awt.Color;

final class ExcelCellStyleConfigurator implements CellStyleConfigurator {

	private final StyleConfigurator baseStyleConfigurator;
	private final Style style;

	ExcelCellStyleConfigurator(ExcelStyleConfigurator excelStyleConfigurator, Style style) {
		this.baseStyleConfigurator = excelStyleConfigurator;
		this.style = style;
	}

	@Override
	public FontStyleConfigurator fontConfig() {
		return new ExcelFontStyleConfigurator(this, style);
	}

	@Override
	public CellStyleConfigurator horizontalAlignment(HorizontalAlignment alignment) {
		style.setHorizontalAlignment(alignment);
		return this;
	}

	@Override
	public CellStyleConfigurator verticalAlignment(VerticalAlignment alignment) {
		style.setVerticalAlignment(alignment);
		return this;
	}

	@Override
	public CellStyleConfigurator borderTop(BorderStyle borderStyle) {
		style.setBorderTop(borderStyle);
		return this;
	}

	@Override
	public CellStyleConfigurator borderBottom(BorderStyle borderStyle) {
		style.setBorderBottom(borderStyle);
		return this;
	}

	@Override
	public CellStyleConfigurator borderLeft(BorderStyle borderStyle) {
		style.setBorderLeft(borderStyle);
		return this;
	}

	@Override
	public CellStyleConfigurator borderRight(BorderStyle borderStyle) {
		style.setBorderRight(borderStyle);
		return this;
	}

	@Override
	public CellStyleConfigurator foregroundColor(int red, int green, int blue) {
		style.setForegroundColor(new Color(red, green, blue));
		return this;
	}

	@Override
	public CellStyleConfigurator oldFormatForegroundColor(IndexedColors color) {
		style.setOldFormatForegroundColorIndex(color.getIndex());
		return this;
	}

	@Override
	public CellStyleConfigurator fillPattern(FillPatternType fillPattern) {
		style.setFillPattern(fillPattern);
		return this;
	}

	@Override
	public StyleConfigurator end() {
		return baseStyleConfigurator;
	}
}