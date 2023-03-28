package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class ColorScheme implements Serializable {
    private String colorBGlight;
    private String colorBGdark;
    private String colorLighter;
    private String colorFont;

    /**
     * Setter with four params
     * @param colorBGlight the lighter color
     * @param colorBGdark the darker color
     * @param colorLighter the light color computed by a lighter version of the light
     * @param colorFont the color of the font
     */
    public ColorScheme(String colorBGlight
            , String colorBGdark, String colorLighter, String colorFont) {
        this.colorBGlight = colorBGlight;
        this.colorBGdark = colorBGdark;
        this.colorLighter = colorLighter;
        this.colorFont = colorFont;
    }
    /**
     * Setter with zero params, sets the colors to some default values
     */
    public ColorScheme(){
        this.colorBGlight = "#000000"; //black
        this.colorBGdark = "#000000"; //black
        this.colorLighter = "#121212"; // grey
        this.colorFont = "#FFFFFF"; //white
    }

    /**
     * getter for bgLight
     * @return the light color
     */
    public String getColorBGlight() {
        return colorBGlight;
    }
    /**
     * setter for bgLight
     * @param colorBGlight new colorBGlight
     */
    public void setColorBGlight(String colorBGlight) {
        this.colorBGlight = colorBGlight;
    }
    /**
     * getter for darker color
     * @return the colorBGdark
     */
    public String getColorBGdark() {
        return colorBGdark;
    }
    /**
     * setter for the darker color
     * @param colorBGdark new colorBGdark
     */
    public void setColorBGdark(String colorBGdark) {
        this.colorBGdark = colorBGdark;
    }
    /**
     * getter for the lighter color
     * @return the lighter color
     */
    public String getColorLighter() {
        return colorLighter;
    }
    /**
     * setter for a lighter color
     * @param colorLighter new colorLighter
     */
    public void setColorLighter(String colorLighter) {
        this.colorLighter = colorLighter;
    }
    /**
     * getter for font color
     * @return the font color
     */
    public String getColorFont() {
        return colorFont;
    }
    /**
     * setter for the font color
     * @param colorFont new colorfont
     */
    public void setColorFont(String colorFont) {
        this.colorFont = colorFont;
    }
    /**
     * getter for bgLight
     * @return the light color
     */
    @Override
    public boolean equals(Object obj) { return EqualsBuilder.reflectionEquals(this, obj); }

    /**
     * Hash Code method for Board
     * @return hash code
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString method for the board class
     * @return this as a String
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
