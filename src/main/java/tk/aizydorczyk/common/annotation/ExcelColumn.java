package tk.aizydorczyk.common.annotation;

import tk.aizydorczyk.common.style.ExcelStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
	String header();

	Class<? extends ExcelStyle> styleClass() default ExcelStyle.class;
}
