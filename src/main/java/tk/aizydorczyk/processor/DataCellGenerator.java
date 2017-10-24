package tk.aizydorczyk.processor;

import tk.aizydorczyk.model.DataBlock;
import tk.aizydorczyk.model.Header;
import tk.aizydorczyk.processor.datablock.DataBlockCreator;
import tk.aizydorczyk.processor.header.HeadersCoordinatesCalculator;
import tk.aizydorczyk.processor.header.HeadersInitializer;

import java.util.List;

public class DataCellGenerator {

	private final HeadersInitializer headersInitializer;

	private final HeadersCoordinatesCalculator headersCoordinatesCalculator;

	private final DataBlockCreator dataBlockCreator;

	private DataCellGenerator(List<?> annotatedObjects) {
		this.headersInitializer = HeadersInitializer.ofAnnotatedObjects(annotatedObjects);
		this.headersCoordinatesCalculator = new HeadersCoordinatesCalculator();
		this.dataBlockCreator = new DataBlockCreator(annotatedObjects);
	}

	public static DataCellGenerator ofAnnotatedObjects(List<?> objects) {
		return new DataCellGenerator(objects);
	}

	public void generate() {
		List<Header> initializedHeaders = headersInitializer.initialize();
		List<Header> calculatedHeaders = headersCoordinatesCalculator.calculate(initializedHeaders);
		List<DataBlock> dataBlocks = dataBlockCreator.generate(calculatedHeaders);
	}
}
