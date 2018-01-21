package tk.aizydorczyk.excel.writer.file;

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
import tk.aizydorczyk.excel.common.exceptions.ExcelWriterException;
import tk.aizydorczyk.excel.common.messages.Messages;
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
import static tk.aizydorczyk.excel.common.messages.Messages.FILE_CREATION_FAIL;
import static tk.aizydorczyk.excel.common.messages.Messages.WRONG_FILE_EXTENSION;
import static tk.aizydorczyk.excel.common.model.Style.DEFAULT_HEADER_STYLE;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getExtension;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.orDefaultParameter;

public final class ExcelFileCreator {

	private final Workbook workbook;

	private final Path path;

	private final StyleCreator styleCreator;

	private ExcelFileCreator(Path path) {
		this.workbook = initializeWorkbook(path);
		this.path = path;
		this.styleCreator = new StyleCreator(workbook);
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
				.forEach(sheet::autoSizeColumn);
	}

	private Workbook initializeWorkbook(Path path) {
		String extension = getExtension(path.toString());
		return ExtensionType.getWorkbookByExtension(extension)
				.orElseThrow(() -> new ExcelFileCreateFail(WRONG_FILE_EXTENSION));
	}

	private void saveFile() {
		try (FileOutputStream outputStream = new FileOutputStream(path.toString())) {
			workbook.write(outputStream);
		} catch (IOException ex) {
			throw new ExcelFileCreateFail(FILE_CREATION_FAIL, ex);
		}
	}

	private void createCellByHeader(Map<Integer, Row> mapOfRows, Header header, Sheet sheet) {
		final Row row = mapOfRows.get(header.getRowPosition());
		final Cell cell = row.createCell(header.getStartColumnPosition());

		cell.setCellValue(header.getHeaderName());
		cell.setCellStyle(styleCreator.createStyle(header.getStyle()));

		if (header.getStartColumnPosition() != header.getEndColumnPosition()) {
			sheet.addMergedRegion(new CellRangeAddress(
					header.getRowPosition(),
					header.getRowPosition(),
					header.getStartColumnPosition(),
					header.getEndColumnPosition())
			);
		}
	}

	private void createCellByDataCell(Map<Integer, Row> mapOfRows, DataCell dataCell) {
		final Row row = mapOfRows.get(dataCell.getRowPosition());
		final Cell cell = row.createCell(dataCell.getColumnPosition());
		final Style dataCellsStyle = dataCell.getStyle();
		cell.setCellStyle(styleCreator.createStyle(dataCellsStyle));
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

	private final class ExcelFileCreateFail extends ExcelWriterException {
		ExcelFileCreateFail(Messages message, IOException ex) {
			super(ex, message.getMessage());
		}

		ExcelFileCreateFail(Messages messages) {
			super(messages.getMessage());
		}
	}

}