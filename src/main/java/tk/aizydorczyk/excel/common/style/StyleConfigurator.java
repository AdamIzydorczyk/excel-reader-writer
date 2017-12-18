package tk.aizydorczyk.excel.common.style;

public interface StyleConfigurator {
	CellStyleConfigurator headerConfig();

	CellStyleConfigurator dataCellsConfig();

	void end();
}
