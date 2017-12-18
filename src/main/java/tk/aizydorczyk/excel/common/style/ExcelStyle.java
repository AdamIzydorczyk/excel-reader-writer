package tk.aizydorczyk.excel.common.style;

@FunctionalInterface
public interface ExcelStyle {
	void configureStyle(ExcelStyleConfigurator config);
}
