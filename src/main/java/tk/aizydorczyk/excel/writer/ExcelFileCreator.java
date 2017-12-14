package tk.aizydorczyk.excel.writer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import tk.aizydorczyk.excel.common.enums.ExtensionType;
import tk.aizydorczyk.excel.common.enums.Messages;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;
import tk.aizydorczyk.excel.common.model.Style;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static tk.aizydorczyk.excel.common.enums.Messages.WRONG_FILE_EXTENSION;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getExtension;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.orDefaultParameter;

public class ExcelFileCreator {

	private final Workbook workbook;

	private final Path path;

	private ExcelFileCreator(Path path) {
		this.workbook = initializeWorkbook(path);
		this.path = path;
	}

	public static ExcelFileCreator ofPath(Path path) {
		return new ExcelFileCreator(path);
	}

	public void createFile(List<DataCell> dataCells, List<Header> headers, String sheetName) {
		final Sheet sheet = workbook.createSheet(sheetName);
		final Map<Integer, Row> mapOfRows = getMapOfRows(sheet, dataCells, headers);

		headers.stream()
				.flatMap(Header::streamHeaders)
				.distinct()
				.forEach(header ->
						createCellByHeader(mapOfRows, header, sheet));

		dataCells.forEach(dataCell ->
				createCellByDataCell(mapOfRows, dataCell));

		resizeColumns(sheet, headers);

		saveFile();
	}

	private void resizeColumns(Sheet sheet, List<Header> headers) {
		headers.stream()
				.filter(Header::isOverData)
				.map(Header::getStartColumnPosition)
				.forEach(colNumber -> sheet.autoSizeColumn(colNumber));
	}

	private Workbook initializeWorkbook(Path path) {
		String extension = getExtension(path.toString());
		return ExtensionType.getWorkbookByExtension(extension)
				.orElseThrow(() ->  new ExcelFileCreateFail(WRONG_FILE_EXTENSION));
	}

	private void saveFile() {
		try {
			final FileOutputStream outputStream = new FileOutputStream(path.toString());
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException ex) {
			throw new ExcelFileCreateFail(ex);
		}
	}

	private void createCellByHeader(Map<Integer, Row> mapOfRows, Header header, Sheet sheet) {
		final Row row = mapOfRows.get(header.getRowPosition());
		final Cell cell = row.createCell(header.getStartColumnPosition());

		cell.setCellValue(header.getHeaderName());
		cell.setCellStyle(createStyle(header.getStyle()));

		if (header.getStartColumnPosition() != header.getEndColumnPosition()) {
			sheet.addMergedRegion(new CellRangeAddress(
					header.getRowPosition(),
					header.getRowPosition(),
					header.getStartColumnPosition(),
					header.getEndColumnPosition())
			);
		}
	}

	private CellStyle createStyle(Style style) {
		final CellStyle cellStyle = workbook.createCellStyle();

		if (isNull(style)) {
			style = Style.DEFAULT_HEADER_STYLE;
		}

		setForegroundColor(style, cellStyle);

		cellStyle.setAlignment(
				orDefaultParameter(style.getHorizontalAlignment(), Style.DEFAULT_HEADER_STYLE.getHorizontalAlignment()));
		cellStyle.setVerticalAlignment(
				orDefaultParameter(style.getVerticalAlignment(), Style.DEFAULT_HEADER_STYLE.getVerticalAlignment()));

		cellStyle.setFont(createFont(style));

		cellStyle.setBorderTop(
				orDefaultParameter(style.getBorderTop(), Style.DEFAULT_HEADER_STYLE.getBorderTop()));
		cellStyle.setBorderBottom(
				orDefaultParameter(style.getBorderBottom(), Style.DEFAULT_HEADER_STYLE.getBorderBottom()));
		cellStyle.setBorderLeft(
				orDefaultParameter(style.getBorderLeft(), Style.DEFAULT_HEADER_STYLE.getBorderLeft()));
		cellStyle.setBorderRight(
				orDefaultParameter(style.getBorderRight(), Style.DEFAULT_HEADER_STYLE.getBorderRight()));
		cellStyle.setFillPattern(
				orDefaultParameter(style.getFillPattern(), Style.DEFAULT_HEADER_STYLE.getFillPattern()));

		return cellStyle;
	}


	private Font createFont(Style style) {
		final Font font = workbook.createFont();

		font.setFontHeightInPoints(
				orDefaultParameter(style.getFontHeight(), Style.DEFAULT_HEADER_STYLE.getFontHeight()));
		font.setFontName(
				orDefaultParameter(style.getFontName(), Style.DEFAULT_HEADER_STYLE.getFontName()));
		font.setUnderline(
				orDefaultParameter(style.getFontUnderline(), Style.DEFAULT_HEADER_STYLE.getFontUnderline()));

		setHeaderFontColor(style, font);

		font.setBold(
				orDefaultParameter(style.getIsFontBold(), Style.DEFAULT_HEADER_STYLE.getIsFontBold()));
		font.setItalic(
				orDefaultParameter(style.getIsFontItalic(), Style.DEFAULT_HEADER_STYLE.getIsFontItalic()));
		font.setUnderline(
				orDefaultParameter(style.getFontUnderline(), Style.DEFAULT_HEADER_STYLE.getFontUnderline()));

		return font;
	}

	private void setForegroundColor(Style style, CellStyle cellStyle) {
		if (cellStyle instanceof XSSFCellStyle) {
			XSSFColor color = new XSSFColor(
					orDefaultParameter(style.getForegroundColor(), Style.DEFAULT_HEADER_STYLE.getForegroundColor()));
			((XSSFCellStyle) cellStyle).setFillForegroundColor(color);
		} else {
			cellStyle.setFillForegroundColor(
					orDefaultParameter(style.getOldFormatForegroundColorIndex(), Style.DEFAULT_HEADER_STYLE.getOldFormatForegroundColorIndex()));
		}
	}

	private void setHeaderFontColor(Style style, Font font) {
		if (font instanceof XSSFFont) {
			XSSFColor color = new XSSFColor(orDefaultParameter(style.getFontColor(), Style.DEFAULT_HEADER_STYLE.getFontColor()));
			((XSSFFont) font).setColor(color);
		} else {
			font.setColor(orDefaultParameter(style.getOldFormatFontColorIndex(), Style.DEFAULT_HEADER_STYLE.getOldFormatFontColorIndex()));
		}
	}

	private void createCellByDataCell(Map<Integer, Row> mapOfRows, DataCell dataCell) {
		final Row row = mapOfRows.get(dataCell.getRowPosition());
		final Cell cell = row.createCell(dataCell.getColumnPosition());
		final Style dataCellsStyle = dataCell.getHeader().getStyle().getDataCellsStyle();
		cell.setCellStyle(createStyle(dataCellsStyle));
		cell.setCellValue(String.valueOf(dataCell.getData()));    // TODO: cell data type classification
	}

	private Map<Integer, Row> getMapOfRows(Sheet sheet, List<DataCell> dataCells, List<Header> headers) {
		return Stream.concat(
				dataCells.stream().map(DataCell::getRowPosition),
				headers.stream().map(Header::getRowPosition))
				.distinct()
				.map(sheet::createRow)
				.collect(Collectors.toMap(Row::getRowNum, Function.identity()));
	}

	private class ExcelFileCreateFail extends RuntimeException {
		public ExcelFileCreateFail(Messages message) {
			super(message.getMessage());
		}

		public ExcelFileCreateFail(Exception ex) {
			super(ex);
		}
	}

}
