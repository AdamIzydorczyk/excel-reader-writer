package tk.aizydorczyk.model;

import lombok.Data;

@Data
public class Cell {
	private Header header;
	private Object data;
	private Long rowPosition;
	private DataBlock dataBlock;
}
