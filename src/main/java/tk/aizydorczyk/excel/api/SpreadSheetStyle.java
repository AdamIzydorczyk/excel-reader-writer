package tk.aizydorczyk.excel.api;

import tk.aizydorczyk.excel.common.style.StyleConfigurator;

@FunctionalInterface
public interface SpreadSheetStyle {
	void configureStyle(StyleConfigurator config);
}
