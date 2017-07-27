package tk.aizydorczyk.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Header {
    private String headerName;
    private Long rowPosition;
    private Long startColumnPosition;
    private Long endColumnPosition;
    private Header upperHeader;
    private List<Cell> cells;
}
