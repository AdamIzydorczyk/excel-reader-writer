package tk.aizydorczyk.excel.writer.datablock.creationstrategy;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;

@Data
@Builder
public class BlocksCreationDto {
	private Field field;
	private Object untypedObject;
	private Header header;
}
