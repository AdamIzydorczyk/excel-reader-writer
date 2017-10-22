package tk.aizydorczyk.util.header;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;
import tk.aizydorczyk.model.Header;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;

public class HeadersInitializer {

	private List<Header> headers;
	private List<?> objects;

	private HeadersInitializer(List<?> objects) {
		this.headers = new ArrayList<>();
		this.objects = objects;
	}

	public static HeadersInitializer ofAnnotatedObjects(List<?> objects) {
		return new HeadersInitializer(objects);
	}

	public List<Header> initialize() {
		if (objects.isEmpty()) {
			throw new HeadersInitializer.NoData();
		}

		createHeaderByClass(objects.get(0).getClass(), null);
		return headers;
	}

	private Header createHeaderByClass(Class<?> aClass, Header upperHeader) {
		Header topHeader = createTopHeader(aClass);

		if (nonNull(upperHeader)) {
			topHeader.setUpperHeader(upperHeader);
			upperHeader.getBottomHeaders().add(topHeader);
		}

		headers.add(topHeader);

		List<Field> fields = FieldUtils.getAllFieldsList(aClass);
		for (Field field : fields) {
			generateBottomHeaders(field, topHeader);
		}

		return topHeader;
	}

	private Header createTopHeader(Class<?> aClass) {
		Header header = new Header();
		ExcelGroup annotation = aClass.getAnnotation(ExcelGroup.class);
		header.setHeaderName(annotation.header());
		return header;
	}

	private void generateBottomHeaders(Field field, Header upperHeader) {
		Header bottomHeader = new Header();
		ExcelColumn excelColumnAnnotation = field.getAnnotation(ExcelColumn.class);
		classifyField(field, upperHeader, bottomHeader, excelColumnAnnotation);
	}

	private void classifyField(Field field, Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		if (isCollection(field)) {
			createHeaderForCollection(field, upperHeader)
					.setOverCollection(true);
		} else if (nonNull(field.getType().getAnnotation(ExcelGroup.class))) {
			createHeaderByClass(field.getType(), upperHeader);
		} else {
			createHeaderOverData(upperHeader, bottomHeader, excelColumnAnnotation);
		}
	}

	private Header createHeaderForCollection(Field field, Header upperHeader) {
		Class<?> genericType = getListGenericType(field.getGenericType());
		return createHeaderByClass(genericType, upperHeader);
	}

	private void createHeaderOverData(Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
		bottomHeader.setHeaderName(excelColumnAnnotation.header());
		bottomHeader.setUpperHeader(upperHeader);
		upperHeader.getBottomHeaders().add(bottomHeader);
		bottomHeader.setOverData(true);
		headers.add(bottomHeader);
	}

	private Class<?> getListGenericType(Type genericType) {
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		return (Class<?>) parameterizedType.getActualTypeArguments()[0];
	}

	private boolean isCollection(Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

	private class NoData extends RuntimeException {
	}
}
