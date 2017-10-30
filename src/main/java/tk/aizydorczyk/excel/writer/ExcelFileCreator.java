package tk.aizydorczyk.excel.writer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tk.aizydorczyk.excel.common.enums.Messages;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tk.aizydorczyk.excel.common.enums.Messages.WRONG_FILE_EXTENSION;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getExtension;

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

		saveFile();
	}

	private Workbook initializeWorkbook(Path path) {
		switch (getExtension(path.toString())) {
			case "xlsx":
				return new XSSFWorkbook();
			case "xls":
				return new HSSFWorkbook();
			default:
				throw new ExcelFileCreateFail(WRONG_FILE_EXTENSION);
		}
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
		// TODO: cell stylization
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
