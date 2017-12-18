package tk.aizydorczyk.excel.common.model;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

@SpreadSheetGroup(header = "Lender", styleClass = NonDataHeaderStyle.class)
@Data
@Builder
public class LenderDto {
	@SpreadSheetColumn(header = "LENDER_ID")
	private Long id;
	@SpreadSheetColumn(header = "LENDER_FIRST_NAME")
	private String firstName;
	@SpreadSheetColumn(header = "LENDER_LAST_NAME")
	private String lastName;
}
