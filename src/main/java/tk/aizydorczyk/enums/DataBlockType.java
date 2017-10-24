package tk.aizydorczyk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tk.aizydorczyk.model.Header;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum DataBlockType {
	SINGLE_WITH_MULTIPLE_CELLS(true, true),
	SINGLE_WITH_CELL(false, true),
	MULTIPLE_FROM_COMPLEX_OBJECT(true, false),
	SINGLE_FROM_COMPLEX_OBJECT(false, false);

	private final boolean overCollection;
	private final boolean overData;

	public static DataBlockType getTypeByHeader(Header header) {
		return Stream.of(DataBlockType.values())
				.filter(type -> type.isOverCollection() == header.isOverCollection() && type.isOverData() == header.isOverData())
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}

}
