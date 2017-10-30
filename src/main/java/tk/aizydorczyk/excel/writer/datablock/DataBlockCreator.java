package tk.aizydorczyk.excel.writer.datablock;

import lombok.Getter;
import tk.aizydorczyk.excel.common.annotation.ExcelColumn;
import tk.aizydorczyk.excel.common.enums.Messages;
import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static tk.aizydorczyk.excel.common.enums.Messages.CANNOT_GET_VALUE_FROM_FIELD;
import static tk.aizydorczyk.excel.common.enums.Messages.MORE_FIELDS_THAN_HEADERS;
import static tk.aizydorczyk.excel.common.enums.Messages.MORE_HEADERS_THAN_FIELDS;
import static tk.aizydorczyk.excel.common.enums.Messages.NO_MAIN_HEADER;
import static tk.aizydorczyk.excel.common.enums.Messages.NO_VALID_TYPE;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getFieldsListWithAnnotation;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getObjectFromFieldOrThrow;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.selectMainHeaderOrThrow;


public class DataBlockCreator {

	private final List<?> annotatedObjects;

	@Getter
	private final List<DataBlock> generatedDataBlocks;

	private DataBlockCreator(List<?> annotatedObjects, List<Header> headers) {
		this.annotatedObjects = annotatedObjects;
		this.generatedDataBlocks = generate(headers);
	}

	public static DataBlockCreator ofAnnotatedObjectsAndHeaders(List<?> annotatedObjects, List<Header> headers) {
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
		final List<Field> fields = getFieldsListWithAnnotation(untypedObject.getClass(), ExcelColumn.class);

		checkHeadersAndFieldsNumberEquality(fields.size(), headers.size());

		for (int index = 0; index < headers.size() && index < fields.size(); index++) {
			final Field field = fields.get(index);
			final Header header = headers.get(index);

			dataBlock.addInternalBlocksWithHeader(header, getListOfDataBlocks(field, untypedObject, header));
		}
		return dataBlock;
	}

	private List<DataBlock> getListOfDataBlocks(Field field, Object untypedObject, Header header) {
		switch (header.getDataBlockType()) {
			case SINGLE_WITH_SINGLE_CELL:
				return getSingleDataBlockWithSingleCell(field, untypedObject, header);
			case SINGLE_FROM_COMPLEX_OBJECT:
				return getSingleDataBlockFromComplexObject(field, untypedObject, header);
			case SINGLE_WITH_MULTIPLE_CELLS:
				return getSingleDataBlockWithMultipleCells(field, untypedObject, header);
			case MULTIPLE_FROM_COMPLEX_OBJECT:
				return getMultipleDataBlocksFromComplexObject(field, untypedObject, header);
			default:
				throw new DataBlockCreateFail(NO_VALID_TYPE);
		}
	}

	private void checkHeadersAndFieldsNumberEquality(Integer fieldsSize, Integer headersSize) {
		final int compareValue = fieldsSize.compareTo(headersSize);

		if (compareValue > 0) {
			throw new DataBlockCreateFail(MORE_FIELDS_THAN_HEADERS);
		} else if (compareValue < 0) {
			throw new DataBlockCreateFail(MORE_HEADERS_THAN_FIELDS);
		}
	}

	private List<DataBlock> getSingleDataBlockFromComplexObject(Field field, Object untypedObject, Header header) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(field, untypedObject, () ->
				new DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		return optional.map(data ->
				Collections.singletonList(createDataBlock(data, header.getBottomHeaders())))
				.orElseGet(() ->
						Collections.singletonList(createDataBlock(null, header.getBottomHeaders()))
				);
	}

	@SuppressWarnings("unchecked")
	private List<DataBlock> getMultipleDataBlocksFromComplexObject(Field field, Object untypedObject, Header header) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(field, untypedObject, () ->
				new DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		return optional.map(collection ->
				((List<Object>) collection).stream()
						.map(data ->
								createDataBlock(data, header.getBottomHeaders()))
						.collect(Collectors.toList()))
				.orElseGet(() ->
						getDataBlockListWithOneBlockWithOneCellWithoutData(header));
	}

	private List<DataBlock> getDataBlockListWithOneBlockWithOneCellWithoutData(Header header) {
		return Collections.singletonList(DataBlock.createWithHeaderAndCells(
				header, Collections.singletonList(DataCell.createWithNotCastData(null))
		));
	}

	private List<DataBlock> getSingleDataBlockWithSingleCell(Field field, Object untypedObject, Header header) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(field, untypedObject, () ->
				new DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		return Collections.singletonList(
				DataBlock.createWithHeaderAndCells(
						header, Collections.singletonList(DataCell.createWithNotCastData(optional.orElse(null)))
				));
	}

	@SuppressWarnings("unchecked")
	private List<DataBlock> getSingleDataBlockWithMultipleCells(Field field, Object untypedObject, Header header) {
		final Optional<Object> optional = getObjectFromFieldOrThrow(field, untypedObject, () ->
				new DataBlockCreateFail(CANNOT_GET_VALUE_FROM_FIELD));

		final List<DataCell> dataCells = optional.map(collection ->
				((List<Object>) collection).stream()
						.map(DataCell::createWithNotCastData)
						.collect(Collectors.toList()))
				.orElseGet(() ->
						Collections.singletonList(DataCell.createWithNotCastData(null)));

		return Collections.singletonList(
				DataBlock.createWithHeaderAndCells(header, dataCells));
	}

	private class DataBlockCreateFail extends RuntimeException {
		public DataBlockCreateFail(Messages message) {
			super(message.getMessage());
		}
	}

}
