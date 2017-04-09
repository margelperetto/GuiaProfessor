package br.com.margel.gp.view.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class JNumberField extends JTextField {
	private Format format;
	private Double literalValue;
	private String oldText;
	private String newText;

	public JNumberField() {
		this("###,##0.00");
	}
	
	public JNumberField(String format) {
		this(new DecimalFormat(format));
	}
	
	public JNumberField(Format format){
		this.format = format;
		initComponent();
		setText("0");
	}
	
	public void onFocusLost() {
		newText = getText();
		if(getText().isEmpty())
			setText("0");
		else if(getText().substring(getText().length()-1, getText().length()).equals("."))
			setText(getText()+"0");
		else{
			if(!oldText.equals(newText))
				setText(getText());
			else{
				double v = Double.parseDouble(newText.replace(",", "."));
				if(format!=null){
					superSetText(format.format(v));
				}
			}

		}
	}
	
	public void onFocusGained() {
		superSetText(getText().replace(".", ""));
		oldText = getText();
		selectAll();
	}

	private void initComponent() {
		setDocument(new NumberDocument());
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				onFocusLost();
			}
			@Override
			public void focusGained(FocusEvent e) {
				onFocusGained();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(!getText().isEmpty()&&!getText().equals("-")){
					double v = Double.parseDouble(getText().replace(",", "."));
					literalValue = v;
				}else{
					literalValue = 0D;
				}
			}
		});
	}

	private void superSetText(String t){
		super.setText(t);
	}

	public synchronized double getValue(){
		return literalValue;
	}
	
	public synchronized void setValue(double value){
		setText(String.valueOf(value));
	}
	
	public synchronized BigDecimal getBigDecimal() {
		return BigDecimal.valueOf(literalValue);
	}
	
	public synchronized void setValue(BigDecimal value) {
		if (value == null)
			setText("0");
		else
			setText(value.toString());
	}
	
	@Deprecated
	@Override
	public void setText(String t) {
		try {
			if(t.isEmpty()||t.equals("-"))
				t = "0";
			t = t.replace(t.contains(",")?".":"","").replace(",", ".");
			double v = Double.parseDouble(t);
			if(format!=null){
				literalValue = v;
				super.setText(format.format(v));
			}else
				super.setText(String.valueOf(v));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Format getFormat() {
		return format;
	}
	
	public void setFormat(Format format) {
		this.format = format;
		setText(getText());
	}
	
	//PLAIN DOCUMENT
	private class NumberDocument extends PlainDocument  {  

		public NumberDocument() {  
			super(); 
		}  

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {  
			if(str == null || str.isEmpty())
				return;
			for(int i = str.length(); i>0; i--){
				if (
						str.substring(i-1, i).equals("0")||
						str.substring(i-1, i).equals("1")||
						str.substring(i-1, i).equals("2")||
						str.substring(i-1, i).equals("3")||
						str.substring(i-1, i).equals("4")||
						str.substring(i-1, i).equals("5")||
						str.substring(i-1, i).equals("6")||
						str.substring(i-1, i).equals("7")||
						str.substring(i-1, i).equals("8")||
						str.substring(i-1, i).equals("9")||
						str.substring(i-1, i).equals(".")||
						str.substring(i-1, i).equals(",")||
						str.substring(i-1, i).equals("-")
						) {
					if(str.substring(i-1, i).equals(",")||str.substring(i-1, i).equals(".")&&str.length()==1){
						if(!getText().isEmpty()&&!getText().contains(","))
							super.insertString(offset, ",", attr);
						else if(getText().isEmpty())
							super.insertString(offset, "0,", attr);
					}else if(str.substring(i-1, i).equals("-")){
						if(!getText().contains("-")&&offset==0)
							super.insertString(offset,str.substring(i-1, i), attr); 
					}else
						super.insertString(offset,str.substring(i-1, i), attr);  
				}
			}
			return;  
		}

		private String getText() {
			try {
				return getText(0, getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			return null;
		}
	}  
}