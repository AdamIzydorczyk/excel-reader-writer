package tk.aizydorczyk.excel.api;

import tk.aizydorczyk.excel.writer.ExcelCellsGenerator;
import tk.aizydorczyk.excel.writer.ExcelFileCreator;

import java.nio.file.Path;
import java.util.List;

public class ExcelWriter {

	private final ExcelFileCreator excelFileCreator;

	private ExcelWriter(Path path) {
		this.excelFileCreator = ExcelFileCreator.ofPath(path);
	}

	public static ExcelWriter ofPath(Path path) {
		return new ExcelWriter(path);
	}

	public void create(List<?> annotatedObjects, String sheetName) {
		final ExcelCellsGenerator excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(annotatedObjects);
		excelFileCreator.createFile(excelCellsGenerator.getDataCells(), excelCellsGenerator.getHeaders(), sheetName);
	}
}
