package tk.aizydorczyk.model;

import lombok.Data;

@Data
public class Cell {
	private Object data;
	private Long rowPosition;

	public Cell(Object data) {
		this.data = data;
	}
}


