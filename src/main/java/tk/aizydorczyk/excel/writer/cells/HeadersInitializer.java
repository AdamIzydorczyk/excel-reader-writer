package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.exceptions.ExcelWriterException;
import tk.aizydorczyk.excel.common.messages.Messages;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static tk.aizydorczyk.excel.common.messages.Messages.NO_ANNOTATION;
import static tk.aizydorczyk.excel.common.messages.Messages.NO_DATA;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getAnnotationOrThrow;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getCollectionGenericType;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getFieldsListWithAnnotation;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.getStyleClassFromAnnotation;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.isCollection;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.isComplexObject;

final class HeadersInitializer {

	private final List<Header> headers;
	private final List<?> annotatedObjects;

	private final StyleFactory styleFactory = new StyleFactory();

	private HeadersInitializer(List<?> annotatedObjects) {
		this.headers = new ArrayList<>();
		this.annotatedObjects = annotatedObjects;
	}

	static HeadersInitializer ofAnnotatedObjects(List<?> annotatedObjects) {
		return new HeadersInitializer(annotatedObjects);
	}

	List<Header> initialize() {
		annotatedObjects.stream().
				findFirst().map(o ->
				createHeaderOverDataBlock(o.getClass(), null))
				.orElseThrow(() ->
						new HeadersInitializationFail(NO_DATA));

		return headers;
	}

	private Header createHeaderOverDataBlock(Class<?> aClass, Header upperHeader) {
		final Header topHeader = createTopHeader(aClass);

		if (nonNull(upperHeader)) {
			topHeader.setUpperHeader(upperHeader);
			upperHeader.getBottomHeaders().add(topHeader);
		}

		headers.add(topHeader);
		createBelowHeadersByAnnotatedFields(aClass, topHeader);
		return topHeader;
	}

	private Header createTopHeader(Class<?> complexObjectClass) {
		final Header header = new Header();
		final SpreadSheetGroup annotation = getAnnotationOrThrow(complexObjectClass, SpreadSheetGroup.class, () ->
				new HeadersInitializationFail(NO_ANNOTATION));
		header.setHeaderName(annotation.header());
		header.setStyle(styleFactory.getStyle(getStyleClassFromAnnotation(annotation)));
		return header;
	}


	private void createBelowHeadersByAnnotatedFields(Class<?> aClass, Header topHeader) {
		final List<Field> annotatedFields = getFieldsListWithAnnotation(aClass, SpreadSheetColumn.class);
		for (Field field : annotatedFields) {
			createBottomHeaderAndClassify(field, topHeader);
		}
	}

	private void createBottomHeaderAndClassify(Field field, Header upperHeader) {
		final Header bottomHeader = new Header();
		final SpreadSheetColumn excelColumnAnnotation = getAnnotationOrThrow(field, SpreadSheetColumn.class, () ->
				new HeadersInitializationFail(NO_ANNOTATION));
		classifyField(field, upperHeader, bottomHeader, excelColumnAnnotation);
	}

	private void classifyField(Field field, Header upperHeader, Header bottomHeader, SpreadSheetColumn excelColumnAnnotation) {
		if (isCollection(field.getType())) {
			createHeaderOverCollection(field, upperHeader, bottomHeader, excelColumnAnnotation);
		} else if (isComplexObject(field.getType())) {
			createHeaderOverDataBlock(field.getType(), upperHeader);
		} else {
			createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		}
	}

	private void createHeaderOverCollection(Field field, Header upperHeader, Header bottomHeader, SpreadSheetColumn excelColumnAnnotation) {
		final Class<?> genericType = getCollectionGenericType(field);
		final Header overCollectionHeader = genericType.isAnnotationPresent(SpreadSheetGroup.class)
				? createHeaderOverDataBlock(genericType, upperHeader)
				: createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		overCollectionHeader.setOverCollection(true);
	}

	private Header createHeaderOverData(Header upperHeader, Header bottomHeader, SpreadSheetColumn excelColumnAnnotation) {
		bottomHeader.setHeaderName(excelColumnAnnotation.header());
		bottomHeader.setStyle(styleFactory.getStyle(getStyleClassFromAnnotation(excelColumnAnnotation)));
		bottomHeader.setUpperHeader(upperHeader);
		upperHeader.getBottomHeaders().add(bottomHeader);
		bottomHeader.setOverData(true);
		headers.add(bottomHeader);

		return bottomHeader;
	}

	private final class HeadersInitializationFail extends ExcelWriterException {
		HeadersInitializationFail(Messages message) {
			super(message.getMessage());
		}
	}
}