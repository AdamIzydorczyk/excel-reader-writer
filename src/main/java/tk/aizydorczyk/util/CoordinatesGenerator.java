package tk.aizydorczyk.util;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;
import tk.aizydorczyk.enums.Types;
import tk.aizydorczyk.model.Header;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesGenerator {

    private List<Header> headers = new ArrayList<>();

    private CoordinatesGenerator(List<?> objects){
        if(objects.isEmpty()){
            throw new CoordinatesGenerator.NoData();
        }
        createHeadersByClass(objects.get(0).getClass(), null);
    }

    private void createHeadersByClass(Class<?> aClass, Header upperHeader){
        Header topHeader = createTopHeader(aClass);

        if(upperHeader != null){
            topHeader.setUpperHeader(upperHeader);
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

        if (excelColumnAnnotation.complex()){

            if (Types.LIST.getType().equals(field.getType().getName())){
                Class<?> genericType = getListGenericType(field.getGenericType());
                createHeadersByClass(genericType, upperHeader);
            } else {
                createHeadersByClass(field.getType(), upperHeader);
            }

        } else {
            bottomHeader.setHeaderName(excelColumnAnnotation.header());
            bottomHeader.setUpperHeader(upperHeader);
            headers.add(bottomHeader);
        }
    }

    private Class<?> getListGenericType(Type genericType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    public static CoordinatesGenerator ofObjects(List<?> objects) {
        return new CoordinatesGenerator(objects);
    }

    private class NoData extends RuntimeException {
    }
}
