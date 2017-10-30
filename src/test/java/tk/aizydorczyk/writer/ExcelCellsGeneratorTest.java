package tk.aizydorczyk.writer;

import org.junit.Before;
import org.junit.Test;
import tk.aizydorczyk.data.TestData;
import tk.aizydorczyk.model.BookDto;
import tk.aizydorczyk.model.DataCell;
import tk.aizydorczyk.model.Header;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ExcelCellsGeneratorTest {


	private List<String> listOfExpectedHeadersNames;

	private ExcelCellsGenerator excelCellsGenerator;

	private static List<BookDto> exampleDtos;

	@Before
	public void init() {
		exampleDtos = TestData.getExampleDtos();
		listOfExpectedHeadersNames = Arrays.asList("Book", "BOOK_ID", "BOOK_NAME", "RELEASE_DATE", "Author", "AUTHOR_ID", "AUTHOR_FIRST_NAME", "AUTHOR_LAST_NAME", "Lender", "LENDER_ID", "LENDER_FIRST_NAME", "LENDER_LAST_NAME");
		this.excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(exampleDtos);
	}

	@Test
	public void shouldInitialize12Headers() {
		List<Header> headers = excelCellsGenerator.getHeaders();
		assertEquals(12L, headers.size());
	}

	@Test
	public void shouldKeepRightOrder() {
		List<Header> headers = excelCellsGenerator.getHeaders();

		List<String> listOfHeadersNames = headers.stream()
				.map(Header::getHeaderName)
				.collect(Collectors.toList());

		assertThat(listOfExpectedHeadersNames, is(listOfHeadersNames));
	}

	@Test
	public void shouldCalculateHeadersCoordinates() {
		List<Header> headers = excelCellsGenerator.getHeaders();

		Header book = headers.get(0);
		Header bookId = headers.get(1);
		Header bookName = headers.get(2);
		Header releaseDate = headers.get(3);
		Header author = headers.get(4);
		Header authorId = headers.get(5);
		Header authorFirstName = headers.get(6);
		Header authorLastName = headers.get(7);
		Header lender = headers.get(8);
		Header lenderId = headers.get(9);
		Header lenderFirstName = headers.get(10);
		Header lenderLastName = headers.get(11);

		assertEquals(Long.valueOf(0), book.getRowPosition());
		assertEquals(Long.valueOf(0), book.getStartColumnPosition());
		assertEquals(Long.valueOf(8), book.getEndColumnPosition());

		assertEquals(Long.valueOf(2), bookId.getRowPosition());
		assertEquals(Long.valueOf(0), bookId.getStartColumnPosition());
		assertEquals(Long.valueOf(0), bookId.getEndColumnPosition());

		assertEquals(Long.valueOf(2), bookName.getRowPosition());
		assertEquals(Long.valueOf(1), bookName.getStartColumnPosition());
		assertEquals(Long.valueOf(1), bookName.getEndColumnPosition());

		assertEquals(Long.valueOf(2), releaseDate.getRowPosition());
		assertEquals(Long.valueOf(2), releaseDate.getStartColumnPosition());
		assertEquals(Long.valueOf(2), releaseDate.getEndColumnPosition());

		assertEquals(Long.valueOf(1), author.getRowPosition());
		assertEquals(Long.valueOf(3), author.getStartColumnPosition());
		assertEquals(Long.valueOf(6), author.getEndColumnPosition());

		assertEquals(Long.valueOf(2), authorId.getRowPosition());
		assertEquals(Long.valueOf(3), authorId.getStartColumnPosition());
		assertEquals(Long.valueOf(3), authorId.getEndColumnPosition());

		assertEquals(Long.valueOf(2), authorFirstName.getRowPosition());
		assertEquals(Long.valueOf(4), authorFirstName.getStartColumnPosition());
		assertEquals(Long.valueOf(4), authorFirstName.getEndColumnPosition());

		assertEquals(Long.valueOf(2), authorLastName.getRowPosition());
		assertEquals(Long.valueOf(5), authorLastName.getStartColumnPosition());
		assertEquals(Long.valueOf(5), authorLastName.getEndColumnPosition());

		assertEquals(Long.valueOf(1), lender.getRowPosition());
		assertEquals(Long.valueOf(6), lender.getStartColumnPosition());
		assertEquals(Long.valueOf(9), lender.getEndColumnPosition());

		assertEquals(Long.valueOf(2), lenderId.getRowPosition());
		assertEquals(Long.valueOf(6), lenderId.getStartColumnPosition());
		assertEquals(Long.valueOf(6), lenderId.getEndColumnPosition());

		assertEquals(Long.valueOf(2), lenderFirstName.getRowPosition());
		assertEquals(Long.valueOf(7), lenderFirstName.getStartColumnPosition());
		assertEquals(Long.valueOf(7), lenderFirstName.getEndColumnPosition());

		assertEquals(Long.valueOf(2), lenderLastName.getRowPosition());
		assertEquals(Long.valueOf(8), lenderLastName.getStartColumnPosition());
		assertEquals(Long.valueOf(8), lenderLastName.getEndColumnPosition());
	}


	@Test
	public void shouldGenerate27DataCells() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		assertEquals(27, dataCells.size());
	}


	@Test
	public void shouldGenerateDataCellsOnCorrectRowPositions() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		long dataCellsOnThirdRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell -> dataCell.getRowPosition().equals(3L))
				.count();
		long dataCellsOnSevenRow = excelCellsGenerator.getDataCells().stream()
				.filter(dataCell -> dataCell.getRowPosition().equals(7L))
				.count();
		assertEquals(9, dataCellsOnThirdRow);
		assertEquals(3, dataCellsOnSevenRow);
	}

	@Test
	public void shouldGenerateDataCellsWithCorrectHeaders() {
		List<DataCell> dataCells = excelCellsGenerator.getDataCells();
		long quantityOfBookTestName2Header = dataCells.stream()
				.filter(dataCell -> dataCell.getHeader().getHeaderName().equals("BOOK_NAME"))
				.count();
		long quantityOfAuthorTestName2Header = dataCells.stream()
				.filter(dataCell -> dataCell.getHeader().getHeaderName().equals("LENDER_ID"))
				.count();
		assertEquals(2, quantityOfBookTestName2Header);
		assertEquals(5, quantityOfAuthorTestName2Header);
	}

}
