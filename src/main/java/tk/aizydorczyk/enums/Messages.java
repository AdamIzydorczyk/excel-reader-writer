package tk.aizydorczyk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {
	MORE_FIELDS_THAN_HEADERS("More fields than headers"),
	MORE_HEADERS_THAN_FIELDS("More headers than fields"),
	NO_MAIN_HEADER("No main header"),
	NO_VALID_TYPE("No valid type"),
	NO_DATA("No data to export"),
	NO_ANNOTATION("Unannotated field or class"),
	NO_BOTTOM_HEADERS("No bottom headers"),
	NO_DATA_HEADERS("No data headers"),
	CANNOT_GET_VALUE_FROM_FIELD("Cannot get value from field");

	private final String message;

}
