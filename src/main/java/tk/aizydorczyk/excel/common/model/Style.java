package tk.aizydorczyk.excel.common.model;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.awt.Color;

public class Style {

	public final static Style DEFAULT_HEADER_STYLE;

	static {
		DEFAULT_HEADER_STYLE = getDefaultStyle();
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

	private Style(Style dataCellsStyle, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, Color foregroundColor, Short oldFormatForegroundColorIndex, Short fontHeight, String fontName, Color fontColor, Short oldFormatFontColorIndex, Boolean isFontBold, Boolean isFontItalic, Byte fontUnderline, BorderStyle borderTop, BorderStyle borderBottom, BorderStyle borderLeft, BorderStyle borderRight, FillPatternType fillPattern) {
		this.dataCellsStyle = dataCellsStyle;
		this.horizontalAlignment = horizontalAlignment;
		this.verticalAlignment = verticalAlignment;
		this.foregroundColor = foregroundColor;
		this.oldFormatForegroundColorIndex = oldFormatForegroundColorIndex;
		this.fontHeight = fontHeight;
		this.fontName = fontName;
		this.fontColor = fontColor;
		this.oldFormatFontColorIndex = oldFormatFontColorIndex;
		this.isFontBold = isFontBold;
		this.isFontItalic = isFontItalic;
		this.fontUnderline = fontUnderline;
		this.borderTop = borderTop;
		this.borderBottom = borderBottom;
		this.borderLeft = borderLeft;
		this.borderRight = borderRight;
		this.fillPattern = fillPattern;
	}

	public Style() {
	}

	private static Style getDefaultStyle() {
		return
				builder()
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
						.foregroundColor(new Color(0, 0, 0))
						.oldFormatForegroundColorIndex(IndexedColors.WHITE.getIndex())
						.build();
	}

	private static StyleBuilder builder() {
		return new StyleBuilder();
	}

	public Style getDataCellsStyle() {
		return this.dataCellsStyle;
	}

	public void setDataCellsStyle(Style dataCellsStyle) {
		this.dataCellsStyle = dataCellsStyle;
	}

	public HorizontalAlignment getHorizontalAlignment() {
		return this.horizontalAlignment;
	}

	public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public Color getForegroundColor() {
		return this.foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public Short getOldFormatForegroundColorIndex() {
		return this.oldFormatForegroundColorIndex;
	}

	public void setOldFormatForegroundColorIndex(Short oldFormatForegroundColorIndex) {
		this.oldFormatForegroundColorIndex = oldFormatForegroundColorIndex;
	}

	public Short getFontHeight() {
		return this.fontHeight;
	}

	public void setFontHeight(Short fontHeight) {
		this.fontHeight = fontHeight;
	}

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Short getOldFormatFontColorIndex() {
		return this.oldFormatFontColorIndex;
	}

	public void setOldFormatFontColorIndex(Short oldFormatFontColorIndex) {
		this.oldFormatFontColorIndex = oldFormatFontColorIndex;
	}

	public Boolean getIsFontBold() {
		return this.isFontBold;
	}

	public void setIsFontBold(Boolean isFontBold) {
		this.isFontBold = isFontBold;
	}

	public Boolean getIsFontItalic() {
		return this.isFontItalic;
	}

	public void setIsFontItalic(Boolean isFontItalic) {
		this.isFontItalic = isFontItalic;
	}

	public Byte getFontUnderline() {
		return this.fontUnderline;
	}

	public void setFontUnderline(Byte fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public BorderStyle getBorderTop() {
		return this.borderTop;
	}

	public void setBorderTop(BorderStyle borderTop) {
		this.borderTop = borderTop;
	}

	public BorderStyle getBorderBottom() {
		return this.borderBottom;
	}

	public void setBorderBottom(BorderStyle borderBottom) {
		this.borderBottom = borderBottom;
	}

	public BorderStyle getBorderLeft() {
		return this.borderLeft;
	}

	public void setBorderLeft(BorderStyle borderLeft) {
		this.borderLeft = borderLeft;
	}

	public BorderStyle getBorderRight() {
		return this.borderRight;
	}

	public void setBorderRight(BorderStyle borderRight) {
		this.borderRight = borderRight;
	}

	public FillPatternType getFillPattern() {
		return this.fillPattern;
	}

	public void setFillPattern(FillPatternType fillPattern) {
		this.fillPattern = fillPattern;
	}

	public String toString() {
		return "Style(dataCellsStyle=" + this.getDataCellsStyle() + ", horizontalAlignment=" + this.getHorizontalAlignment() + ", verticalAlignment=" + this.getVerticalAlignment() + ", foregroundColor=" + this.getForegroundColor() + ", oldFormatForegroundColorIndex=" + this.getOldFormatForegroundColorIndex() + ", fontHeight=" + this.getFontHeight() + ", fontName=" + this.getFontName() + ", fontColor=" + this.getFontColor() + ", oldFormatFontColorIndex=" + this.getOldFormatFontColorIndex() + ", isFontBold=" + this.getIsFontBold() + ", isFontItalic=" + this.getIsFontItalic() + ", fontUnderline=" + this.getFontUnderline() + ", borderTop=" + this.getBorderTop() + ", borderBottom=" + this.getBorderBottom() + ", borderLeft=" + this.getBorderLeft() + ", borderRight=" + this.getBorderRight() + ", fillPattern=" + this.getFillPattern() + ")";
	}

	@SuppressWarnings("SameParameterValue")
	static class StyleBuilder {
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

		StyleBuilder() {
		}

		StyleBuilder dataCellsStyle(Style dataCellsStyle) {
			this.dataCellsStyle = dataCellsStyle;
			return this;
		}

		StyleBuilder horizontalAlignment(HorizontalAlignment horizontalAlignment) {
			this.horizontalAlignment = horizontalAlignment;
			return this;
		}

		StyleBuilder verticalAlignment(VerticalAlignment verticalAlignment) {
			this.verticalAlignment = verticalAlignment;
			return this;
		}

		StyleBuilder foregroundColor(Color foregroundColor) {
			this.foregroundColor = foregroundColor;
			return this;
		}

		StyleBuilder oldFormatForegroundColorIndex(Short oldFormatForegroundColorIndex) {
			this.oldFormatForegroundColorIndex = oldFormatForegroundColorIndex;
			return this;
		}

		StyleBuilder fontHeight(Short fontHeight) {
			this.fontHeight = fontHeight;
			return this;
		}

		StyleBuilder fontName(String fontName) {
			this.fontName = fontName;
			return this;
		}

		StyleBuilder fontColor(Color fontColor) {
			this.fontColor = fontColor;
			return this;
		}

		StyleBuilder oldFormatFontColorIndex(Short oldFormatFontColorIndex) {
			this.oldFormatFontColorIndex = oldFormatFontColorIndex;
			return this;
		}

		StyleBuilder isFontBold(Boolean isFontBold) {
			this.isFontBold = isFontBold;
			return this;
		}

		StyleBuilder isFontItalic(Boolean isFontItalic) {
			this.isFontItalic = isFontItalic;
			return this;
		}

		StyleBuilder fontUnderline(Byte fontUnderline) {
			this.fontUnderline = fontUnderline;
			return this;
		}

		StyleBuilder borderTop(BorderStyle borderTop) {
			this.borderTop = borderTop;
			return this;
		}

		StyleBuilder borderBottom(BorderStyle borderBottom) {
			this.borderBottom = borderBottom;
			return this;
		}

		StyleBuilder borderLeft(BorderStyle borderLeft) {
			this.borderLeft = borderLeft;
			return this;
		}

		StyleBuilder borderRight(BorderStyle borderRight) {
			this.borderRight = borderRight;
			return this;
		}

		StyleBuilder fillPattern(FillPatternType fillPattern) {
			this.fillPattern = fillPattern;
			return this;
		}

		Style build() {
			return new Style(dataCellsStyle, horizontalAlignment, verticalAlignment, foregroundColor, oldFormatForegroundColorIndex, fontHeight, fontName, fontColor, oldFormatFontColorIndex, isFontBold, isFontItalic, fontUnderline, borderTop, borderBottom, borderLeft, borderRight, fillPattern);
		}
	}
}
