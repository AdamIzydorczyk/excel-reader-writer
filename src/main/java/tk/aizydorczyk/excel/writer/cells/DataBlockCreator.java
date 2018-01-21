package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.common.exceptions.ExcelWriterException;
import tk.aizydorczyk.excel.common.messages.Messages;
import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static tk.aizydorczyk.excel.common.messages.Messages.MORE_FIELDS_THAN_HEADERS;
import static tk.aizydorczyk.excel.common.messages.Messages.MORE_HEADERS_THAN_FIELDS;
import static tk.aizydorczyk.excel.common.messages.Messages.NO_MAIN_HEADER;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getFieldsListWithAnnotation;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.selectMainHeaderOrThrow;

final class DataBlockCreator {

	private final List<?> annotatedObjects;

	private final List<DataBlock> generatedDataBlocks;

	private DataBlockCreator(List<?> annotatedObjects, List<Header> headers) {
		this.annotatedObjects = annotatedObjects;
		this.generatedDataBlocks = generate(headers);
	}

	static DataBlockCreator ofAnnotatedObjectsAndHeaders(List<?> annotatedObjects, List<Header> headers) {
		return new DataBlockCreator(annotatedObjects, headers);
	}

	private List<DataBlock> generate(List<Header> headers) {
		final Header mainHeader = selectMainHeaderOrThrow(headers, () ->
				new DataBlockCreateFail(NO_MAIN_HEADER));

		return annotatedObjects.stream()
				.map(untypedObject ->
						mapToDataBlock(untypedObject, mainHeader))
				.collect(Collectors.toList());
	}

	private DataBlock mapToDataBlock(Object untypedObject, Header mainHeader) {
		final DataBlock dataBlock = createDataBlock(untypedObject, mainHeader.getBottomHeaders());
		dataBlock.setHeader(mainHeader);
		return dataBlock;
	}

	private DataBlock createDataBlock(Object untypedObject, List<Header> headers) {
		final DataBlock dataBlock = new DataBlock();
		final List<Field> fields = getFieldsListWithAnnotation(untypedObject.getClass(), SpreadSheetColumn.class);

		checkHeadersAndFieldsNumberEquality(fields.size(), headers.size());

		for (int index = 0; index < headers.size() && index < fields.size(); index++) {
			final Field field = fields.get(index);
			final Header header = headers.get(index);

			final List<DataBlock> listOfDataBlocks = getListOfDataBlocks(field, untypedObject, header);
			dataBlock.addInternalBlocksWithHeader(header, listOfDataBlocks);
		}
		return dataBlock;
	}

	private void checkHeadersAndFieldsNumberEquality(Integer fieldsSize, Integer headersSize) {
		final int compareValue = fieldsSize.compareTo(headersSize);

		if (compareValue > 0) {
			throw new DataBlockCreateFail(MORE_FIELDS_THAN_HEADERS);
		} else if (compareValue < 0) {
			throw new DataBlockCreateFail(MORE_HEADERS_THAN_FIELDS);
		}
	}

	private List<DataBlock> getListOfDataBlocks(Field field, Object untypedObject, Header header) {
		final BlocksCreationDto creationDto = createBlockCreationDto(field, untypedObject, header);
		return DataBlockType.getTypeByHeader(header)
				.createBlocks(creationDto, this::createDataBlock);
	}

	private BlocksCreationDto createBlockCreationDto(Field field, Object untypedObject, Header header) {
		return BlocksCreationDto.builder()
				.field(field)
				.untypedObject(untypedObject)
				.header(header)
				.build();
	}

	public List<DataBlock> getGeneratedDataBlocks() {
		return this.generatedDataBlocks;
	}

	static final class DataBlockCreateFail extends ExcelWriterException {
		DataBlockCreateFail(Messages message) {
			super(message.getMessage());
		}
	}

}