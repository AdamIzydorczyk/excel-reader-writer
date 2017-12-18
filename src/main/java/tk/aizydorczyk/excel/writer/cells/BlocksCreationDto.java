package tk.aizydorczyk.excel.writer.cells;

import lombok.Builder;
import lombok.Data;
import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;

@Data
@Builder
final class BlocksCreationDto {
	private Field field;
	private Object untypedObject;
	private Header header;
}