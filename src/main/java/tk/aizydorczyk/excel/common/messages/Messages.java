package tk.aizydorczyk.excel.common.messages;

public enum Messages {
	MORE_FIELDS_THAN_HEADERS("More fields than headers"),
	MORE_HEADERS_THAN_FIELDS("More headers than fields"),
	NO_MAIN_HEADER("No main header"),
	NO_VALID_TYPE("No valid type"),
	NO_DATA("No data to export"),
	NO_ANNOTATION("Unannotated field or class"),
	NO_BOTTOM_HEADERS("No bottom headers"),
	NO_DATA_HEADERS("No data headers"),
	CANNOT_GET_VALUE_FROM_FIELD("Cannot get value from field"),
	WRONG_FILE_EXTENSION("Wrong file extension"),
	FILE_CREATION_FAIL("File creation error"),
	CLASS_MUST_NOT_BE_NULL("The class must not be null"),
	STYLE_INITIALIZATION_FAIL("Style initialization fail"),
	FILE_SAVE_ERROR("File saving failed"),
	FILE_CLOSE_FAIL("File closing failed"),
	CANNOT_CAST_TO_COLLECTION("Cannot cast to collection");

	private final String message;

	Messages(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
