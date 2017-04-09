package visualizacao;

import javax.swing.UIManager;

import visualizacao.componentes.OptionPane;
import visualizacao.utils.WindowUtils;

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
