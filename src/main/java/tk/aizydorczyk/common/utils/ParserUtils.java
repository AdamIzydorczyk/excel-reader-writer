package tk.aizydorczyk.common.utils;

import tk.aizydorczyk.common.annotation.ExcelGroup;
import tk.aizydorczyk.model.Header;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

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

	public static Header selectMainHeaderOrThrow(List<Header> headers, Supplier exceptionSupplier) {
		return headers.stream()
				.filter(Header::isMainHeader)
				.findFirst()
				.orElseThrow(exceptionSupplier);
	}

	public static <T extends Annotation> T getAnnotationOrThrow(Class<?> annotatedClass, Class<T> annotationClass, Supplier exceptionSupplier) {
		return (T) Optional.ofNullable(annotatedClass.getAnnotation(annotationClass))
				.orElseThrow(exceptionSupplier);
	}

	public static <T extends Annotation> T getAnnotationOrThrow(Field field, Class<T> annotationClass, Supplier exceptionSupplier) {
		return (T) Optional.ofNullable(field.getAnnotation(annotationClass))
				.orElseThrow(exceptionSupplier);
	}


	public static <T extends Throwable> Object getObjectFromFieldOrThrow(Field field, Object untypedObject, Supplier<T> exceptionSupplier) throws T {
		try {
			field.setAccessible(true);
			return field.get(untypedObject);
		} catch (IllegalAccessException e) {
			throw exceptionSupplier.get();
		}
	}
}
