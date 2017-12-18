package tk.aizydorczyk.excel.api;

import java.util.List;

public interface SpreadSheetWriter {
	void create(List<?> annotatedObjects, String sheetName);
}
