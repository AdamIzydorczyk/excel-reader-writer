package tk.aizydorczyk.excel.common.annotation;

import tk.aizydorczyk.excel.common.style.ExcelStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
	String header();

	Class<? extends ExcelStyle> styleClass() default ExcelStyle.class;
}
