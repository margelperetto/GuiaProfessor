package br.com.margel.visualizacao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import br.com.margel.visualizacao.utils.FontUtils;
import br.com.margel.visualizacao.utils.IconUtils;

@SuppressWarnings("serial")
public class JFTurmas extends JFrame{

	public JFTurmas() {
		try {
			
			JLabel jlTitulo = new JLabel("EM DESENVOLVIMENTO");
			FontUtils.derive(jlTitulo, Font.PLAIN, 22f, Color.LIGHT_GRAY);
			
			setLayout(new MigLayout(
					new LC().minWidth("800").minHeight("600").noGrid().insets("30","10","10","10")
					));
			
			add(jlTitulo, new CC().wrap());
			add(new JSeparator(), new CC().width("0:100%:").wrap());
			
			pack();
			setIconImage(IconUtils.getWindowIcon());
			setMinimumSize(getSize());
			setLocationRelativeTo(null);
			setTitle("Turmas");
		} catch (Throwable e) {
			e.printStackTrace();;
		}
	}
}
