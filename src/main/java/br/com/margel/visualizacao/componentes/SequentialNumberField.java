package br.com.margel.visualizacao.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class SequentialNumberField extends JNumberField{

	public SequentialNumberField(final int min, final int max) {
		super("###0");
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_UP && getValue()<max){
					setValue(getValue()+1);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN && getValue()>min){
					setValue(getValue()-1);
				}
				selectAll();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				double v = getValue();
				if(v<min){
					setValue(min);
				}else if(v>max){
					setValue(max);
				}
			}
		});
	}
	
	public Integer getIntValue() {
		double d = getValue();
		return (int)d;
	}

	public boolean cursorNoFinal() {
		return getCaretPosition()==getText().length();
	}
}
