package tk.aizydorczyk.model;

import lombok.Data;

@Data
public class DataCell {
	private Object data;
	private Long rowPosition;
	private Header header;

	private DataCell(Object data) {
		this.data = data;
	}

	public static DataCell createWithUncastData(Object data) {
		return new DataCell(data);
	}

	public Long getColumnPosition(){
		return this.getHeader().getStartColumnPosition();
	}
}


