package br.com.margel.gp.view;

import javax.swing.UIManager;

import br.com.margel.gp.view.components.OptionPane;
import br.com.margel.gp.view.utils.WindowUtils;

public class XStartApp {

	public static void main(String[] args) {
		try {
			System.out.println("Iniciando... Definindo LAF...");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.out.println("LAF Ok! Iniciando sistema...");

			WindowUtils.openWindow(JDCarregamento.class);
		} catch (Throwable e) {
			OptionPane.showError(e);
		}
	}

}
