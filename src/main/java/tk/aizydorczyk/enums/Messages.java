package tk.aizydorczyk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {
	MORE_FIELDS_THAN_HEADERS("More fields than headers"),
	MORE_HEADERS_THAN_FIELDS("More headers than fields"),
	LACK_OF_MAIN_HEADER("Lack of main header");

	private String message;

}
