package tk.aizydorczyk.excel.common.enums;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum DataBlockType {
	SINGLE_WITH_MULTIPLE_CELLS(true, true),
	SINGLE_WITH_SINGLE_CELL(false, true),
	MULTIPLE_FROM_COMPLEX_OBJECT(true, false),
	SINGLE_FROM_COMPLEX_OBJECT(false, false);

	private final boolean overCollection;
	private final boolean overData;

	private static final Map<Key, DataBlockType> typeMap = new HashMap<>();

	static {
		for (DataBlockType dataBlockType : values()) {
			typeMap.put(new Key(
							dataBlockType.isOverCollection(),
							dataBlockType.isOverData()),
					dataBlockType);
		}
	}

	public static DataBlockType getTypeByHeader(Header header) {
		return typeMap.get(new Key(
				header.isOverCollection(),
				header.isOverData()));
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	private static class Key {
		private final boolean overCollection;
		private final boolean overData;
	}
}
