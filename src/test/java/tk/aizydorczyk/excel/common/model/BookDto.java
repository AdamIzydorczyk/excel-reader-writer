package tk.aizydorczyk.excel.common.model;

import tk.aizydorczyk.excel.api.annotation.SpreadSheetColumn;
import tk.aizydorczyk.excel.api.annotation.SpreadSheetGroup;
import tk.aizydorczyk.excel.common.model.style.NonDataHeaderStyle;

import java.time.LocalDate;
import java.util.List;

@SpreadSheetGroup(header = "Book", styleClass = NonDataHeaderStyle.class)
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

	public BookDto(Long id, String name, LocalDate releaseDate, AuthorDto author, List<LenderDto> lenders, List<String> strings) {
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
		this.author = author;
		this.lenders = lenders;
		this.strings = strings;
	}

	public static BookDtoBuilder builder() {
		return new BookDtoBuilder();
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public LocalDate getReleaseDate() {
		return this.releaseDate;
	}

	public AuthorDto getAuthor() {
		return this.author;
	}

	public List<LenderDto> getLenders() {
		return this.lenders;
	}

	public List<String> getStrings() {
		return this.strings;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setAuthor(AuthorDto author) {
		this.author = author;
	}

	public void setLenders(List<LenderDto> lenders) {
		this.lenders = lenders;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}

	public String toString() {
		return "BookDto(id=" + this.getId() + ", name=" + this.getName() + ", releaseDate=" + this.getReleaseDate() + ", author=" + this.getAuthor() + ", lenders=" + this.getLenders() + ", strings=" + this.getStrings() + ")";
	}

	public static class BookDtoBuilder {
		private Long id;
		private String name;
		private LocalDate releaseDate;
		private AuthorDto author;
		private List<LenderDto> lenders;
		private List<String> strings;

		BookDtoBuilder() {
		}

		public BookDto.BookDtoBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public BookDto.BookDtoBuilder name(String name) {
			this.name = name;
			return this;
		}

		public BookDto.BookDtoBuilder releaseDate(LocalDate releaseDate) {
			this.releaseDate = releaseDate;
			return this;
		}

		public BookDto.BookDtoBuilder author(AuthorDto author) {
			this.author = author;
			return this;
		}

		public BookDto.BookDtoBuilder lenders(List<LenderDto> lenders) {
			this.lenders = lenders;
			return this;
		}

		public BookDto.BookDtoBuilder strings(List<String> strings) {
			this.strings = strings;
			return this;
		}

		public BookDto build() {
			return new BookDto(id, name, releaseDate, author, lenders, strings);
		}

		public String toString() {
			return "BookDto.BookDtoBuilder(id=" + this.id + ", name=" + this.name + ", releaseDate=" + this.releaseDate + ", author=" + this.author + ", lenders=" + this.lenders + ", strings=" + this.strings + ")";
		}
	}
}
