package tk.aizydorczyk.excel.common.utils;

import tk.aizydorczyk.excel.common.annotation.ExcelGroup;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static tk.aizydorczyk.excel.common.enums.Messages.CLASS_MUST_NOT_BE_NULL;

public class ParserUtils {

	public static Class<?> getCollectionGenericType(Field field) {
		final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		return (Class<?>) parameterizedType.getActualTypeArguments()[0];
	}

	public static boolean isCollection(Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

	public static boolean isComplexObject(Field field) {
		return nonNull(field.getType().getAnnotation(ExcelGroup.class));
	}

	@SuppressWarnings("unchecked")
	public static Header selectMainHeaderOrThrow(List<Header> headers, Supplier exceptionSupplier) {
		return headers.stream()
				.filter(Header::isMainHeader)
				.findFirst()
				.orElseThrow(exceptionSupplier);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotationOrThrow(Class<?> annotatedClass, Class<T> annotationClass, Supplier exceptionSupplier) {
		return (T) Optional.ofNullable(annotatedClass.getAnnotation(annotationClass))
				.orElseThrow(exceptionSupplier);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotationOrThrow(Field field, Class<T> annotationClass, Supplier exceptionSupplier) {
		return (T) Optional.ofNullable(field.getAnnotation(annotationClass))
				.orElseThrow(exceptionSupplier);
	}


	public static <T extends Throwable> Optional<Object> getObjectFromFieldOrThrow(Field field, Object untypedObject, Supplier<T> exceptionSupplier) throws T {
		try {
			field.setAccessible(true);
			return Optional.ofNullable(field.get(untypedObject));
		} catch (IllegalAccessException e) {
			throw exceptionSupplier.get();
		}
	}

	public static boolean notSetBefore(int value) {
		return value == -1;
	}

	/*retrieved from org.apache.commons.lang3.reflect.FieldUtils.getFieldsListWithAnnotation*/
	@SuppressWarnings("unchecked")
	public static List<Field> getFieldsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
		requireNonNull(annotationCls, CLASS_MUST_NOT_BE_NULL.getMessage());
		List<Field> allFields = getAllFieldsList(cls);
		List<Field> annotatedFields = new ArrayList();

		for (Field field : allFields) {
			if (field.getAnnotation(annotationCls) != null) {
				annotatedFields.add(field);
			}
		}

		return annotatedFields;
	}

	@SuppressWarnings("unchecked")
	private static List<Field> getAllFieldsList(Class<?> clazz) {
		requireNonNull(clazz, CLASS_MUST_NOT_BE_NULL.getMessage());
		List<Field> allFields = new ArrayList();

		for (Class currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
			Field[] declaredFields = currentClass.getDeclaredFields();

			Collections.addAll(allFields, declaredFields);
		}
		return allFields;
	}

	/*retrieved from org.apache.commons.io.FilenameUtils.getExtension*/
	public static String getExtension(String filename) {
		if (filename == null) {
			return null;
		} else {
			int index = indexOfExtension(filename);
			return index == -1 ? "" : filename.substring(index + 1);
		}
	}

	private static int indexOfExtension(String filename) {
		if (filename == null) {
			return -1;
		} else {
			int extensionPos = filename.lastIndexOf(46);
			int lastSeparator = indexOfLastSeparator(filename);
			return lastSeparator > extensionPos ? -1 : extensionPos;
		}
	}

	private static int indexOfLastSeparator(String filename) {
		if (filename == null) {
			return -1;
		} else {
			int lastUnixPos = filename.lastIndexOf(47);
			int lastWindowsPos = filename.lastIndexOf(92);
			return Math.max(lastUnixPos, lastWindowsPos);
		}
	}
}
