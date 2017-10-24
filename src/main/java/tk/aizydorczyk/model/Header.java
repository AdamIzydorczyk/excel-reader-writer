package tk.aizydorczyk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
@EqualsAndHashCode(exclude = "bottomHeaders")
public class Header {
	private String headerName;
	private Long rowPosition;
	private Long startColumnPosition;
	private Long endColumnPosition;
	private Header upperHeader;
	private List<Header> bottomHeaders = new ArrayList<>();
	private boolean overData;
	private boolean overCollection;

	public boolean isMainHeader() {
		return isNull(this.upperHeader);
	}

	public boolean notOverData() {
		return !overData;
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

	@Override
	public String toString() {
		return "Header{" +
				"headerName='" + headerName + '\'' +
				'}';
	}
}
