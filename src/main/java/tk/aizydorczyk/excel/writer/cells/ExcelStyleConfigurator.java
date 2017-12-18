package tk.aizydorczyk.excel.writer.cells;

import lombok.Getter;
import tk.aizydorczyk.excel.common.model.Style;
import tk.aizydorczyk.excel.common.style.CellStyleConfigurator;
import tk.aizydorczyk.excel.common.style.StyleConfigurator;

final class ExcelStyleConfigurator implements StyleConfigurator {

	@Getter
	private final Style style;

	private ExcelCellStyleConfigurator styleConfigurator;

	ExcelStyleConfigurator() {
		style = new Style();
	}

	@Override
	public CellStyleConfigurator headerConfig() {
		styleConfigurator = new ExcelCellStyleConfigurator(this, style);
		return styleConfigurator;
	}

	@Override
	public CellStyleConfigurator dataCellsConfig() {
		style.setDataCellsStyle(new Style());
		styleConfigurator = new ExcelCellStyleConfigurator(this, style.getDataCellsStyle());
		return styleConfigurator;
	}


	@Override
	public void end() {
	}
}