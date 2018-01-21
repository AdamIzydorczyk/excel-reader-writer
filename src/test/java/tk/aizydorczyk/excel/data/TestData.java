package tk.aizydorczyk.excel.data;

import tk.aizydorczyk.excel.common.model.AuthorDto;
import tk.aizydorczyk.excel.common.model.BookDto;
import tk.aizydorczyk.excel.common.model.LenderDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public final class TestData {

	public final static String BOOK = "Book";
	public final static String BOOK_ID = "BOOK_ID";
	public final static String BOOK_NAME = "BOOK_NAME";
	public final static String RELEASE_DATE = "RELEASE_DATE";
	public final static String AUTHOR = "Author";
	public final static String AUTHOR_ID = "AUTHOR_ID";
	public final static String AUTHOR_FIRST_NAME = "AUTHOR_FIRST_NAME";
	public final static String AUTHOR_LAST_NAME = "AUTHOR_LAST_NAME";
	public final static String LENDER = "Lender";
	public final static String LENDER_ID = "LENDER_ID";
	public final static String LENDER_FIRST_NAME = "LENDER_FIRST_NAME";
	public final static String LENDER_LAST_NAME = "LENDER_LAST_NAME";
	public final static String STRINGS = "strings";

	private final static String LENDER_TEST_FNAME_1 = "LENDER_TEST_FNAME_1";
	private final static String LENDER_TEST_FNAME_2 = "LENDER_TEST_FNAME_2";
	private final static String LENDER_TEST_FNAME_3 = "LENDER_TEST_FNAME_3";
	private final static String LENDER_TEST_FNAME_4 = "LENDER_TEST_FNAME_4";
	private final static String LENDER_TEST_FNAME_5 = "LENDER_TEST_FNAME_5";
	private final static String LENDER_TEST_LNAME_1 = "LENDER_TEST_LNAME_1";
	private final static String LENDER_TEST_LNAME_2 = "LENDER_TEST_LNAME_2";
	private final static String LENDER_TEST_LNAME_3 = "LENDER_TEST_LNAME_3";
	private final static String LENDER_TEST_LNAME_4 = "LENDER_TEST_LNAME_4";
	private final static String LENDER_TEST_LNAME_5 = "LENDER_TEST_LNAME_5";
	private final static String AUTHOR_TEST_FNAME_1 = "AUTHOR_TEST_FNAME_1";
	private final static String AUTHOR_TEST_FNAME_2 = "AUTHOR_TEST_FNAME_2";
	private final static String AUTHOR_TEST_LNAME_1 = "AUTHOR_TEST_LNAME_1";
	private final static String AUTHOR_TEST_LNAME_2 = "AUTHOR_TEST_LNAME_2";
	private final static String BOOK_TEST_NAME_1 = "BOOK_TEST_NAME_1";
	private final static String BOOK_TEST_NAME_2 = "BOOK_TEST_NAME_2";

	private static List<BookDto> exampleDtos;

	static {
		LenderDto lender1 = LenderDto.builder().id(1L).firstName(LENDER_TEST_FNAME_1).lastName(LENDER_TEST_LNAME_1).build();
		LenderDto lender2 = LenderDto.builder().id(2L).firstName(LENDER_TEST_FNAME_2).lastName(LENDER_TEST_LNAME_2).build();
		AuthorDto author1 = AuthorDto.builder().id(1L).firstName(AUTHOR_TEST_FNAME_1).lastName(AUTHOR_TEST_LNAME_1).build();
		BookDto book1 = BookDto.builder().id(1L).name(BOOK_TEST_NAME_1).releaseDate(LocalDate.now()).author(author1).lenders(Arrays.asList(lender1, lender2)).build();
		LenderDto lender3 = LenderDto.builder().id(3L).firstName(LENDER_TEST_FNAME_3).lastName(LENDER_TEST_LNAME_3).build();
		LenderDto lender4 = LenderDto.builder().id(4L).firstName(LENDER_TEST_FNAME_4).lastName(LENDER_TEST_LNAME_4).build();
		LenderDto lender5 = LenderDto.builder().id(5L).firstName(LENDER_TEST_FNAME_5).lastName(LENDER_TEST_LNAME_5).build();
		AuthorDto author2 = AuthorDto.builder().id(2L).firstName(AUTHOR_TEST_FNAME_2).lastName(AUTHOR_TEST_LNAME_2).build();
		BookDto book2 = BookDto.builder().id(2L).name(BOOK_TEST_NAME_2).releaseDate(LocalDate.of(2010, 12, 12)).author(author2).lenders(Arrays.asList(lender3, lender4, lender5)).build();

		book1.setStrings(Arrays.asList("A", "B", "C", "D", "E", "F"));
		exampleDtos = Arrays.asList(book1, book2);
	}

	private TestData() {
	}

	public static List<BookDto> getExampleDtos() {
		return TestData.exampleDtos;
	}
}
