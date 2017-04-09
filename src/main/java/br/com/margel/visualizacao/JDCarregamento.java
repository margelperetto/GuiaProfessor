package br.com.margel.visualizacao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import br.com.margel.controle.bd.Db;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import br.com.margel.visualizacao.componentes.OptionPane;
import br.com.margel.visualizacao.utils.FontUtils;
import br.com.margel.visualizacao.utils.IconUtils;
import br.com.margel.visualizacao.utils.WindowUtils;

@SuppressWarnings("serial")
public class JDCarregamento extends JDialog{

	public JDCarregamento() {

		JLabel jlTitulo = new JLabel("Iniciando Guia do professor...", JLabel.CENTER);
		FontUtils.derive(jlTitulo, Font.PLAIN, 22f, Color.LIGHT_GRAY);
		
		getContentPane().setBackground(Color.WHITE);
		setLayout(new MigLayout(new LC().insetsAll("80")));
		add(new JLabel(IconUtils.getIcon("loading.gif")), new CC().wrap());
		add(jlTitulo, new CC().alignX("center"));
		
		setUndecorated(true);
		pack();
		setIconImage(IconUtils.getWindowIcon());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		carregarTelaPrincipal();
	}

	private void carregarTelaPrincipal() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				System.out.println("Conectando com o banco de dados...");
				Db.em();
				return null;
			}
			@Override
			protected void done() {
				try {
					get();
					System.out.println("Acesso ao banco OK! Iniciando tela principal...");
					WindowUtils.openWindow(JDCarregamento.this, TelaPrincipal.class);
				} catch (Exception e) {
					OptionPane.showError(e);
				}
			}
		}.execute();
	}
	
}