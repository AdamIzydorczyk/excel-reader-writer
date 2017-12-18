package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.DataHeaderStyle;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

@SpreadSheetGroup(header = "Author", styleClass = NonDataHeaderStyle.class)
@Data
@Builder
public class AuthorDto {
	@SpreadSheetColumn(header = "AUTHOR_ID", styleClass = DataHeaderStyle.class)
	private Long id;
	@SpreadSheetColumn(header = "AUTHOR_FIRST_NAME", styleClass = DataHeaderStyle.class)
	private String firstName;
	@SpreadSheetColumn(header = "AUTHOR_LAST_NAME", styleClass = DataHeaderStyle.class)
	private String lastName;
}
