package br.com.margel.visualizacao.utils;

import java.awt.EventQueue;
import java.awt.Window;

import br.com.margel.visualizacao.componentes.OptionPane;

public class WindowUtils {

	public static void openWindow(final Class<? extends Window> windowClass){
		openWindow(null,windowClass);
	}
	
	public static void openWindow(final Window windowClosing, final Class<? extends Window> windowClass){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					windowClass.newInstance().setVisible(true);
					if(windowClosing!=null){
						windowClosing.dispose();
					}
				} catch (Throwable e) {
					OptionPane.showError(e);
				}
			}
		});
	}
}
