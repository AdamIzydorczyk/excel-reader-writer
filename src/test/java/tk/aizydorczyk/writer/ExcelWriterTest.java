package tk.aizydorczyk.writer;

import org.junit.Assert;
import org.junit.Test;
import tk.aizydorczyk.api.ExcelWriter;

public class ExcelWriterTest {

    @Test
    public void objectShouldByCreated(){
        ExcelWriter excelWriter = new ExcelWriter();
        Assert.assertNotNull(excelWriter);
    }

}
