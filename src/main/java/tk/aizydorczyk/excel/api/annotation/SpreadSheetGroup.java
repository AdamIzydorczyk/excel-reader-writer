package tk.aizydorczyk.excel.api.annotation;

import tk.aizydorczyk.excel.api.SpreadSheetStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SpreadSheetGroup {
	String header();

	Class<? extends SpreadSheetStyle> styleClass() default SpreadSheetStyle.class;
}
