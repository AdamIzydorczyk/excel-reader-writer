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
	private Map<String, List<DataBlock>> internalBlocks = new HashMap<>();
	private List<Cell> cells = new ArrayList<>();

	public DataBlock(Header header, List<Cell> cells) {
		this.header = header;
		this.cells = cells;
	}

	@Override
	public String toString() {
		return "DataBlock{" +
				"header=" + header +
				'}';
	}
}
