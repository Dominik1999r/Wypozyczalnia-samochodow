package com.cielicki.gui.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;

public class Util {
	/**
	 * Rozmiar ekranu.
	 */
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	
	/**
	 * Wyœrodkowuje okno.
	 * 
	 * @param frame Okno do wyœrodkowania.
	 */
	public static void centerWindow(Frame frame) {
		frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
	}
	
	public static String dataFormatString = "dd.MM.yyyy";
	
	/**
	 * Format daty.
	 */
	public static SimpleDateFormat df = new SimpleDateFormat(dataFormatString);
}
