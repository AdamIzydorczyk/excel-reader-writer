package tk.aizydorczyk.util.header;

import tk.aizydorczyk.model.Header;

import java.util.List;

public class HeadersGenerator {

    private HeadersInitializer headersInitializer;

    private HeadersGenerator(List<?> objects) {
        this.headersInitializer = HeadersInitializer.ofAnnotatedObjects(objects);
    }

    public List<Header> generate(List<?> objects) {
        return headersInitializer.initialize();
    }

    public static HeadersGenerator ofAnnotatedObjects(List<?> objects) {
        return new HeadersGenerator(objects);
    }
}
