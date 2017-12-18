package tk.aizydorczyk.excel.writer;

import org.junit.Before;
import org.junit.Test;
import tk.aizydorczyk.excel.common.model.BookDto;
import tk.aizydorczyk.excel.common.model.DataCell;
import tk.aizydorczyk.excel.common.model.Header;
import tk.aizydorczyk.excel.data.TestData;
import tk.aizydorczyk.excel.writer.cells.ExcelCellsGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static tk.aizydorczyk.excel.data.TestData.AUTHOR;
import static tk.aizydorczyk.excel.data.TestData.AUTHOR_FIRST_NAME;
import static tk.aizydorczyk.excel.data.TestData.AUTHOR_ID;
import static tk.aizydorczyk.excel.data.TestData.AUTHOR_LAST_NAME;
import static tk.aizydorczyk.excel.data.TestData.BOOK;
import static tk.aizydorczyk.excel.data.TestData.BOOK_ID;
import static tk.aizydorczyk.excel.data.TestData.BOOK_NAME;
import static tk.aizydorczyk.excel.data.TestData.LENDER;
import static tk.aizydorczyk.excel.data.TestData.LENDER_FIRST_NAME;
import static tk.aizydorczyk.excel.data.TestData.LENDER_ID;
import static tk.aizydorczyk.excel.data.TestData.LENDER_LAST_NAME;
import static tk.aizydorczyk.excel.data.TestData.RELEASE_DATE;
import static tk.aizydorczyk.excel.data.TestData.STRINGS;

public final class ExcelCellsGeneratorTest {

	private List<String> listOfExpectedHeadersNames;
	private ExcelCellsGenerator excelCellsGenerator;
	private List<Header> headers;

	@Before
	public void init() {
		final List<BookDto> exampleDtos = TestData.getExampleDtos();
		listOfExpectedHeadersNames = Arrays.asList(
				BOOK,
				BOOK_ID,
				BOOK_NAME,
				RELEASE_DATE,
				AUTHOR,
				AUTHOR_ID,
				AUTHOR_FIRST_NAME,
				AUTHOR_LAST_NAME,
				LENDER,
				LENDER_ID,
				LENDER_FIRST_NAME,
				LENDER_LAST_NAME,
				STRINGS);
		this.excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(exampleDtos);
		this.headers = excelCellsGenerator.getHeaders();
	}

	@Test
	public void shouldInitialize13Headers() {
		assertEquals(13L, headers.size());
	}

	@Test
	public void shouldKeepRightOrder() {
		List<String> listOfHeadersNames = headers.stream()
				.map(Header::getHeaderName)
				.collect(Collectors.toList());
		assertThat(listOfExpectedHeadersNames, is(listOfHeadersNames));
	}

