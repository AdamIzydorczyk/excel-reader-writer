package tk.aizydorczyk.excel.writer.file;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter(AccessLevel.PRIVATE)
enum ExtensionType {
	XLS("xls", HSSFWorkbook::new), XLSX("xlsx", XSSFWorkbook::new);

	private String extension;
	private Supplier<Workbook> creationFunction;

	static Optional<Workbook> getWorkbookByExtension(String extension) {
		return Stream.of(values())
				.filter(extensionType -> extensionType
						.getExtension().equals(extension.toLowerCase()))
				.map(extensionType -> extensionType.getCreationFunction().get())
				.findFirst();
	}
}
