package tk.aizydorczyk.writer;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.aizydorczyk.api.ExcelWriter;
import tk.aizydorczyk.model.AuthorDto;
import tk.aizydorczyk.model.BookDto;
import tk.aizydorczyk.model.LenderDto;
import tk.aizydorczyk.util.CoordinatesGenerator;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExcelWriterTest {

    private List<BookDto> dtos;

    @Before
    public void init(){
        LenderDto lender1 = LenderDto.builder().id(1L).firstName("LENDER_TEST_FNAME_1").lastName("LENDER_TEST_LNAME_1").build();
        LenderDto lender2 = LenderDto.builder().id(2L).firstName("LENDER_TEST_FNAME_2").lastName("LENDER_TEST_LNAME_1").build();
        AuthorDto author1 = AuthorDto.builder().id(1L).firstName("AUTHOR_TEST_FNAME_1").lastName("AUTHOR_TEST_LNAME_1").build();

        BookDto book1 = BookDto.builder().id(1L).name("BOOK_TEST_NAME_1").releaseDate(LocalDate.now()).author(author1).lenders(Arrays.asList(lender1, lender2)).build();

        LenderDto lender3 = LenderDto.builder().id(3L).firstName("LENDER_TEST_FNAME_3").lastName("LENDER_TEST_LNAME_3").build();
        LenderDto lender4 = LenderDto.builder().id(4L).firstName("LENDER_TEST_FNAME_4").lastName("LENDER_TEST_LNAME_4").build();
        LenderDto lender5 = LenderDto.builder().id(5L).firstName("LENDER_TEST_FNAME_3").lastName("LENDER_TEST_LNAME_3").build();
        AuthorDto author2 = AuthorDto.builder().id(2L).firstName("AUTHOR_TEST_FNAME_2").lastName("AUTHOR_TEST_LNAME_2").build();

        BookDto book2 = BookDto.builder().id(2L).name("BOOK_TEST_NAME_2").releaseDate(LocalDate.of(2010,12,12)).author(author2).lenders(Arrays.asList(lender3, lender4,lender5)).build();

        dtos = Arrays.asList(book1,book2);
    }


    @Test
    public void shouldCount11fields() throws IllegalAccessException {
        CoordinatesGenerator coordinatesGenerator = CoordinatesGenerator.ofObjects(dtos);
        Long numberOfAllFields = (Long) FieldUtils.getField(coordinatesGenerator.getClass(),"numberOfAllFields", true).get(coordinatesGenerator);

        Assert.assertEquals(Long.valueOf(11L),numberOfAllFields);
    }

}
