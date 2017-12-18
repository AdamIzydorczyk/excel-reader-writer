package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

import java.time.LocalDate;
import java.util.List;

@SpreadSheetGroup(header = "Book", styleClass = NonDataHeaderStyle.class)
@Data
@Builder
public class BookDto {
	@SpreadSheetColumn(header = "BOOK_ID")
	private Long id;
	@SpreadSheetColumn(header = "BOOK_NAME")
	private String name;
	@SpreadSheetColumn(header = "RELEASE_DATE")
	private LocalDate releaseDate;
	@SpreadSheetColumn(header = "Author")
	private AuthorDto author;
	@SpreadSheetColumn(header = "Lender")
	private List<LenderDto> lenders;
	@SpreadSheetColumn(header = "strings")
	private List<String> strings;
}
