package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.common.annotation.ExcelColumn;
import tk.aizydorczyk.excel.common.annotation.ExcelGroup;

import java.time.LocalDate;
import java.util.List;

@ExcelGroup(header = "Book")
@Data
@Builder
public class BookDto {

	@ExcelColumn(header = "BOOK_ID")
	private Long id;
	@ExcelColumn(header = "BOOK_NAME")
	private String name;
	@ExcelColumn(header = "RELEASE_DATE")
	private LocalDate releaseDate;
	@ExcelColumn(header = "Author")
	private AuthorDto author;
	@ExcelColumn(header = "Lender")
	private List<LenderDto> lenders;
	@ExcelColumn(header = "strings")
	private List<String> strings;
}
