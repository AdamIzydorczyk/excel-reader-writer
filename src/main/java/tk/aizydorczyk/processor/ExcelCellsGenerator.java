package tk.aizydorczyk.processor;

import lombok.Getter;
import tk.aizydorczyk.model.DataCell;
import tk.aizydorczyk.model.Header;
import tk.aizydorczyk.processor.datablock.DataBlockCreator;
import tk.aizydorczyk.processor.datablock.DataCellCoordinatesCalculator;
import tk.aizydorczyk.processor.header.HeadersCoordinatesCalculator;
import tk.aizydorczyk.processor.header.HeadersInitializer;

import java.util.List;

public class ExcelCellsGenerator {

	private final HeadersInitializer headersInitializer;

	private final HeadersCoordinatesCalculator headersCoordinatesCalculator;

	private final DataBlockCreator dataBlockCreator;

	private final DataCellCoordinatesCalculator dataCellCoordinatesCalculator;

	@Getter
	private final List<DataCell> dataCells;

	@Getter
	private final List<Header> headers;

	private ExcelCellsGenerator(List<?> annotatedObjects) {
		this.headersInitializer = HeadersInitializer.ofAnnotatedObjects(annotatedObjects);
		this.headersCoordinatesCalculator = new HeadersCoordinatesCalculator(headersInitializer.initialize());
		this.dataBlockCreator = DataBlockCreator.ofAnnotatedObjectsAndHeaders(annotatedObjects, headersCoordinatesCalculator.getCalculatedHeaders());
		this.dataCellCoordinatesCalculator = DataCellCoordinatesCalculator.ofDataBlocksAndFirstDataRowPosition(dataBlockCreator.getGeneratedDataBlocks(), headersCoordinatesCalculator.getFirstDataRowPosition());
		this.dataCells = dataCellCoordinatesCalculator.getDataCells();
		this.headers = headersCoordinatesCalculator.getCalculatedHeaders();
	}

	public static ExcelCellsGenerator ofAnnotatedObjects(List<?> objects) {
		return new ExcelCellsGenerator(objects);
	}

}
