package tk.aizydorczyk.excel.common.model;

import lombok.Data;

@Data
public class DataCell {
	private Object data;
	private int rowPosition = -1;
	private Header header;

	private DataCell(Object data) {
		this.data = data;
	}

	public static DataCell createWithNotCastData(Object data) {
		return new DataCell(data);
	}

	public int getColumnPosition() {
		return this.getHeader().getStartColumnPosition();
	}


}


