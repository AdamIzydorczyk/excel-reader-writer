package tk.aizydorczyk.api;

import tk.aizydorczyk.writer.ExcelCellsGenerator;
import tk.aizydorczyk.writer.ExcelFileCreator;

import java.util.List;

public class ExcelWriter {

	private final ExcelCellsGenerator excelCellsGenerator;

	private final ExcelFileCreator excelFileCreator;

	private ExcelWriter(List<?> objects) {
		this.excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(objects);
		this.excelFileCreator = ExcelFileCreator.ofDataCellsAndHeaders(excelCellsGenerator.getDataCells(), excelCellsGenerator.getHeaders());
	}

	public static ExcelWriter ofAnnotatedObjects(List<?> objects) {
		return new ExcelWriter(objects);
	}

	public void create(String sheetName, String path) {
		excelFileCreator.createFile(sheetName, path);
	}
}
