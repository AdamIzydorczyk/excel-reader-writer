package tk.aizydorczyk.api;

import tk.aizydorczyk.processor.ExcelCellsGenerator;

import java.util.List;

public class ExcelWriter {

	private ExcelCellsGenerator excelCellsGenerator;

	private ExcelWriter(List<?> objects) {
		this.excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(objects);
	}

	public static ExcelWriter ofAnnotatedObjects(List<?> objects) {
		return new ExcelWriter(objects);
	}

	public void create() {
		// TODO: 28.07.2017
	}
}
