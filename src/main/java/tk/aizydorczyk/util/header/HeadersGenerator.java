package tk.aizydorczyk.util.header;

import tk.aizydorczyk.model.Header;

import java.util.List;

public class HeadersGenerator {

	private HeadersInitializer headersInitializer;

	private HeadersCoordinatesCalculator headersCoordinatesCalculator;

	private HeadersGenerator(List<?> objects) {
		this.headersInitializer = HeadersInitializer.ofAnnotatedObjects(objects);
		this.headersCoordinatesCalculator = new HeadersCoordinatesCalculator();
	}

	public List<Header> generate(List<?> objects) {
		List<Header> initializedHeaders = headersInitializer.initialize();
		List<Header> calculatedHeaders = headersCoordinatesCalculator.calculate(initializedHeaders);
		return calculatedHeaders;
	}

	public static HeadersGenerator ofAnnotatedObjects(List<?> objects) {
		return new HeadersGenerator(objects);
	}
}
