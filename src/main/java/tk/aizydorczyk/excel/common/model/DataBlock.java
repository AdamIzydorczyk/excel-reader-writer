package tk.aizydorczyk.excel.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class DataBlock {
	private Header header;
	private List<DataBlock> internalBlocks = new ArrayList<>();
	private List<DataCell> cells = new ArrayList<>();

	private DataBlock(Header header, List<DataCell> cells) {
		this.header = header;
		this.cells = cells;
	}

	public static DataBlock createWithHeaderAndCells(Header header, List<DataCell> cells) {
		return new DataBlock(header, cells);
	}

	public void addInternalBlocksWithHeader(Header header, List<DataBlock> listOfDataBlocks) {
		listOfDataBlocks.forEach(dataBlock ->
				dataBlock.setHeader(header));
		this.internalBlocks.addAll(listOfDataBlocks);
	}

	@SuppressWarnings("ConstantConditions")
	public int getHeight() {
		if (internalBlocks.isEmpty()) {
			return cells.size();
		} else {
			return internalBlocks.stream()
					.collect(Collectors.groupingBy(DataBlock::getHeader))
					.entrySet()
					.stream()
					.mapToInt(dataBlockGroup ->
							dataBlockGroup.getValue()
									.stream()
									.mapToInt(DataBlock::getHeight)
									.sum())
					.max()
					.getAsInt();
		}
	}

	public Stream<DataBlock> streamDataBlocks() {
		return Stream.concat(
				Stream.of(this),
				internalBlocks.stream().flatMap(DataBlock::streamDataBlocks));
	}

	@Override
	public String toString() {
		return "DataBlock{" +
				"header=" + header +
				'}';
	}
}
