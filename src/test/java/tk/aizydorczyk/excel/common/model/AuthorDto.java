package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.common.annotation.ExcelColumn;
import tk.aizydorczyk.excel.common.annotation.ExcelGroup;
import tk.aizydorczyk.excel.common.model.style.DataHeaderStyle;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

@ExcelGroup(header = "Author", styleClass = NonDataHeaderStyle.class)
@Data
@Builder
public class AuthorDto {
	@ExcelColumn(header = "AUTHOR_ID", styleClass = DataHeaderStyle.class)
	private Long id;
	@ExcelColumn(header = "AUTHOR_FIRST_NAME", styleClass = DataHeaderStyle.class)
	private String firstName;
	@ExcelColumn(header = "AUTHOR_LAST_NAME", styleClass = DataHeaderStyle.class)
	private String lastName;
}
