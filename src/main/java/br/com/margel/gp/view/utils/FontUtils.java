package br.com.margel.gp.view.utils;

import java.awt.Color;

import javax.swing.JComponent;

public class FontUtils {

	public static void derive(JComponent comp, float size){
		derive(comp, comp.getFont().getStyle(), comp.getFont().getSize(), comp.getForeground());
	}
	
	public static void derive(JComponent comp, int style){
		derive(comp, style, comp.getFont().getSize(), comp.getForeground());
	}
	
	public static void derive(JComponent comp, int style, float size){
		derive(comp, style, size, comp.getForeground());
	}
	
	public static void derive(JComponent comp, int style, float size, Color foregroud){
		comp.setFont(comp.getFont().deriveFont(style,size));
		comp.setForeground(foregroud);
	}
}
