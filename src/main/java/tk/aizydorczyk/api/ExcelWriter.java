package tk.aizydorczyk.api;

import tk.aizydorczyk.processor.DataCellGenerator;

import java.util.List;

public class ExcelWriter {

	private DataCellGenerator dataCellGenerator;

	private ExcelWriter(List<?> objects) {
		this.dataCellGenerator = DataCellGenerator.ofAnnotatedObjects(objects);
	}

	public static ExcelWriter ofAnnotatedObjects(List<?> objects) {
		return new ExcelWriter(objects);
	}

	public void create() {
		// TODO: 28.07.2017
	}
}
