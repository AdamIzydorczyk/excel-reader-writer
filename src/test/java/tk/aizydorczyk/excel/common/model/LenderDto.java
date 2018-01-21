package tk.aizydorczyk.excel.common.model;

import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

@SpreadSheetGroup(header = "Lender", styleClass = NonDataHeaderStyle.class)
public class LenderDto {
	@SpreadSheetColumn(header = "LENDER_ID")
	private Long id;
	@SpreadSheetColumn(header = "LENDER_FIRST_NAME")
	private String firstName;
	@SpreadSheetColumn(header = "LENDER_LAST_NAME")
	private String lastName;

	LenderDto(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static LenderDtoBuilder builder() {
		return new LenderDtoBuilder();
	}

	public Long getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toString() {
		return "LenderDto(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ")";
	}

	public static class LenderDtoBuilder {
		private Long id;
		private String firstName;
		private String lastName;

		LenderDtoBuilder() {
		}

		public LenderDtoBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public LenderDtoBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public LenderDtoBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public LenderDto build() {
			return new LenderDto(id, firstName, lastName);
		}

		public String toString() {
			return "LenderDto.LenderDtoBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ")";
		}
	}
}
