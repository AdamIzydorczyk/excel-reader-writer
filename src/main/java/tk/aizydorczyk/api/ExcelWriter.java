package tk.aizydorczyk.api;

import tk.aizydorczyk.util.header.HeadersGenerator;

import java.util.List;

public class ExcelWriter {

    private HeadersGenerator headersGenerator;

    private ExcelWriter(List<?> objects) {
        this.headersGenerator = HeadersGenerator.ofAnnotatedObjects(objects);
    }

    public static ExcelWriter ofAnnotatedObjects(List<?> objects) {
        return new ExcelWriter(objects);
    }

    public void create() {
        // TODO: 28.07.2017
    }
}
