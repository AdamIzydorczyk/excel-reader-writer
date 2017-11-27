package tk.aizydorczyk.excel.writer.datablock.creationstrategy;

import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;
import tk.aizydorczyk.excel.writer.datablock.DataBlockCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static tk.aizydorczyk.excel.common.enums.Messages.CANNOT_GET_VALUE_FROM_FIELD;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getObjectFromFieldOrThrow;

public class SingleDataBlockFromComplexObjectStrategy implements BlocksCreationStrategy {

	@Override
	public List<DataBlock> createBlocks(BlocksCreationDto creationDto, BiFunction<Object, List<Header>, DataBlock> blockCreationFunction) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(creationDto.getField(), creationDto.getUntypedObject(), () ->
				new DataBlockCreator.DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		return optional.map(data ->
				Collections.singletonList(blockCreationFunction.apply(data, creationDto.getHeader().getBottomHeaders())))
				.orElseGet(() ->
						Collections.singletonList(blockCreationFunction.apply(null, creationDto.getHeader().getBottomHeaders()))
				);
	}
}
