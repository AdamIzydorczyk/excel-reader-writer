package tk.aizydorczyk.excel.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

public class Header {
	private static final int ONE_DATA_CELL_WIDTH = 1;

	private String headerName;
	private int rowPosition = -1;
	private int startColumnPosition = -1;
	private int endColumnPosition = -1;
	private Header upperHeader;
	private List<Header> bottomHeaders = new ArrayList<>();
	private boolean overData;
	private boolean overCollection;
	private Style style;

	public Header() {
	}

	public boolean isMainHeader() {
		return isNull(this.upperHeader);
	}

	public boolean notOverData() {
		return !overData;
	}

	public int getWidth() {
		if (isOverData()) {
			return ONE_DATA_CELL_WIDTH;
		} else {
			return bottomHeaders
					.stream()
					.mapToInt(Header::getWidth)
					.sum();
		}
	}

	public Stream<Header> streamHeaders() {
		return Stream.concat(
				Stream.of(this),
				bottomHeaders.stream().flatMap(Header::streamHeaders));
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public int getRowPosition() {
		return this.rowPosition;
	}

	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}

	public int getStartColumnPosition() {
		return this.startColumnPosition;
	}

	public void setStartColumnPosition(int startColumnPosition) {
		this.startColumnPosition = startColumnPosition;
	}

	public int getEndColumnPosition() {
		return this.endColumnPosition;
	}

	public void setEndColumnPosition(int endColumnPosition) {
		this.endColumnPosition = endColumnPosition;
	}

	public void setUpperHeader(Header upperHeader) {
		this.upperHeader = upperHeader;
	}

	public List<Header> getBottomHeaders() {
		return this.bottomHeaders;
	}

	public boolean isOverData() {
		return this.overData;
	}

	public void setOverData(boolean overData) {
		this.overData = overData;
	}

	public boolean isOverCollection() {
		return this.overCollection;
	}

	public void setOverCollection(boolean overCollection) {
		this.overCollection = overCollection;
	}

	public Style getStyle() {
		return this.style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Header header = (Header) o;

		return rowPosition == header.rowPosition
				&& startColumnPosition == header.startColumnPosition
				&& endColumnPosition == header.endColumnPosition
				&& overData == header.overData
				&& overCollection == header.overCollection
				&& (headerName != null ? headerName.equals(header.headerName) : header.headerName == null)
				&& (upperHeader != null ? upperHeader.equals(header.upperHeader) : header.upperHeader == null)
				&& (style != null ? style.equals(header.style) : header.style == null);
	}

	@Override
	public int hashCode() {
		int result = headerName != null ? headerName.hashCode() : 0;
		result = 31 * result + rowPosition;
		result = 31 * result + startColumnPosition;
		result = 31 * result + endColumnPosition;
		result = 31 * result + (upperHeader != null ? upperHeader.hashCode() : 0);
		result = 31 * result + (overData ? 1 : 0);
		result = 31 * result + (overCollection ? 1 : 0);
		result = 31 * result + (style != null ? style.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Header{" +
				"headerName='" + headerName + '\'' +
				'}';
	}

	public String getHeaderName() {
		return this.headerName;
	}
}
