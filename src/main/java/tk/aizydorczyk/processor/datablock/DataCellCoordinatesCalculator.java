package tk.aizydorczyk.processor.datablock;

import lombok.Getter;
import tk.aizydorczyk.model.DataBlock;
import tk.aizydorczyk.model.DataCell;
import tk.aizydorczyk.model.Header;

import java.util.List;
import java.util.stream.Collectors;

public class DataCellCoordinatesCalculator {
	@Getter
	private final List<DataCell> dataCells;

	private DataCellCoordinatesCalculator(List<DataBlock> dataBlocks, long firstDataRowPosition) {
		List<DataBlock> dataBlocksWithCalculatedCells = calculateDataCellsCoordinates(dataBlocks, firstDataRowPosition);
		this.dataCells = extractDataCellsFromBlocks(dataBlocksWithCalculatedCells);
	}

	public static DataCellCoordinatesCalculator ofDataBlocksAndFirstDataRowPosition(List<DataBlock> generatedDataBlocks, long firstDataRowPosition) {
		return new DataCellCoordinatesCalculator(generatedDataBlocks, firstDataRowPosition);
	}

	private List<DataBlock> calculateDataCellsCoordinates(List<DataBlock> dataBlocks, long firstDataRowPosition) {
		dataBlocks.stream()
				.collect(Collectors.groupingBy(DataBlock::getHeader))
				.entrySet().stream()
				.forEach(entry -> entry
						.getValue().stream()
						.forEach(dataBlock -> {
							Header header = dataBlock.getHeader();
							if (header.isOverData()) {
								calculateCellsPosition(firstDataRowPosition, entry.getValue());
							} else {
								calculateCellsPositionInInnerDataBlocks(firstDataRowPosition, entry.getValue());
							}
						}));
		return dataBlocks;
	}

	private void calculateCellsPosition(long firstDataRowPosition, List<DataBlock> dataBlocks) {
		for (DataBlock block : dataBlocks) {
			for (DataCell dataCell : block.getCells()) {
				dataCell.setRowPosition(++firstDataRowPosition);
				dataCell.setHeader(block.getHeader());
			}
		}
	}

	private void calculateCellsPositionInInnerDataBlocks(long firstDataRowPosition, List<DataBlock> dataBlocks) {
		for (DataBlock block : dataBlocks) {
			calculateDataCellsCoordinates(block.getInternalBlocks(), firstDataRowPosition);
			firstDataRowPosition = firstDataRowPosition + block.getHeight();
		}
	}

	private List<DataCell> extractDataCellsFromBlocks(List<DataBlock> dataBlocks) {
		return dataBlocks.stream()
				.flatMap(DataBlock::streamDataBlocks)
				.map(dataBlock -> dataBlock.getCells())
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
}

