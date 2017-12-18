package tk.aizydorczyk.excel.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.awt.Color;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Style {

	public final static Style DEFAULT_HEADER_STYLE;

	static {
		DEFAULT_HEADER_STYLE = getDefaultStyle();
	}

	private static Style getDefaultStyle() {
		return Style
				.builder()
				.horizontalAlignment(HorizontalAlignment.LEFT)
				.verticalAlignment(VerticalAlignment.CENTER)
				.borderTop(BorderStyle.NONE)
				.borderBottom(BorderStyle.NONE)
				.borderLeft(BorderStyle.NONE)
				.borderRight(BorderStyle.NONE)
				.fontHeight((short) 12)
				.fontColor(new Color(0, 0, 0))
				.oldFormatFontColorIndex(IndexedColors.WHITE.getIndex())
				.isFontBold(false)
				.isFontItalic(false)
				.fontUnderline(Font.U_NONE)
				.fillPattern(FillPatternType.NO_FILL)
				.foregroundColor(new Color(0,0,0))
				.oldFormatForegroundColorIndex(IndexedColors.WHITE.getIndex())
				.build();
	}

	private Style dataCellsStyle;

	private HorizontalAlignment horizontalAlignment;
	private VerticalAlignment verticalAlignment;
	private Color foregroundColor;
	private Short oldFormatForegroundColorIndex;

	private Short fontHeight;
	private String fontName;
	private Color fontColor;
	private Short oldFormatFontColorIndex;
	private Boolean isFontBold;
	private Boolean isFontItalic;
	private Byte fontUnderline;

	private BorderStyle borderTop;
	private BorderStyle borderBottom;
	private BorderStyle borderLeft;
	private BorderStyle borderRight;

	private FillPatternType fillPattern;
}
