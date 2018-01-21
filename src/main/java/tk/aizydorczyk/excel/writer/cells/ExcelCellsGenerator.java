package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.Collections;
import java.util.List;

public final class ExcelCellsGenerator {

	private final List<DataCell> dataCells;

	private final List<Header> headers;

	private ExcelCellsGenerator(List<?> annotatedObjects) {
		final HeadersInitializer headersInitializer = HeadersInitializer.ofAnnotatedObjects(annotatedObjects);
		final HeadersCoordinatesCalculator headersCoordinatesCalculator = new HeadersCoordinatesCalculator(headersInitializer.initialize());
		final DataBlockCreator dataBlockCreator = DataBlockCreator.ofAnnotatedObjectsAndHeaders(annotatedObjects, headersCoordinatesCalculator.getCalculatedHeaders());
		final DataCellCoordinatesCalculator dataCellCoordinatesCalculator = DataCellCoordinatesCalculator.ofDataBlocksAndFirstDataRowPosition(dataBlockCreator.getGeneratedDataBlocks(), headersCoordinatesCalculator.getFirstDataRowPosition());
		this.dataCells = dataCellCoordinatesCalculator.getDataCells();
		this.headers = headersCoordinatesCalculator.getCalculatedHeaders();
	}

	public static ExcelCellsGenerator ofAnnotatedObjects(List<?> objects) {
		return new ExcelCellsGenerator(objects);
	}

	public List<DataCell> getDataCells() {
		return Collections.unmodifiableList(this.dataCells);
	}

	public List<Header> getHeaders() {
		return this.headers;
	}
}