package tk.aizydorczyk.excel.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataBlock {
	private Header header;
	private List<DataBlock> internalBlocks = new ArrayList<>();
	private List<DataCell> cells = new ArrayList<>();

	private DataBlock(Header header, List<DataCell> cells) {
		this.header = header;
		this.cells = cells;
	}

	public DataBlock() {
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
			return getHeightOfLongerInternalDataBlock();
		}
	}

	private int getHeightOfLongerInternalDataBlock() {
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

	public Stream<DataBlock> streamDataBlocks() {
		return Stream.concat(
				Stream.of(this),
				internalBlocks.stream().flatMap(DataBlock::streamDataBlocks));
	}

	public Header getHeader() {
		return this.header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public List<DataBlock> getInternalBlocks() {
		return this.internalBlocks;
	}

	public List<DataCell> getCells() {
		return this.cells;
	}

	public void setCells(List<DataCell> cells) {
		this.cells = cells;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DataBlock dataBlock = (DataBlock) o;

		return (header != null ? header.equals(dataBlock.header) : dataBlock.header == null)
				&& (internalBlocks != null ? internalBlocks.equals(dataBlock.internalBlocks) : dataBlock.internalBlocks == null)
				&& (cells != null ? cells.equals(dataBlock.cells) : dataBlock.cells == null);
	}

	@Override
	public int hashCode() {
		int result = header != null ? header.hashCode() : 0;
		result = 31 * result + (internalBlocks != null ? internalBlocks.hashCode() : 0);
		result = 31 * result + (cells != null ? cells.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "DataBlock{" +
				"header=" + header +
				'}';
	}
}
