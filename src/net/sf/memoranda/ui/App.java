package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.util.Configuration;

/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: App.java,v 1.28 2007/03/20 06:21:46 alexeya Exp $*/
public class App {
	// boolean packFrame = false;

	static AppFrame frame = null;
	
	public static final String GUIDE_URL = "http://www.public.asu.edu/~agamezli/CST316/Memoranda_Revamped.pdf"; //"http://memoranda.sourceforge.net/guide.html";
	public static final String BUGS_TRACKER_URL = "https://github.com/cst316/spring16project-Modula-2/issues";
	public static final String WEBSITE_URL = "https://github.com/cst316/spring16project-Modula-2";

	private JFrame splash = null;

	/*========================================================================*/ 
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using 
       'version' property and datestamp.*/

	public static final String VERSION_INFO = "@VERSION@";
	public static final String BUILD_INFO = "@BUILD@";
	
	/*========================================================================*/

	public static AppFrame getFrame() {
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();
		System.out.println("Memoranda version " + VERSION_INFO);
		try {
			if (Configuration.get("LOOK_AND_FEEL").equals("system"))
				UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
				UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());					
			else if (
				Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
				UIManager.setLookAndFeel(
					Configuration.get("LOOK_AND_FEEL").toString());

		} catch (Exception e) {		    
			new ExceptionDialog(e, "Error when initializing a pluggable look-and-feel. Default LF will be used.", "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
		}
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
		}

		EventsScheduler.init();
		frame = new AppFrame();
		if (fullmode) {
			init();
		}
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			splash.dispose();
	}

	void init() {
		/*
		 * if (packFrame) { frame.pack(); } else { frame.validate(); }
		 * 
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * 
		 * Dimension frameSize = frame.getSize(); if (frameSize.height >
		 * screenSize.height) { frameSize.height = screenSize.height; } if
		 * (frameSize.width > screenSize.width) { frameSize.width =
		 * screenSize.width; }
		 * 
		 * 
		 * Make the window fullscreen - On Request of users This seems not to
		 * work on sun's version 1.4.1_01 Works great with 1.4.2 !!! So update
		 * your J2RE or J2SDK.
		 */
		/* Used to maximize the screen if the JVM Version if 1.4 or higher */
		/* --------------------------------------------------------------- */
		double JVMVer =
			Double
				.valueOf(System.getProperty("java.version").substring(0, 3))
				.doubleValue();

		frame.pack();
		if (JVMVer >= 1.4) {
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			frame.setExtendedState(Frame.NORMAL);
		}
		/* --------------------------------------------------------------- */
		/* Added By Jeremy Whitlock (jcscoobyrs) 07-Nov-2003 at 15:54:24 */

		// Not needed ???
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();

	}

	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon spl =
			new ImageIcon(App.class.getResource("resources/splash.png"));
		JLabel l = new JLabel();
		l.setSize(400, 300);
		l.setIcon(spl);
		splash.getContentPane().add(l);
		splash.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splash.setLocation(
			(screenSize.width - 400) / 2,
			(screenSize.height - 300) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);
	}
	
	public static String getVersion() {
		if(App.VERSION_INFO.equals("@VERSION@")) {
			return "2.0";
		}
		return App.VERSION_INFO;
	}
	
	public static String getBuild() {
		if(App.BUILD_INFO.equals("@VERSION@")) {
			return "2.0";
		}
		return App.BUILD_INFO;
	}
}