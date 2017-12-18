package tk.aizydorczyk.excel.common.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public interface CellStyleConfigurator {
	FontStyleConfigurator fontConfig();

	CellStyleConfigurator horizontalAlignment(HorizontalAlignment alignment);

	CellStyleConfigurator verticalAlignment(VerticalAlignment alignment);

	CellStyleConfigurator borderTop(BorderStyle borderStyle);

	CellStyleConfigurator borderBottom(BorderStyle borderStyle);

	CellStyleConfigurator borderLeft(BorderStyle borderStyle);

	CellStyleConfigurator borderRight(BorderStyle borderStyle);

	CellStyleConfigurator foregroundColor(int red, int green, int blue);

	CellStyleConfigurator oldFormatForegroundColor(IndexedColors color);

	CellStyleConfigurator fillPattern(FillPatternType fillPattern);

	StyleConfigurator end();
}
