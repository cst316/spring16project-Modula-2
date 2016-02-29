package net.sf.memoranda.util;

import java.awt.Color;

public class ColorScheme {
	private static ColorSchemeEnum currentScheme = ColorSchemeEnum.BASIC;
	
	public static enum ColorEnum {
        LIGHT_RED(255,0,0), RED(192,0,0), DARK_RED(128,0,0),
        LIGHT_GREEN(0,256,0), GREEN(0,192,0), DARK_GREEN(0,128,0),
        LIGHT_BLUE(0,0,256), BLUE(0,0,192), DARK_BLUE(0,0,128),
        LIGHT_ORANGE(255,102,0), ORANGE(255,102,0), DARK_ORANGE(192,88,0),
        LIGHT_YELLOW(255,204,0), YELLOW(255,204,0), DARK_YELLOW(192,150,0),
        LIGHT_PURPLE(136,0,182), PURPLE(102,0,153), DARK_PURPLE(78,0,124),
        BLACK(0,0,0), WHITE(255,255,255), GREY(127,127,127);

        private int red;
        private int green;
        private int blue;

        private ColorEnum(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
        }

        public Color getColor() {
            return new Color(red, green, blue);
        }
    }

	public static enum ColorSchemeEnum {
		BASIC(ColorEnum.RED.getColor(), ColorEnum.GREEN.getColor(), ColorEnum.DARK_GREEN.getColor(), ColorEnum.DARK_YELLOW.getColor(), ColorEnum.BLUE.getColor(),ColorEnum.BLACK.getColor());
		
	    public Color mainColor;
	    public Color secondaryColor;
	    public Color tertiaryColor;
	    public Color headerColor;
	    public Color highlightColor;
	    public Color textColor;
	    
	    private ColorSchemeEnum(
	    		Color mainColor, 
	    		Color secondaryColor, 
	    		Color tertiaryColor,
	    		Color headerColor,
	    		Color highlightColor,
	    		Color textColor
	    ) {
	        this.mainColor = mainColor;
	        this.secondaryColor = secondaryColor;
	        this.tertiaryColor = tertiaryColor;
	        this.headerColor = headerColor;
	        this.highlightColor = highlightColor;
	        this.textColor = textColor;
	    }
	}	

}
