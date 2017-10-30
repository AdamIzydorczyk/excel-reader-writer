package tk.aizydorczyk.writer;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tk.aizydorczyk.enums.Messages;
import tk.aizydorczyk.model.DataCell;
import tk.aizydorczyk.model.Header;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;
import static tk.aizydorczyk.enums.Messages.FILE_CREATION_FAIL;
import static tk.aizydorczyk.enums.Messages.WRONG_FILE_EXTENSION;

public class ExcelFileCreator {

	private final List<DataCell> dataCells;

	private final List<Header> headers;

	private ExcelFileCreator(List<DataCell> dataCells, List<Header> headers) {
		this.dataCells = dataCells;
		this.headers = headers;
	}

	public static ExcelFileCreator ofDataCellsAndHeaders(List<DataCell> dataCells, List<Header> headers) {
		return new ExcelFileCreator(dataCells, headers);
	}

	public void createFile(String sheetName, String path) {
		final Workbook workbook = initializeWorkbook(path);
		final Sheet sheet = workbook.createSheet(sheetName);

		final Map<Integer, Row> mapOfRows = getMapOfRows(sheet);

		headers.stream()
				.flatMap(Header::streamHeaders)
				.distinct()
				.forEach(header -> createCellByHeader(mapOfRows, header, sheet));

		dataCells.forEach(dataCell -> createCellByDataCell(mapOfRows, dataCell));

		saveFile(path, workbook);
	}

	private Workbook initializeWorkbook(String path) {
		switch (FilenameUtils.getExtension(path)) {
			case "xlsx":
				return new XSSFWorkbook();
			case "xls":
				return new HSSFWorkbook();
			default:
				throw new ExcelFileCreateFail(WRONG_FILE_EXTENSION);
		}
	}

	private void saveFile(String path, Workbook workbook) {
		try {
			final FileOutputStream outputStream = new FileOutputStream(path);
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			throw new ExcelFileCreateFail(FILE_CREATION_FAIL);
		}
	}

	private void createCellByHeader(Map<Integer, Row> mapOfRows, Header header, Sheet sheet) {
		final Row row = mapOfRows.get(toIntExact(header.getRowPosition()));
		final Cell cell = row.createCell(toIntExact(header.getStartColumnPosition()));
		cell.setCellValue(header.getHeaderName());

		if (header.getStartColumnPosition() != header.getEndColumnPosition()) {
			sheet.addMergedRegion(new CellRangeAddress(
					toIntExact(header.getRowPosition()),
					toIntExact(header.getRowPosition()),
					toIntExact(header.getStartColumnPosition()),
					toIntExact(header.getEndColumnPosition()))
			);
		}
	}

	private void createCellByDataCell(Map<Integer, Row> mapOfRows, DataCell dataCell) {
		final Row row = mapOfRows.get(toIntExact(toIntExact(dataCell.getRowPosition())));
		final Cell cell = row.createCell(toIntExact(dataCell.getColumnPosition()));
		cell.setCellValue(dataCell.getData().toString());
	}

	private Map<Integer, Row> getMapOfRows(Sheet sheet) {
		return Stream.concat(
				dataCells.stream().map(DataCell::getRowPosition),
				headers.stream().map(Header::getRowPosition)
		).distinct()
				.map(rowPosition -> sheet.createRow(toIntExact(rowPosition)))
				.collect(Collectors.toMap(Row::getRowNum, Function.identity()));
	}

	private class ExcelFileCreateFail extends RuntimeException {
		public ExcelFileCreateFail(Messages message) {
			super(message.getMessage());
		}
	}

}
