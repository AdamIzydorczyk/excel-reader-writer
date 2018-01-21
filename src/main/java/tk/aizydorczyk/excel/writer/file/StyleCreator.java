package tk.aizydorczyk.excel.writer.file;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import tk.aizydorczyk.excel.common.model.Style;

import static java.util.Objects.isNull;
import static tk.aizydorczyk.excel.common.model.Style.DEFAULT_HEADER_STYLE;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.orDefaultParameter;

class StyleCreator {

	private final Workbook workbook;

	StyleCreator(Workbook workbook) {
		this.workbook = workbook;
	}

	CellStyle createStyle(Style style) {
		final CellStyle cellStyle = workbook.createCellStyle();

		if (isNull(style)) {
			style = DEFAULT_HEADER_STYLE;
		}

		setForegroundColor(style, cellStyle);

		cellStyle.setAlignment(
				orDefaultParameter(style.getHorizontalAlignment(), DEFAULT_HEADER_STYLE.getHorizontalAlignment()));
		cellStyle.setVerticalAlignment(
				orDefaultParameter(style.getVerticalAlignment(), DEFAULT_HEADER_STYLE.getVerticalAlignment()));

		cellStyle.setFont(createFont(style));

		cellStyle.setBorderTop(
				orDefaultParameter(style.getBorderTop(), DEFAULT_HEADER_STYLE.getBorderTop()));
		cellStyle.setBorderBottom(
				orDefaultParameter(style.getBorderBottom(), DEFAULT_HEADER_STYLE.getBorderBottom()));
		cellStyle.setBorderLeft(
				orDefaultParameter(style.getBorderLeft(), DEFAULT_HEADER_STYLE.getBorderLeft()));
		cellStyle.setBorderRight(
				orDefaultParameter(style.getBorderRight(), DEFAULT_HEADER_STYLE.getBorderRight()));
		cellStyle.setFillPattern(
				orDefaultParameter(style.getFillPattern(), DEFAULT_HEADER_STYLE.getFillPattern()));

		return cellStyle;
	}

	private Font createFont(Style style) {
		final Font font = workbook.createFont();

		font.setFontHeightInPoints(
				orDefaultParameter(style.getFontHeight(), DEFAULT_HEADER_STYLE.getFontHeight()));
		font.setFontName(
				orDefaultParameter(style.getFontName(), DEFAULT_HEADER_STYLE.getFontName()));
		font.setUnderline(
				orDefaultParameter(style.getFontUnderline(), DEFAULT_HEADER_STYLE.getFontUnderline()));

		setHeaderFontColor(style, font);

		font.setBold(
				orDefaultParameter(style.getIsFontBold(), DEFAULT_HEADER_STYLE.getIsFontBold()));
		font.setItalic(
				orDefaultParameter(style.getIsFontItalic(), DEFAULT_HEADER_STYLE.getIsFontItalic()));
		font.setUnderline(
				orDefaultParameter(style.getFontUnderline(), DEFAULT_HEADER_STYLE.getFontUnderline()));

		return font;
	}

	private void setForegroundColor(Style style, CellStyle cellStyle) {
		if (cellStyle instanceof XSSFCellStyle) {
			XSSFColor color = new XSSFColor(
					orDefaultParameter(style.getForegroundColor(), DEFAULT_HEADER_STYLE.getForegroundColor()));
			((XSSFCellStyle) cellStyle).setFillForegroundColor(color);
		} else {
			cellStyle.setFillForegroundColor(
					orDefaultParameter(style.getOldFormatForegroundColorIndex(), DEFAULT_HEADER_STYLE.getOldFormatForegroundColorIndex()));
		}
	}

	private void setHeaderFontColor(Style style, Font font) {
		if (font instanceof XSSFFont) {
			XSSFColor color = new XSSFColor(orDefaultParameter(style.getFontColor(), DEFAULT_HEADER_STYLE.getFontColor()));
			((XSSFFont) font).setColor(color);
		} else {
			font.setColor(orDefaultParameter(style.getOldFormatFontColorIndex(), DEFAULT_HEADER_STYLE.getOldFormatFontColorIndex()));
		}
	}
}
