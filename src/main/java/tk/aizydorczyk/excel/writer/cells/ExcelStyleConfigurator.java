package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.Style;
import tk.aizydorczyk.excel.common.style.CellStyleConfigurator;
import tk.aizydorczyk.excel.common.style.StyleConfigurator;

final class ExcelStyleConfigurator implements StyleConfigurator {

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

	public Style getStyle() {
		return this.style;
	}
}