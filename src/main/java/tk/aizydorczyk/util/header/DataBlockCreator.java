package tk.aizydorczyk.util.header;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.enums.Messages;
import tk.aizydorczyk.model.Cell;
import tk.aizydorczyk.model.DataBlock;
import tk.aizydorczyk.model.Header;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static tk.aizydorczyk.enums.Messages.MORE_FIELDS_THAN_HEADERS;
import static tk.aizydorczyk.enums.Messages.MORE_HEADERS_THAN_FIELDS;
import static tk.aizydorczyk.enums.Messages.LACK_OF_MAIN_HEADER;


public class DataBlockCreator {

	private List<?> objects;

	public DataBlockCreator(List<?> objects) {
		this.objects = objects;
	}

	public List<DataBlock> generate(List<Header> headers) {
		Header mainHeader = headers.stream()
				.filter(Header::isMainHeader)
				.findFirst()
				.orElseThrow(() -> new DataBlockCreateFail(LACK_OF_MAIN_HEADER));

		return objects.stream()
				.map(o -> createDataBlock(o, mainHeader.getBottomHeaders()))
				.collect(Collectors.toList());
	}

	private DataBlock createDataBlock(Object object, List<Header> headers) {
		DataBlock dataBlock = new DataBlock();
		List<Field> fields = FieldUtils.getFieldsListWithAnnotation(object.getClass(), ExcelColumn.class);

		checkHeadersAndFieldsNumberEquality(fields.size(), headers.size());

		for (int index = 0; index < headers.size(); index++) {
			Field field = fields.get(index);
			Header header = headers.get(index);

			if (header.isOverCollection() && header.isOverData()) {
				dataBlock.getDataBlockMap().put(header.getHeaderName(), getSingleDataBlockWithCells(field, object, header));
			}
			if (!header.isOverCollection() && header.isOverData()) {
				dataBlock.getDataBlockMap().put(header.getHeaderName(), getSingleDataBlockWithOneCell(field, object, header));
			}
			if (header.isOverCollection() && !header.isOverData()) {
				dataBlock.getDataBlockMap().put(header.getHeaderName(), getMultipleDataBlocksFromComplexObject(field, object, header));
			}
			if (!header.isOverCollection() && !header.isOverData()) {
				dataBlock.getDataBlockMap().put(header.getHeaderName(), getSingleDataBlockFromComplexObject(field, object, header));
			}
		}
		return dataBlock;
	}

	private void checkHeadersAndFieldsNumberEquality(Integer fieldsSize, Integer headersSize) {
		int compareValue = fieldsSize.compareTo(headersSize);

		if (compareValue > 0) {
			throw new DataBlockCreateFail(MORE_FIELDS_THAN_HEADERS);
		} else if (compareValue < 0) {
			throw new DataBlockCreateFail(MORE_HEADERS_THAN_FIELDS);
		}
	}

	private List<DataBlock> getSingleDataBlockFromComplexObject(Field field, Object object, Header header) {
		return Collections.singletonList(createDataBlock(getObjectFromField(field, object), header.getBottomHeaders()));
	}

	private List<DataBlock> getMultipleDataBlocksFromComplexObject(Field field, Object object, Header header) {
		Collection collection = (Collection) getObjectFromField(field, object);
		return (List<DataBlock>) collection.stream()
				.map(o -> createDataBlock(o, header.getBottomHeaders()))
				.collect(Collectors.toList());
	}

	private List<DataBlock> getSingleDataBlockWithOneCell(Field field, Object object, Header header) {
		Object o = getObjectFromField(field, object);
		return Collections.singletonList(new DataBlock(header, Collections.singletonList(new Cell(o))));
	}

	private List<DataBlock> getSingleDataBlockWithCells(Field field, Object object, Header header) {
		Collection collection = (Collection) getObjectFromField(field, object);
		List<Cell> cells = (List<Cell>) collection.stream()
				.map(o -> new Cell(o))
				.collect(Collectors.toList());
		return Collections.singletonList(new DataBlock(header, cells));
	}

	private Object getObjectFromField(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalAccessException e) {
			throw new DataBlockCreateFail(e);
		}
	}

	private class DataBlockCreateFail extends RuntimeException {
		public DataBlockCreateFail(IllegalAccessException e) {
			super(e);
		}

		public DataBlockCreateFail(Messages message) {
			super(message.getMessage());
		}
	}

}
