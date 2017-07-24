package tk.aizydorczyk.util;

import org.apache.commons.lang3.reflect.FieldUtils;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.enums.Types;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CoordinatesGenerator {

    private Long numberOfAllFields = 0L;

    private CoordinatesGenerator(List<?> objects){
        if(objects.isEmpty()){
            throw new CoordinatesGenerator.NoData();
        }
        countAllFields(objects.get(0).getClass());
    }

    private void countAllFields(Class<?> aClass){
        List<Field> fields = FieldUtils.getAllFieldsList(aClass);
        for (Field field : fields) {
            numberOfAllFields++;
            classifyField(field);
        }
    }

    private void classifyField(Field field) {
        ExcelColumn excelColumnAnnotation = field.getAnnotation(ExcelColumn.class);
        if (excelColumnAnnotation.complex()){

            if (Types.LIST.getType().equals(field.getType().getName())){
                Class<?> genericType = getListGenericType(field.getGenericType());
                countAllFields(genericType);
            }

            countAllFields(field.getType());
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
