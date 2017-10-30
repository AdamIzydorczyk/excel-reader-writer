package tk.aizydorczyk.writer;

import lombok.Getter;
import tk.aizydorczyk.model.DataCell;
import tk.aizydorczyk.model.Header;
import tk.aizydorczyk.writer.datablock.DataBlockCreator;
import tk.aizydorczyk.writer.datablock.DataCellCoordinatesCalculator;
import tk.aizydorczyk.writer.header.HeadersCoordinatesCalculator;
import tk.aizydorczyk.writer.header.HeadersInitializer;

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
