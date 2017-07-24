package tk.aizydorczyk.model;

import lombok.Data;

import java.util.List;

@Data
public class Header {
    private String headerName;
    private Long rowPosition;
    private Long startColumnPosition;
    private Long endColumnPosition;
    private List<Cell> cells;
}
