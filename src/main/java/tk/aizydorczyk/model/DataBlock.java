package tk.aizydorczyk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		listOfDataBlocks.forEach(dataBlock -> dataBlock.setHeader(header));
		this.internalBlocks.addAll(listOfDataBlocks);
	}

	@Override
	public String toString() {
		return "DataBlock{" +
				"header=" + header +
				'}';
	}
}
