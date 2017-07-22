package tk.aizydorczyk.model;

import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;

@ExcelGroup
public class LenderDto {
    @ExcelColumn
    private Long id;
    @ExcelColumn
    private String firstName;
    @ExcelColumn
    private String lastName;
}
