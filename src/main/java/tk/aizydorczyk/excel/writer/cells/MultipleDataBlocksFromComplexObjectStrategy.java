package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static tk.aizydorczyk.excel.common.enums.Messages.CANNOT_GET_VALUE_FROM_FIELD;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getObjectFromFieldOrThrow;

final class MultipleDataBlocksFromComplexObjectStrategy implements BlocksCreationStrategy {

	@Override
	public List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(creationDto.getField(), creationDto.getUntypedObject(), () ->
				new DataBlockCreator.DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		return optional.map(collection ->
				((List<Object>) collection).stream()
						.map(data ->
								blockCreationFunction.apply(data, creationDto.getHeader().getBottomHeaders()))
						.collect(Collectors.toList()))
				.orElseGet(() ->
						getDataBlockListWithOneBlockWithOneCellWithoutData(creationDto.getHeader()));
	}

	private List<DataBlock> getDataBlockListWithOneBlockWithOneCellWithoutData(Header header) {
		return Collections.singletonList(DataBlock.createWithHeaderAndCells(
				header, Collections.singletonList(DataCell.createWithNotCastData(null))
		));
	}
}