	@Test
	public void shouldCalculateBookHeaderCoordinates() {
		Header book = headers.get(0);
		assertEquals(0, book.getRowPosition());
		assertEquals(0, book.getStartColumnPosition());
		assertEquals(9, book.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateBookIdHeaderCoordinates() {
		Header bookId = headers.get(1);
		assertEquals(2, bookId.getRowPosition());
		assertEquals(0, bookId.getStartColumnPosition());
		assertEquals(0, bookId.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateBookNameHeaderCoordinates() {
		Header bookName = headers.get(2);
		assertEquals(2, bookName.getRowPosition());
		assertEquals(1, bookName.getStartColumnPosition());
		assertEquals(1, bookName.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateBookReleaseDateHeaderCoordinates() {
		Header releaseDate = headers.get(3);
		assertEquals(2, releaseDate.getRowPosition());
		assertEquals(2, releaseDate.getStartColumnPosition());
		assertEquals(2, releaseDate.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateAuthorHeaderCoordinates() {
		Header author = headers.get(4);
		assertEquals(1, author.getRowPosition());
		assertEquals(3, author.getStartColumnPosition());
		assertEquals(5, author.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateAuthorIdHeaderCoordinates() {
		Header authorId = headers.get(5);
		assertEquals(2, authorId.getRowPosition());
		assertEquals(3, authorId.getStartColumnPosition());
		assertEquals(3, authorId.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateAuthorFirsNameHeaderCoordinates() {
		Header authorFirstName = headers.get(6);
		assertEquals(2, authorFirstName.getRowPosition());
		assertEquals(4, authorFirstName.getStartColumnPosition());
		assertEquals(4, authorFirstName.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateAuthorLastNameHeaderCoordinates() {
		Header authorLastName = headers.get(7);
		assertEquals(2, authorLastName.getRowPosition());
		assertEquals(5, authorLastName.getStartColumnPosition());
		assertEquals(5, authorLastName.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateLenderHeaderCoordinates() {
		Header lender = headers.get(8);
		assertEquals(1, lender.getRowPosition());
		assertEquals(6, lender.getStartColumnPosition());
		assertEquals(8, lender.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateLenderIdHeaderCoordinates() {
		Header lenderId = headers.get(9);
		assertEquals(2, lenderId.getRowPosition());
		assertEquals(6, lenderId.getStartColumnPosition());
		assertEquals(6, lenderId.getEndColumnPosition());

	}

	@Test
	public void shouldCalculateLenderFirstNameHeaderCoordinates() {
		Header lenderFirstName = headers.get(10);
		assertEquals(2, lenderFirstName.getRowPosition());
		assertEquals(7, lenderFirstName.getStartColumnPosition());
		assertEquals(7, lenderFirstName.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateLenderLastNameHeaderCoordinates() {
		Header lenderLastName = headers.get(11);
		assertEquals(2, lenderLastName.getRowPosition());
		assertEquals(8, lenderLastName.getStartColumnPosition());
		assertEquals(8, lenderLastName.getEndColumnPosition());
	}

	@Test
	public void shouldCalculateStringsHeaderCoordinates() {
		Header strings = headers.get(12);
		assertEquals(2, strings.getRowPosition());
		assertEquals(9, strings.getStartColumnPosition());
		assertEquals(9, strings.getEndColumnPosition());
	}

	@Test
	public void shouldGenerate34DataCells() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		assertEquals(34, dataCells.size());
	}

	@Test
	public void on3RowShouldBe10Cells() {
		long dataCellsOnThirdRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell ->
						dataCell.getRowPosition() == 3)
				.count();
		assertEquals(10, dataCellsOnThirdRow);
	}

	@Test
	public void on4RowShouldBe4Cells() {
		long dataCellsOnThirdRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell ->
						dataCell.getRowPosition() == 4)
				.count();
		assertEquals(4, dataCellsOnThirdRow);
	}

	@Test
	public void on7RowShouldBe10Cells() {
		long dataCellsOnThirdRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell ->
						dataCell.getRowPosition() == 7)
				.count();
		assertEquals(1, dataCellsOnThirdRow);
	}

	@Test
	public void on9RowShouldBe10Cells() {
		long dataCellsOnThirdRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell ->
						dataCell.getRowPosition() == 9)
				.count();
		assertEquals(10, dataCellsOnThirdRow);
	}

	@Test
	public void shouldGenerate2DataCellsWithHeaderBookName() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		long quantityOfBookTestName2Header = dataCells.stream()
				.filter(dataCell ->
						dataCell.getHeader().getHeaderName().equals(BOOK_NAME))
				.count();
		assertEquals(2, quantityOfBookTestName2Header);
	}

	@Test
	public void shouldGenerate5DataCellsWithHeaderLenderId() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		long quantityOfAuthorTestName2Header = dataCells.stream()
				.filter(dataCell ->
						dataCell.getHeader().getHeaderName().equals(LENDER_ID))
				.count();
		assertEquals(5, quantityOfAuthorTestName2Header);
	}
}
