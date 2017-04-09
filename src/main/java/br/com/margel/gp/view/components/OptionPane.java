package br.com.margel.gp.view.components;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OptionPane {

	public static void showMessage(String msg){
		JOptionPane.showMessageDialog(getWindow(), msg);
	}
	
	public static void showWarning(String msg){
		JOptionPane.showMessageDialog(getWindow(), msg, "Atenção!",JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showError(String msg){
		JOptionPane.showMessageDialog(getWindow(), msg, "Erro!",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showError(Throwable e) {
		e.printStackTrace();
		showError(e.getMessage());
	}
	
	public static boolean showConfirmOkCancel(String msg){
		return JOptionPane.showConfirmDialog(getWindow(), msg, "Confirmar", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION;
	}
	
	public static boolean showConfirmOkCancelWarning(String msg){
		return JOptionPane.showConfirmDialog(getWindow(), msg, "Confirmar", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION;
	}
	
	public static boolean showConfirmSimNao(String msg){
		return JOptionPane.showConfirmDialog(getWindow(), msg, "Confirmar", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	public static boolean showConfirmSimNaoWarning(String msg){
		return JOptionPane.showConfirmDialog(getWindow(), msg, "Confirmar", 
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
	}
	
	private static Window getWindow(){
		for(Window w : Window.getWindows()){
			if(w.isFocusOwner()){
				if(w instanceof JDialog){
					return ((JDialog)w).getOwner();
				}
				System.out.println(((JFrame)w).getTitle());
				return w;
			}
		}
		return null;
	}

}
