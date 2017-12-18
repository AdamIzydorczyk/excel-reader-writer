package tk.aizydorczyk.excel.writer.header;

import tk.aizydorczyk.excel.common.annotation.ExcelColumn;
import tk.aizydorczyk.excel.common.annotation.ExcelGroup;
import tk.aizydorczyk.excel.common.enums.Messages;
import tk.aizydorczyk.excel.common.model.Header;
import tk.aizydorczyk.excel.common.model.Style;
import tk.aizydorczyk.excel.common.style.ExcelStyle;
import tk.aizydorczyk.excel.common.style.ExcelStyleConfigurator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static tk.aizydorczyk.excel.common.enums.Messages.NO_ANNOTATION;
import static tk.aizydorczyk.excel.common.enums.Messages.NO_DATA;
import static tk.aizydorczyk.excel.common.enums.Messages.STYLE_INITIALIZATION_FAIL;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getAnnotationOrThrow;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getCollectionGenericType;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getFieldsListWithAnnotation;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.getStyleClassFromAnnotation;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.isCollection;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.isComplexObject;
import static tk.aizydorczyk.excel.common.utils.ParserUtils.notSpecifiedStyle;

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
				findFirst().map(o ->
				createHeaderByClass(o.getClass(), null))
				.orElseThrow(() ->
						new HeadersInitializationFail(NO_DATA));

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
		final List<Field> annotatedFields = getFieldsListWithAnnotation(aClass, ExcelColumn.class);
		for (final Field field : annotatedFields) {
			generateBottomHeader(field, topHeader);
		}
	}

	private Header createTopHeader(Class<?> complexObjectClass) {
		final Header header = new Header();
		final ExcelGroup annotation = getAnnotationOrThrow(complexObjectClass, ExcelGroup.class, () ->
				new HeadersInitializationFail(NO_ANNOTATION));
		header.setHeaderName(annotation.header());
		header.setStyle(collectStyle(getStyleClassFromAnnotation(annotation)));
		return header;
	}

	private Style collectStyle(Class<? extends ExcelStyle> styleClass) {
		if (notSpecifiedStyle(styleClass)) {
			return Style.DEFAULT_HEADER_STYLE;
		} else {
			final ExcelStyle excelStyle = instantiateStyle(styleClass);
			final ExcelStyleConfigurator excelStyleConfigurator = new ExcelStyleConfigurator();
			excelStyle.configureStyle(excelStyleConfigurator);
			return excelStyleConfigurator.getStyle();
		}
	}

	private ExcelStyle instantiateStyle(Class<? extends ExcelStyle> styleClass) {
		try {
			return styleClass.newInstance();
		} catch (Exception e) {
			throw new HeadersInitializationFail(STYLE_INITIALIZATION_FAIL, e);
		}
	}

	private void generateBottomHeader(Field field, Header upperHeader) {
		final Header bottomHeader = new Header();
		final ExcelColumn excelColumnAnnotation = getAnnotationOrThrow(field, ExcelColumn.class, () ->
				new HeadersInitializationFail(NO_ANNOTATION));
		classifyField(field, upperHeader, bottomHeader, excelColumnAnnotation);
	}

	private void classifyField(Field field, Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		if (isCollection(field)) {
			createHeaderForCollection(field, upperHeader, bottomHeader, excelColumnAnnotation)
					.setOverCollection(true);
		} else if (isComplexObject(field)) {
			createHeaderByClass(field.getType(), upperHeader);
		} else {
			createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		}
	}

	private Header createHeaderForCollection(Field field, Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		final Class<?> genericType = getCollectionGenericType(field);

		if (genericType.isAnnotationPresent(ExcelGroup.class)) {
			return createHeaderByClass(genericType, upperHeader);
		} else {
			return createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		}
	}

	private Header createHeaderOverData(Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		bottomHeader.setHeaderName(excelColumnAnnotation.header());
		bottomHeader.setStyle(collectStyle(getStyleClassFromAnnotation(excelColumnAnnotation)));
		bottomHeader.setUpperHeader(upperHeader);
		upperHeader.getBottomHeaders().add(bottomHeader);
		bottomHeader.setOverData(true);
		headers.add(bottomHeader);

		return bottomHeader;
	}

	private class HeadersInitializationFail extends RuntimeException {
		public HeadersInitializationFail(Messages message) {
			super(message.getMessage());
		}

		public HeadersInitializationFail(Messages messages, Exception e) {
			super(messages.getMessage(), e);
		}
	}
}
