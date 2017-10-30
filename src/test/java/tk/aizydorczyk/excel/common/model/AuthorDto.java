package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.common.annotation.ExcelColumn;
import tk.aizydorczyk.excel.common.annotation.ExcelGroup;

@ExcelGroup(header = "Author")
@Data
@Builder
public class AuthorDto {
	@ExcelColumn(header = "AUTHOR_ID")
	private Long id;
	@ExcelColumn(header = "AUTHOR_FIRST_NAME")
	private String firstName;
	@ExcelColumn(header = "AUTHOR_LAST_NAME")
	private String lastName;
}
