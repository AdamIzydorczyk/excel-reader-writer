package tk.aizydorczyk.excel.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.aizydorczyk.excel.common.enums.DataBlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Data
@EqualsAndHashCode(exclude = "bottomHeaders")
public class Header {
	private String headerName;
	private int rowPosition = -1;
	private int startColumnPosition = -1;
	private int endColumnPosition = -1;
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

	public int getWidth() {
		if (isOverData()) {
			return 1;
		} else {
			return bottomHeaders
					.stream()
					.mapToInt(Header::getWidth)
					.sum();
		}
	}

	public DataBlockType getDataBlockType() {
		return DataBlockType.getTypeByHeader(this);
	}

	public Stream<Header> streamHeaders() {
		return Stream.concat(
				Stream.of(this),
				bottomHeaders.stream().flatMap(Header::streamHeaders));
	}

	@Override
	public String toString() {
		return "Header{" +
				"headerName='" + headerName + '\'' +
				'}';
	}
}
