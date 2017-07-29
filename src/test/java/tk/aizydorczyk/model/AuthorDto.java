package tk.aizydorczyk.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.common.annotation.ExcelColumn;
import tk.aizydorczyk.common.annotation.ExcelGroup;

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
