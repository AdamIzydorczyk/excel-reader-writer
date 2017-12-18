package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
interface BlocksCreationStrategy {
	List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction);
}