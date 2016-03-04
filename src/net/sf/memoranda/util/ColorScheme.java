package net.sf.memoranda.util;

import java.awt.Color;
import java.util.HashMap;

public class ColorScheme {
	private static HashMap<String,Color> currentScheme = new HashMap<String,Color>();
	private static int color;
	
	public static Color getColor(String color) {
		if(!currentScheme.containsKey("frame_primary")) {		
			String config = (String) Configuration.get("APPEAR_COLOR");
			if(config.equals("")) config = "220";
			ColorScheme.changeColor(Integer.parseInt(config));
		}
		if(!currentScheme.containsKey(color)) {
			return Color.MAGENTA;
		}
		
        return currentScheme.get(color);
    }
	
	public static float modHue(float hue, float amt) {
		return ( ((hue*360)+amt)%360 / 360);
	}

	public static int getColor() {
		return color;
	}
	
	public static void changeColor(int input) {
		if(input < -1) input = -1;
		else if(input > 359) input = 359;
		
		color = input;

		if(input == -1) {
			currentScheme.put("frame_primary", Color.WHITE);
			currentScheme.put("frame_secondary", Color.GRAY);
			currentScheme.put("frame_tertiary", Color.LIGHT_GRAY);
			currentScheme.put("frame_background", Color.WHITE);
			currentScheme.put("frame_highlight", Color.RED);
			currentScheme.put("frame_text", Color.BLACK);
			currentScheme.put("button_primary", Color.WHITE);
			currentScheme.put("taskbar_primary", Color.WHITE);
			currentScheme.put("taskbar_highlight", new Color(215, 225, 250));
			currentScheme.put("taskbar_text", Color.BLACK);
		} else {
			float hue = (input/360f);
			currentScheme.put("frame_primary", Color.getHSBColor(hue, 1f, 1f));
			currentScheme.put("frame_secondary", Color.getHSBColor(hue, 0.25f, 1f));
			currentScheme.put("frame_tertiary", Color.getHSBColor(hue, 0.125f, 1f));
			currentScheme.put("frame_background", Color.WHITE);
			currentScheme.put("frame_highlight", Color.getHSBColor(hue, 1f, 1f));
			currentScheme.put("frame_text", Color.BLACK);
			currentScheme.put("button_primary", Color.getHSBColor(hue, 0.5f, 1f));
			currentScheme.put("taskbar_primary", Color.getHSBColor(hue, 0.75f, 0.5f));
			currentScheme.put("taskbar_highlight", Color.getHSBColor(hue, 0.75f, 0.75f));
			currentScheme.put("taskbar_text", Color.WHITE);
		}
	}
}
