package tk.aizydorczyk.excel.common.style;

import org.apache.poi.ss.usermodel.IndexedColors;

public interface FontStyleConfigurator {
	FontStyleConfigurator height(int height);

	FontStyleConfigurator name(String fontName);

	FontStyleConfigurator color(int red, int green, int blue);

	FontStyleConfigurator oldFormatColor(IndexedColors color);

	FontStyleConfigurator bold(boolean isBold);

	FontStyleConfigurator italic(boolean isItalic);

	FontStyleConfigurator underline(byte underline);

	CellStyleConfigurator end();
}
