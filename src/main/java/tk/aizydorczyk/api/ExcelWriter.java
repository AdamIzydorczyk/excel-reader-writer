package tk.aizydorczyk.api;

import tk.aizydorczyk.util.CoordinatesGenerator;

import java.util.List;

public class ExcelWriter {


    public void create(List<?> objects) {
        CoordinatesGenerator.ofObjects(objects);
    }
}
