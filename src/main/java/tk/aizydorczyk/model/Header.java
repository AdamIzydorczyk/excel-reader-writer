package tk.aizydorczyk.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Data
public class Header {
	private String headerName;
	private Long rowPosition;
	private Long startColumnPosition;
	private Long endColumnPosition;
	private Header upperHeader;
	private List<Header> bottomHeaders = new ArrayList<>();
	private List<Cell> cells;

	public boolean isMainHeader() {
		return isNull(this.upperHeader);
	}

	public boolean isOverData() {
		return nonNull(this.cells);
	}

	public boolean notOverData() {
		return isNull(this.cells);
	}

	public Long getWidth() {
		if (isOverData()) {
			return 1L;
		} else {
			return bottomHeaders
					.stream()
					.mapToLong(Header::getWidth)
					.sum();
		}
	}

}
