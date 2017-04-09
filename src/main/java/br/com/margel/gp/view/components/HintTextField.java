package br.com.margel.gp.view.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HintTextField extends JTextField implements FocusListener {

	private final String hint;
	private boolean showingHint;
	private Color hintForeground = Color.LIGHT_GRAY;
	private Color foreground = Color.LIGHT_GRAY;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		foreground = getForeground();
		super.setForeground(hintForeground);
		showingHint = true;
		addFocusListener(this);
	}

	@Override
	public void setForeground(Color fg) {
		this.foreground = fg;
		if(showingHint){
			super.setForeground(hintForeground);
		}else{
			super.setForeground(fg);
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		super.setForeground(foreground);
		if(this.getText().isEmpty() && isEditable() && isEnabled()) {
			super.setText("");
			showingHint = false;
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
			super.setForeground(hintForeground);
		}
	}
	
	@Override
	public void setText(String t) {
		if(t == null || t.isEmpty()){
			showingHint = true;
			super.setText(hint);
			super.setForeground(hintForeground);
		}else{
			showingHint = false;
			super.setText(t);
			super.setForeground(foreground);
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}

	public Color getHintForeground() {
		return hintForeground;
	}

	public void setHintForeground(Color hintForeground) {
		this.hintForeground = hintForeground;
		if(showingHint){
			super.setForeground(hintForeground);
		}
	}
}