package br.com.margel.visualizacao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import br.com.margel.controle.bd.Db;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import br.com.margel.visualizacao.utils.FontUtils;
import br.com.margel.visualizacao.utils.IconUtils;
import br.com.margel.visualizacao.utils.WindowUtils;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	public static final Color DARK_GRAY = new Color(100, 100, 100);

	public TelaPrincipal() {
		setLayout(new MigLayout(new LC().insetsAll("0").gridGap("0", "0")));
		add(criarTopo(), new CC().width("100%").wrap());
		add(criarCentro(), new CC().width("100%").height("100%").wrap());
		add(criarRodape(), new CC().width("100%"));

		setJMenuBar(criarMenuBar());
		
		pack();
		setIconImage(IconUtils.getWindowIcon());
		setMinimumSize(getSize());
		setSize(new Dimension(800, getSize().height));
		setMaximumSize(getToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setTitle("Guia do professor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Db.close();
			}
		});
	}
	
	private JMenuBar criarMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(menu(" ALUNOS ",
				menuItem(" Cadastro de Alunos ", JFAlunos.class)
				));
		
		menuBar.add(menu(" TURMAS ",
				menuItem(" Cadastro de Turmas ", JFTurmas.class)
				));
		
		return menuBar;
	}
	
	private JMenu menu(String str, JMenuItem... menus){
		JMenu menu = new JMenu(str);
		menu.setIcon(IconUtils.getIcon("menu36x36.png"));
		if(menus!=null){
			for(JMenuItem i : menus){
				menu.add(i);
			}
		}
		return menu;
	}
	
	private JMenuItem menuItem(String str, Class<? extends Window> clazz){
		JMenuItem menu = new JMenuItem(str);
		menu.setIcon(IconUtils.getIcon("menuitem36x36.png"));
		if(clazz!=null){
			menu.addActionListener(new AbrirWindowAction(clazz));
		}
		return menu;
	}

	private JPanel criarTopo() {
		JLabel jlTitulo = new JLabel("Guia do Professor");
		FontUtils.derive(jlTitulo, Font.BOLD, 32f, Color.WHITE);
		JLabel jlSubTitulo = new JLabel("Um modo simples de organizar suas aulas");
		FontUtils.derive(jlSubTitulo, Font.PLAIN, 18f, Color.WHITE);
		
		JPanel jpTopo = new JPanel(new MigLayout(new LC().insetsAll("20")));
		jpTopo.setBackground(DARK_GRAY);
		jpTopo.add(new JLabel(IconUtils.getIcon("logo.png")),new CC().gap("10").spanY());
		jpTopo.add(new JSeparator(JSeparator.VERTICAL), new CC().spanY().height("0:100%:").gap("30","30"));
		jpTopo.add(jlTitulo, new CC().wrap());
		jpTopo.add(jlSubTitulo, new CC());
		return jpTopo;
	}
	
	private JComponent criarCentro() {
		JPMenu jpMenuAlunos = new JPMenu(
				"Alunos", 
				"Mantenha as informações dos alunos "
				+ "armazenadas de maneira organizada e simples", 
				JFAlunos.class);
		
		JPMenu jpMenuTurma = new JPMenu(
				"Turmas", 
				"Cadastre suas turmas, vincule os alunos e "
				+ "controle as atividades, provas, notas e "
				+ "as frequências de cada aluno", 
				JFTurmas.class);
		
		JPanel jpCentro = new JPanel(new MigLayout(new LC()
				.align("center","center").insets("20","50","20","50")));
		jpCentro.setBackground(Color.WHITE);
		jpCentro.add(jpMenuAlunos, new CC().growY());
		jpCentro.add(jpMenuTurma, new CC().growY());
		
		return jpCentro;
	}
	
	private JPanel criarRodape() {
		JLabel jlAutor = new JLabel("Desenvolvido por: Margel Douglas Peretto");
		FontUtils.derive(jlAutor, Font.PLAIN, 14f, Color.WHITE);
		
		JLabel jlVersao = new JLabel("Versão: 0.0.1");
		FontUtils.derive(jlVersao, Font.PLAIN, 14f, Color.WHITE);
		
		JPanel jpRodape = new JPanel(new MigLayout(new LC().insetsAll("15")));
		jpRodape.setBackground(DARK_GRAY);
		jpRodape.add(jlAutor, new CC().width("100%"));
		jpRodape.add(jlVersao);
		return jpRodape;
	}

	private class JPMenu extends JPanel{
		
		private JLabel jlTitulo = new JLabel("", JLabel.CENTER);
		private JEditorPane jepSubTitulo = new JEditorPane();
		private JButton jbAbrirCadastro = new JButton("ABRIR CADASTRO");
		
		public JPMenu(String titulo, String subTitulo, Class<? extends Window> clazz) {
			jlTitulo.setText(titulo);
			jepSubTitulo.setOpaque(false);
			jepSubTitulo.setEditable(false);
			jepSubTitulo.setContentType("text/html");
			jepSubTitulo.setText("<html><center>"+subTitulo+"</center></html>");
			jbAbrirCadastro.setFocusable(false);
			jbAbrirCadastro.addActionListener(new AbrirWindowAction(clazz));
			jbAbrirCadastro.setMargin(new Insets(10, 10, 10, 10));
			FontUtils.derive(jlTitulo,Font.BOLD,28f);
			FontUtils.derive(jepSubTitulo,16f);
			FontUtils.derive(jbAbrirCadastro,Font.BOLD,18f);
			
			setLayout(new MigLayout(new LC().fill().maxWidth("400").gridGapY("20").insetsAll("30")));
			add(jlTitulo, new CC().alignX("center").wrap());
			add(jepSubTitulo, new CC().growY().alignX("center").wrap());
			add(jbAbrirCadastro, new CC().width("100%"));
			
			setOpaque(true);
			setBackground(new Color(248,245,240));
			setBorder(new LineBorder(DARK_GRAY, 2, true));
		}
		
	}
	
	private class AbrirWindowAction implements ActionListener{
		private Class<? extends Window> clazz;
		public AbrirWindowAction(Class<? extends Window> clazz) {
			this.clazz = clazz;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			WindowUtils.openWindow(clazz);
		}
	}
}