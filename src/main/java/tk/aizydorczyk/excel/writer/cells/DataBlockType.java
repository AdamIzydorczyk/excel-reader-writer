package tk.aizydorczyk.excel.writer.cells;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@AllArgsConstructor
enum DataBlockType {
	SINGLE_WITH_MULTIPLE_CELLS(true, true, new SingleDataBlockWithMultipleCellsStrategy()),
	SINGLE_WITH_SINGLE_CELL(false, true, new SingleDataBlockWithSingleCellStrategy()),
	MULTIPLE_FROM_COMPLEX_OBJECT(true, false, new MultipleDataBlocksFromComplexObjectStrategy()),
	SINGLE_FROM_COMPLEX_OBJECT(false, false, new SingleDataBlockFromComplexObjectStrategy());

	private static final Map<Key, DataBlockType> typeMap = new HashMap<>();

	static {
		for (DataBlockType dataBlockType : values()) {
			typeMap.put(new Key(
							dataBlockType.isOverCollection(),
							dataBlockType.isOverData()),
					dataBlockType);
		}
	}

	@Getter
	private final boolean overCollection;
	@Getter
	private final boolean overData;
	private final BlocksCreationStrategy blocksCreationStrategy;

	static DataBlockType getTypeByHeader(Header header) {
		return typeMap.get(new Key(
				header.isOverCollection(),
				header.isOverData()));
	}

	List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction) {
		return this.blocksCreationStrategy.createBlocks(creationDto, blockCreationFunction);
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	private static final class Key {
		private final boolean overCollection;
		private final boolean overData;
	}
}