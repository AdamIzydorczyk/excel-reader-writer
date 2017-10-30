package tk.aizydorczyk.writer.header;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;
import tk.aizydorczyk.enums.Messages;
import tk.aizydorczyk.model.Header;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static tk.aizydorczyk.common.utils.ParserUtils.getAnnotationOrThrow;
import static tk.aizydorczyk.common.utils.ParserUtils.getCollectionGenericType;
import static tk.aizydorczyk.common.utils.ParserUtils.isCollection;
import static tk.aizydorczyk.common.utils.ParserUtils.isComplexObject;
import static tk.aizydorczyk.enums.Messages.NO_DATA;

public class HeadersInitializer {

	private final List<Header> headers;
	private final List<?> annotatedObjects;

	private HeadersInitializer(List<?> annotatedObjects) {
		this.headers = new ArrayList<>();
		this.annotatedObjects = annotatedObjects;
	}

	public static HeadersInitializer ofAnnotatedObjects(List<?> annotatedObjects) {
		return new HeadersInitializer(annotatedObjects);
	}

	public List<Header> initialize() {
		annotatedObjects.stream().
				findFirst().map(o -> createHeaderByClass(o.getClass(), null))
				.orElseThrow(() -> new HeadersInitializationFail(NO_DATA));

		return headers;
	}

	private Header createHeaderByClass(Class<?> aClass, Header upperHeader) {
		final Header topHeader = createTopHeader(aClass);

		if (nonNull(upperHeader)) {
			topHeader.setUpperHeader(upperHeader);
			upperHeader.getBottomHeaders().add(topHeader);
		}

		headers.add(topHeader);
		createBelowHeadersByAnnotatedFields(aClass, topHeader);
		return topHeader;
	}

	private void createBelowHeadersByAnnotatedFields(Class<?> aClass, Header topHeader) {
		final List<Field> annotatedFields = FieldUtils.getFieldsListWithAnnotation(aClass, ExcelColumn.class);
		for (final Field field : annotatedFields) {
			generateBottomHeader(field, topHeader);
		}
	}

	private Header createTopHeader(Class<?> complexObjectClass) {
		final Header header = new Header();
		final ExcelGroup annotation = getAnnotationOrThrow(complexObjectClass, ExcelGroup.class,
				() -> new HeadersInitializationFail(Messages.NO_ANNOTATION));
		header.setHeaderName(annotation.header());
		return header;
	}

	private void generateBottomHeader(Field field, Header upperHeader) {
		final Header bottomHeader = new Header();
		final ExcelColumn excelColumnAnnotation = getAnnotationOrThrow(field, ExcelColumn.class,
				() -> new HeadersInitializationFail(Messages.NO_ANNOTATION));
		classifyField(field, upperHeader, bottomHeader, excelColumnAnnotation);
	}

	private void classifyField(Field field, Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		if (isCollection(field)) {
			createHeaderForCollection(field, upperHeader)
					.setOverCollection(true);
		} else if (isComplexObject(field)) {
			createHeaderByClass(field.getType(), upperHeader);
		} else {
			createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		}
	}

	private Header createHeaderForCollection(Field field, Header upperHeader) {
		final Class<?> genericType = getCollectionGenericType(field);
		return createHeaderByClass(genericType, upperHeader);
	}

	private void createHeaderOverData(Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		bottomHeader.setHeaderName(excelColumnAnnotation.header());
		bottomHeader.setUpperHeader(upperHeader);
		upperHeader.getBottomHeaders().add(bottomHeader);
		bottomHeader.setOverData(true);
		headers.add(bottomHeader);
	}

	private class HeadersInitializationFail extends RuntimeException {
		public HeadersInitializationFail(Messages message) {
			super(message.getMessage());
		}
	}
}
