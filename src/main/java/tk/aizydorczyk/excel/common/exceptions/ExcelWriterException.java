package tk.aizydorczyk.excel.common.exceptions;

import java.io.IOException;

public class ExcelWriterException extends RuntimeException {
	public ExcelWriterException(String message) {
		super(message);
	}

	public ExcelWriterException(IOException ex, String message) {
		super(message, ex);
	}
}
