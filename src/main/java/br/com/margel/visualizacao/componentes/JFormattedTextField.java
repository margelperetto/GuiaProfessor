package br.com.margel.visualizacao.componentes;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;

@SuppressWarnings("serial")
public class JFormattedTextField extends javax.swing.JFormattedTextField {
	
	public JFormattedTextField(AbstractFormatter mask) {
		super(mask);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				setCaretPosition(0);
			}
			@Override
			public void focusLost(FocusEvent evt) {
				try {
					String text = JFormattedTextField.super.getText();
					if(getFormatter()!=null && text!=null){
						if (text.equals(getFormatter().valueToString(""))){
							JFormattedTextField.super.setValue("");
						}else{
							System.out.println(text);
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Use {@link #getFormattedText()}
	 */
	@Deprecated
	@Override
	public String getText() {
		return super.getText();
	}

	/**
	 * Retorna uma String vazia (ou nulo, se nullIfEmpty for <b>true</b>) caso o campo este somente com a máscara, caso
	 * contrário retorna o campo mascarado
	 * @return
	 */
	public String getFormattedText() {

		try {
			String text = super.getText(); 
			if (text ==null || (getFormatter()!=null && text.equals(getFormatter().valueToString(""))) ){
				return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return super.getText();
	}
	
	/**
	 * Retorna apenas o texto sem a máscara. 
	 * Nulo se setado como nullIfEmpty.
	 * @return
	 */
	public String getUnformattedText(){
		String f = getFormattedText();
		return f == null ? null : f.replaceAll( "\\D*", "" );
	}

	public static JFormattedTextField buildFoneMask(){
		return buildByMask("(##)#####-####");
	}
	public static JFormattedTextField buildCepMask(){
		return buildByMask("#####-###");
	}
	public static JFormattedTextField buildCPFMask(){
		return buildByMask("###.###.###-##");
	}
	public static JFormattedTextField buildCNPJMask(){
		return buildByMask("##.###.###/####-##");
	}
	public static JFormattedTextField buildByMask(String mask){
		try {
			return new JFormattedTextField(new MaskFormatter(mask));
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
}
