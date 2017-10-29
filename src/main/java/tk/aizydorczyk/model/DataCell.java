package tk.aizydorczyk.model;

import lombok.Data;

@Data
public class DataCell {
	private Object data;
	private Long rowPosition;

	private DataCell(Object data) {
		this.data = data;
	}

	public static DataCell createWithUncastData(Object data) {
		return new DataCell(data);
	}
}

