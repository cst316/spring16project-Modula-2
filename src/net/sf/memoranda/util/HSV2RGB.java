package net.sf.memoranda.util;

// HSV modified from https://github.com/michalharakal/ledomatic-adk

import java.awt.Color;

public class HSV2RGB {
	 private HSV2RGB() {
	 }

	    /**
	     * Converts a color from HSV to RGB
	     * @param hsv
	     * @return
	     */
	    static public Color convert(HSV hsv) {
	        float h;
	        float f, p, q, t;
	        int i;
	        
	        float r = 0;
	        float g = 0;
	        float b = 0;

	        h = hsv.getH();
	        if (hsv.getS() != 0) {
	            if (h == 360)
	                h = 0;
	            h /= 60.0;
	            i = (int) h;
	            f = h - i;
	            p = hsv.getV() * (1 - hsv.getS());
	            q = hsv.getV() * (1 - (hsv.getS() * f));
	            t = hsv.getV() * (1 - hsv.getS() * (1 - f));
	            switch (i) {
	                case 0:
	                    r = hsv.getV();
	                    g = t;
	                    b = p;
	                    break;
	                case 1:
	                    r = q;
	                    g = hsv.getV();
	                    b = p;
	                    break;
	                case 2:
	                    r = p;
	                    g = hsv.getV();
	                    b = t;
	                    break;
	                case 3:
	                    r = p;
	                    g = q;
	                    b = hsv.getV();
	                    break;
	                case 4:
	                    r = t;
	                    g = p;
	                    b = hsv.getV();
	                    break;
	                case 5:
	                    r = hsv.getV();
	                    g = p;
	                    b = q;
	                    break;
	                default:
	                    r = 1.0f;
	                    g = 1.0f;
	                    b = 1.0f;
	                    break;
	            }
	        }
	        return new Color(r,g,b);
	    }

}