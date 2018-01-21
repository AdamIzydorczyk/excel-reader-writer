package tk.aizydorczyk.excel.common.model;

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

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getRowPosition() {
		return this.rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public Header getHeader() {
		return this.header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Style getStyle() {
		final Style style = this.header.getStyle();
		return style.getDataCellsStyle();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DataCell dataCell = (DataCell) o;

		return rowPosition == dataCell.rowPosition
				&& (data != null ? data.equals(dataCell.data) : dataCell.data == null)
				&& (header != null ? header.equals(dataCell.header) : dataCell.header == null);
	}

	@Override
	public int hashCode() {
		int result = data != null ? data.hashCode() : 0;
		result = 31 * result + rowPosition;
		result = 31 * result + (header != null ? header.hashCode() : 0);
		return result;
	}

	public String toString() {
		return "DataCell(data=" + this.getData() + ", rowPosition=" + this.getRowPosition() + ", header=" + this.getHeader() + ", style=" + this.getStyle() + ")";
	}
}