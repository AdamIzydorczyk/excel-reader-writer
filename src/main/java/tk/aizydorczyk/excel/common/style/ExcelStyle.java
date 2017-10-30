package tk.aizydorczyk.excel.common.style;

@FunctionalInterface
public interface ExcelStyle {
	ExcelStyleBuilder.Style createStyle(ExcelStyleBuilder styleBuilder);
}
