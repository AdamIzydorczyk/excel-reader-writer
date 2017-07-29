package tk.aizydorczyk.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Header {
	private String headerName;
	private Long rowPosition;
	private Long startColumnPosition;
	private Long endColumnPosition;
	private Header upperHeader;
	private List<Header> bottomHeaders = new ArrayList<>();
	private List<Cell> cells;

	public boolean isMainHeader(){
		return this.upperHeader == null;
	}

	public boolean isOverData(){
		return this.cells != null;
	}

	public boolean notOverData(){
		return this.cells == null;
	}

	public Long getWidth(){
		if(isOverData()){
			return 1L;
		} else {
			return bottomHeaders
					.stream()
					.mapToLong(Header::getWidth)
					.sum();
		}
	}

}
