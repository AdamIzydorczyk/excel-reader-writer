package tk.aizydorczyk.excel.writer.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

enum ExtensionType {
	XLS("xls", HSSFWorkbook::new), XLSX("xlsx", XSSFWorkbook::new);

	private final String extension;
	private final Supplier<Workbook> creationFunction;

	ExtensionType(String extension, Supplier<Workbook> creationFunction) {
		this.extension = extension;
		this.creationFunction = creationFunction;
	}

	static Optional<Workbook> getWorkbookByExtension(String extension) {
		return Stream.of(values())
				.filter(extensionType -> extensionType
						.getExtension().equals(extension.toLowerCase()))
				.map(extensionType -> extensionType.getCreationFunction().get())
				.findFirst();
	}

	private String getExtension() {
		return this.extension;
	}

	private Supplier<Workbook> getCreationFunction() {
		return this.creationFunction;
	}
}
