package tk.aizydorczyk.util.header;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;
import tk.aizydorczyk.model.Header;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

        createHeadersByClass(objects.get(0).getClass(), null);
        return headers;
    }

    private void createHeadersByClass(Class<?> aClass, Header upperHeader) {
        Header topHeader = createTopHeader(aClass);

        if (upperHeader != null) {
            topHeader.setUpperHeader(upperHeader);
            upperHeader.getBottomHeaders().add(topHeader);
        }

        headers.add(topHeader);

        List<Field> fields = FieldUtils.getAllFieldsList(aClass);
        for (Field field : fields) {
            generateBottomHeaders(field, topHeader);
        }
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

        if (excelColumnAnnotation.complex()) {
            createHeaderForComplexType(field, upperHeader);
        } else {
            createHeaderForSimpleType(upperHeader, bottomHeader, excelColumnAnnotation);
        }
    }

    private void createHeaderForComplexType(Field field, Header upperHeader) {
        if (List.class.getName().equals(field.getType().getName())) {
            createHeaderForList(field, upperHeader);
        } else {
            createHeadersByClass(field.getType(), upperHeader);
        }
    }

    private void createHeaderForList(Field field, Header upperHeader) {
        Class<?> genericType = getListGenericType(field.getGenericType());
        createHeadersByClass(genericType, upperHeader);
    }

    private void createHeaderForSimpleType(Header upperHeader, Header bottomHeader, ExcelColumn excelColumnAnnotation) {
        bottomHeader.setHeaderName(excelColumnAnnotation.header());
        bottomHeader.setUpperHeader(upperHeader);
        upperHeader.getBottomHeaders().add(bottomHeader);
        headers.add(bottomHeader);
    }

    private Class<?> getListGenericType(Type genericType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    private class NoData extends RuntimeException {
    }
}
