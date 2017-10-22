package tk.aizydorczyk.util.header;

import tk.aizydorczyk.model.DataBlock;
import tk.aizydorczyk.model.Header;

import java.util.List;

public class HeadersGenerator {

	private HeadersInitializer headersInitializer;

	private HeadersCoordinatesCalculator headersCoordinatesCalculator;

	private DataBlockCreator dataBlockCreator;

	private HeadersGenerator(List<?> objects) {
		this.headersInitializer = HeadersInitializer.ofAnnotatedObjects(objects);
		this.headersCoordinatesCalculator = new HeadersCoordinatesCalculator();
		this.dataBlockCreator = new DataBlockCreator(objects);
	}

	public List<DataBlock> generate() {
		List<Header> initializedHeaders = headersInitializer.initialize();
		List<Header> calculatedHeaders = headersCoordinatesCalculator.calculate(initializedHeaders);
		return dataBlockCreator.generate(calculatedHeaders);
	}

	public static HeadersGenerator ofAnnotatedObjects(List<?> objects) {
		return new HeadersGenerator(objects);
	}
}
