package tk.aizydorczyk.excel.writer;

import tk.aizydorczyk.excel.api.SpreadSheetWriter;
import tk.aizydorczyk.excel.writer.cells.ExcelCellsGenerator;
import tk.aizydorczyk.excel.writer.file.ExcelFileCreator;

import java.nio.file.Path;
import java.util.List;

public final class ExcelWriter implements SpreadSheetWriter {

	private final ExcelFileCreator excelFileCreator;

	private ExcelWriter(Path path) {
		this.excelFileCreator = ExcelFileCreator.ofPath(path);
	}

	public static ExcelWriter ofPath(Path path) {
		return new ExcelWriter(path);
	}

	@Override
	public void create(List<?> annotatedObjects, String sheetName) {
		final ExcelCellsGenerator excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(annotatedObjects);
		excelFileCreator.createFile(excelCellsGenerator.getDataCells(), excelCellsGenerator.getHeaders(), sheetName);
	}
}