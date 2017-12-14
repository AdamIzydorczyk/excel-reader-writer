package tk.aizydorczyk.excel.common.style;

import lombok.Getter;
import tk.aizydorczyk.excel.common.model.Style;

public class ExcelStyleConfigurator {

	@Getter
	private Style style;

	private ExcelCellStyleConfigurator styleConfigurator;

	public ExcelStyleConfigurator() {
		style = new Style();
	}

	public ExcelCellStyleConfigurator headerConfig(){
		styleConfigurator = new ExcelCellStyleConfigurator(this, style);
		return styleConfigurator;
	}

	public ExcelCellStyleConfigurator dataCellsConfig(){
		style.setDataCellsStyle(new Style());
		styleConfigurator = new ExcelCellStyleConfigurator(this, style.getDataCellsStyle());
		return styleConfigurator;
	}


	public void end(){
	}


}
