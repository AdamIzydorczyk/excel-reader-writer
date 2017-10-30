package tk.aizydorczyk.writer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.aizydorczyk.data.TestData;
import tk.aizydorczyk.model.BookDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExcelFileCreatorTest {

	private static List<BookDto> exampleDtos;

	private ExcelFileCreator excelFileCreator;

	private ExcelCellsGenerator excelCellsGenerator;

	@Before
	public void init() {
		exampleDtos = TestData.getExampleDtos();
		this.excelCellsGenerator = ExcelCellsGenerator.ofAnnotatedObjects(exampleDtos);
		this.excelFileCreator = ExcelFileCreator.ofDataCellsAndHeaders(excelCellsGenerator.getDataCells(), excelCellsGenerator.getHeaders());
	}

	@Test
	public void shouldCreateFile() {
		excelFileCreator.createFile("test", "./test.xlsx");
		Assert.assertTrue(Files.exists(Paths.get("./test.xlsx")));
	}

	@After
	public void end() throws IOException {
		Files.delete(Paths.get("./test.xlsx"));
	}

}
