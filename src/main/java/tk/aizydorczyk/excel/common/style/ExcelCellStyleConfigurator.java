package tk.aizydorczyk.excel.common.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import tk.aizydorczyk.excel.common.model.Style;

import java.awt.Color;

public class ExcelCellStyleConfigurator {

	private ExcelStyleConfigurator baseStyleConfigurator;
	private ExcelFontConfigurator fontConfigurator;

	private Style style;
	
	public ExcelCellStyleConfigurator(ExcelStyleConfigurator excelStyleConfigurator, Style style) {
		this.baseStyleConfigurator = excelStyleConfigurator;
		this.style = style;
	}
	
	public ExcelFontConfigurator fontConfig() {
		fontConfigurator = new ExcelFontConfigurator(this, style);
		return fontConfigurator;
	}
	
	public ExcelCellStyleConfigurator horizontalAlignment(HorizontalAlignment alignment) {
		style.setHorizontalAlignment(alignment);
		return this;
	}
	
	public ExcelCellStyleConfigurator verticalAlignment(VerticalAlignment alignment) {
		style.setVerticalAlignment(alignment);
		return this;
	}
	
	public ExcelCellStyleConfigurator borderTop(BorderStyle borderStyle) {
		style.setBorderTop(borderStyle);
		return this;
	}
	
	public ExcelCellStyleConfigurator borderBottom(BorderStyle borderStyle) {
		style.setBorderBottom(borderStyle);
		return this;
	}
	
	public ExcelCellStyleConfigurator borderLeft(BorderStyle borderStyle) {
		style.setBorderLeft(borderStyle);
		return this;
	}
	
	public ExcelCellStyleConfigurator borderRight(BorderStyle borderStyle) {
		style.setBorderRight(borderStyle);
		return this;
	}
	
	public ExcelCellStyleConfigurator foregroundColor(int red, int green, int blue) {
		style.setForegroundColor(new Color(red, green, blue));
		return this;
	}
	
	public ExcelCellStyleConfigurator oldFormatForegroundColor(IndexedColors color) {
		style.setOldFormatForegroundColorIndex(color.getIndex());
		return this;
	}
	
	public ExcelCellStyleConfigurator fillPattern(FillPatternType fillPattern) {
		style.setFillPattern(fillPattern);
		return this;
	}
	
	public ExcelStyleConfigurator end() {
		return baseStyleConfigurator;
	}
}
