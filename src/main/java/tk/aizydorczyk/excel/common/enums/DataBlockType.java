package tk.aizydorczyk.excel.common.enums;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.BlocksCreationDto;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.BlocksCreationStrategy;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.MultipleDataBlocksFromComplexObjectStrategy;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.SingleDataBlockFromComplexObjectStrategy;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.SingleDataBlockWithMultipleCellsStrategy;
import tk.aizydorczyk.excel.writer.datablock.creationstrategy.SingleDataBlockWithSingleCellStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@AllArgsConstructor
public enum DataBlockType {
	SINGLE_WITH_MULTIPLE_CELLS(true, true, new SingleDataBlockWithMultipleCellsStrategy()),
	SINGLE_WITH_SINGLE_CELL(false, true, new SingleDataBlockWithSingleCellStrategy()),
	MULTIPLE_FROM_COMPLEX_OBJECT(true, false, new MultipleDataBlocksFromComplexObjectStrategy()),
	SINGLE_FROM_COMPLEX_OBJECT(false, false, new SingleDataBlockFromComplexObjectStrategy());

	@Getter
	private final boolean overCollection;
	@Getter
	private final boolean overData;

	private final BlocksCreationStrategy blocksCreationStrategy;

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

	public List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction) {
		return this.blocksCreationStrategy.createBlocks(creationDto, blockCreationFunction);
	}

	@AllArgsConstructor
	@EqualsAndHashCode
	private static class Key {
		private final boolean overCollection;
		private final boolean overData;
	}
}
