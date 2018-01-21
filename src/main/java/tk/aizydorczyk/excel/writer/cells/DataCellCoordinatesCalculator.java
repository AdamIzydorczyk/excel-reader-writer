package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.DataBlock;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.List;
import java.util.stream.Collectors;

final class DataCellCoordinatesCalculator {
	private final List<DataCell> dataCells;

	private DataCellCoordinatesCalculator(List<DataBlock> dataBlocks, int firstDataRowPosition) {
		List<DataBlock> dataBlocksWithCalculatedCells = calculateDataCellsCoordinates(dataBlocks, firstDataRowPosition);
		this.dataCells = extractDataCellsFromBlocks(dataBlocksWithCalculatedCells);
	}

	static DataCellCoordinatesCalculator ofDataBlocksAndFirstDataRowPosition(List<DataBlock> generatedDataBlocks, int firstDataRowPosition) {
		return new DataCellCoordinatesCalculator(generatedDataBlocks, firstDataRowPosition);
	}

	private List<DataBlock> calculateDataCellsCoordinates(List<DataBlock> dataBlocks, int firstDataRowPosition) {
		dataBlocks.stream()
				.collect(Collectors.groupingBy(DataBlock::getHeader))
				.forEach((keyHeader, dataBlockList) -> dataBlockList
						.forEach(dataBlock -> {
									Header header = dataBlock.getHeader();
									if (header.isOverData()) {
										calculateCellsPosition(firstDataRowPosition, dataBlockList);
									} else {
										calculateCellsPositionInInnerDataBlocks(firstDataRowPosition, dataBlockList);
									}
								}
						));
		return dataBlocks;
	}

	private void calculateCellsPosition(int firstDataRowPosition, List<DataBlock> dataBlocks) {
		for (DataBlock block : dataBlocks) {
			for (DataCell dataCell : block.getCells()) {
				dataCell.setRowPosition(++firstDataRowPosition);
				dataCell.setHeader(block.getHeader());
			}
		}
	}

	private void calculateCellsPositionInInnerDataBlocks(int firstDataRowPosition, List<DataBlock> dataBlocks) {
		for (DataBlock block : dataBlocks) {
			calculateDataCellsCoordinates(block.getInternalBlocks(), firstDataRowPosition);
			firstDataRowPosition = firstDataRowPosition + block.getHeight();
		}
	}

	private List<DataCell> extractDataCellsFromBlocks(List<DataBlock> dataBlocks) {
		return dataBlocks.stream()
				.flatMap(DataBlock::streamDataBlocks)
				.map(DataBlock::getCells)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	public List<DataCell> getDataCells() {
		return this.dataCells;
	}
}