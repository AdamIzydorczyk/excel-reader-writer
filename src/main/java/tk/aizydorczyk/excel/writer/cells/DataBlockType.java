package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

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

	private final boolean overCollection;
	private final boolean overData;
	private final BlocksCreationStrategy blocksCreationStrategy;

	DataBlockType(boolean overCollection, boolean overData, BlocksCreationStrategy blocksCreationStrategy) {
		this.overCollection = overCollection;
		this.overData = overData;
		this.blocksCreationStrategy = blocksCreationStrategy;
	}

	static DataBlockType getTypeByHeader(Header header) {
		return typeMap.get(new Key(
				header.isOverCollection(),
				header.isOverData()));
	}

	List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction) {
		return this.blocksCreationStrategy.createBlocks(creationDto, blockCreationFunction);
	}

	private boolean isOverCollection() {
		return this.overCollection;
	}

	private boolean isOverData() {
		return this.overData;
	}

	private static final class Key {
		private final boolean overCollection;
		private final boolean overData;

		Key(boolean overCollection, boolean overData) {
			this.overCollection = overCollection;
			this.overData = overData;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Key key = (Key) o;

			return overCollection == key.overCollection
					&& overData == key.overData;
		}

		@Override
		public int hashCode() {
			int result = (overCollection ? 1 : 0);
			result = 31 * result + (overData ? 1 : 0);
			return result;
		}
	}
}