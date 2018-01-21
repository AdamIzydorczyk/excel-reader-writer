package tk.aizydorczyk.excel.common.model;

import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.DataHeaderStyle;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

@SpreadSheetGroup(header = "Author", styleClass = NonDataHeaderStyle.class)
public class AuthorDto {
	@SpreadSheetColumn(header = "AUTHOR_ID", styleClass = DataHeaderStyle.class)
	private Long id;
	@SpreadSheetColumn(header = "AUTHOR_FIRST_NAME", styleClass = DataHeaderStyle.class)
	private String firstName;
	@SpreadSheetColumn(header = "AUTHOR_LAST_NAME", styleClass = DataHeaderStyle.class)
	private String lastName;

	public AuthorDto(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static AuthorDtoBuilder builder() {
		return new AuthorDtoBuilder();
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
		return "AuthorDto(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ")";
	}

	public static class AuthorDtoBuilder {
		private Long id;
		private String firstName;
		private String lastName;

		AuthorDtoBuilder() {
		}

		public AuthorDto.AuthorDtoBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public AuthorDto.AuthorDtoBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public AuthorDto.AuthorDtoBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public AuthorDto build() {
			return new AuthorDto(id, firstName, lastName);
		}

		public String toString() {
			return "AuthorDto.AuthorDtoBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ")";
		}
	}
}
