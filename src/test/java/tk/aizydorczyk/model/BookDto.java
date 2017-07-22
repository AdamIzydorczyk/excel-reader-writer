package tk.aizydorczyk.model;

import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;

import java.time.LocalDate;
import java.util.List;

@ExcelGroup
public class BookDto {

    @ExcelColumn
    private Long id;
    @ExcelColumn
    private String name;
    @ExcelColumn
    private LocalDate releaseDate;
    @ExcelColumn(complex = true)
    private AuthorDto author;
    @ExcelColumn(complex = true)
    private List<LenderDto> lenders;

}
