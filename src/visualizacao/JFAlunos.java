package visualizacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import controle.AlunoContr;
import modelos.Aluno;
import modelos.Aluno.Genero;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import visualizacao.componentes.HintTextField;
import visualizacao.componentes.JFormattedTextField;
import visualizacao.componentes.JPLocalDate;
import visualizacao.componentes.OptionPane;
import visualizacao.models.GenericTableModel;
import visualizacao.models.TableField;
import visualizacao.models.TableFieldBuilder;
import visualizacao.utils.FontUtils;
import visualizacao.utils.IconUtils;

@SuppressWarnings("serial")
public class JFAlunos extends JFrame{
	private JTabbedPane tab = new JTabbedPane();;
	private HintTextField jtfCodigo = new HintTextField("0000");
	private HintTextField jtfNome = new HintTextField("Ex.: João Carlos da Silva");
	private HintTextField jtfCidade = new HintTextField("Cidade onde reside atualmente");
	private HintTextField jtfEmail = new HintTextField("email@email.com");
	private JFormattedTextField jftfCelular = JFormattedTextField.buildFoneMask();
	private JPLocalDate jpDataNascimento = new JPLocalDate(false);
	private HintTextField jtfDataCadastro = new HintTextField("--/--/---- --:--");
	private JComboBox<Genero> jcbGenero = new JComboBox<>(Genero.values());
	private JButton jbNovo = new JButton(" Novo ",IconUtils.getIcon("add20x20.png"));
	private JButton jbSalvar = new JButton(" Salvar ",IconUtils.getIcon("save20x20.png"));
	private JButton jbExcluir = new JButton(" Excluir ",IconUtils.getIcon("delete20x20.png"));
	
	private GenericTableModel<Aluno> tmAlunos;
	private JTable jtAlunos;
	private HintTextField jtfPesquisa = new HintTextField("Pesquise pelo nome do aluno, email ou celular");
	private JButton jbPesquisar = new JButton("Pesquisar", IconUtils.getIcon("search20x20.png"));
	private JButton jbLimparPesquisa = new JButton(IconUtils.getIcon("backspace15x15.png"));
	
	private AlunoContr controle = new AlunoContr();
	private Aluno aluno;
	
	public JFAlunos() {
		try {
			tab.add("Cadastro", criarPainelCadastro());
			tab.add("Pesquisa", criarPainelPesquisa());
			
			setLayout(new BorderLayout());
			add(tab);
			
			limparCampos();
			
			pack();
			setIconImage(IconUtils.getWindowIcon());
			setMinimumSize(getSize());
			setLocationRelativeTo(null);
			setTitle("Alunos");
			
			pesquisar();
		} catch (Throwable e) {
			OptionPane.showError(e);
		}
	}
	
	private JPanel criarPainelCadastro() throws Throwable{
		jtfDataCadastro.setEditable(false);
		jtfCodigo.setEditable(false);
		
		jbNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		jbSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				salvar();
			}
		});
		jbExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				excluir();
			}
		});
		
		JLabel jlTitulo = new JLabel("FORMULÁRIO DE ALUNO");
		FontUtils.derive(jlTitulo, Font.PLAIN, 22f, Color.LIGHT_GRAY);
		
		JLabel jlAviso = new JLabel("* = Campos Obrigatórios",JLabel.RIGHT);
		FontUtils.derive(jlAviso, Font.ITALIC);
		
		JPanel panel = new JPanel(new MigLayout(
				new LC().maxWidth("800").noGrid().insets("30","10","10","10")
				));
		
		panel.add(jlTitulo, new CC().wrap());
		panel.add(new JSeparator(), new CC().width("0:100%:").wrap());
		
		panel.add(labelTo("Código",jtfCodigo), new CC().width("80!"));
		panel.add(labelTo("Nome Completo*",jtfNome), new CC().width("400:100%:"));
		panel.add(labelTo("Gênero",jcbGenero), new CC().wrap());
		
		panel.add(labelTo("Celular",jftfCelular), new CC().width("130::"));
		panel.add(labelTo("Email*",jtfEmail), new CC().width("250:30%:"));
		panel.add(labelTo("Cidade",jtfCidade), new CC().width("300:70%").wrap());
		
		panel.add(labelTo("Data de Nascimento",jpDataNascimento), new CC().width("150::"));
		panel.add(labelTo("Data de Cadastro",jtfDataCadastro), new CC().width("150::").wrap());
		
		panel.add(new JSeparator(), new CC().gapBottom("0").gapTop("10").width("0:100%:").wrap());
		panel.add(jbNovo, new CC());
		panel.add(jbSalvar, new CC());
		panel.add(jbExcluir, new CC());
		panel.add(jlAviso, new CC().alignY("top").width("100%"));
		
		JPanel agg = new JPanel(new MigLayout(new LC().insetsAll("0")));
		agg.add(panel);
		
		return agg;
	}
	
	private JPanel criarPainelPesquisa() throws Throwable{
		
		TableFieldBuilder builder = new TableFieldBuilder(Aluno.class);
		TableField[] fields = builder
				.field("id", "Id").width("50!").add()
				.field("nome", "Nome").width("80:200:").add()
				.field("email", "Email").width("50:150:").add()
				.field("celular", "Celular").width("150!").add()
				.build();
		
		tmAlunos = new GenericTableModel<>(fields);
		jtAlunos = new JTable(tmAlunos);
		jtAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtAlunos.addMouseListener(new AlunoSelecionadoMouseAdapter());
		
		jbPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pesquisar();
			}
		});
		jbLimparPesquisa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jtfPesquisa.setText("");
				jtfPesquisa.requestFocus();
			}
		});
		jtfPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					pesquisar();
				}
			}
		});
		
		JLabel jlTitulo = new JLabel("LISTAGEM DE ALUNOS CADASTRADOS");
		FontUtils.derive(jlTitulo, Font.PLAIN, 22f, Color.LIGHT_GRAY);
		
		JPanel panel = new JPanel(new MigLayout(new LC().noGrid().insets("30","10","10","10")));
		panel.add(jlTitulo, new CC().wrap());
		panel.add(new JSeparator(), new CC().width("0:100%:").wrap());
		panel.add(jtfPesquisa, new CC().width("100:100%:").gapRight("0"));
		panel.add(jbLimparPesquisa, new CC().gapLeft("0"));
		panel.add(jbPesquisar, new CC().wrap());
		panel.add(new JScrollPane(jtAlunos), new CC().width("100%").height("400:100%:"));
		
		return panel;
	}
	
	private JPanel labelTo(String label, JComponent comp){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel(label), BorderLayout.NORTH);
		panel.add(comp, BorderLayout.CENTER);
		return panel;
	}
	
//-------------------------------------------------------------------------------------------------
	
	protected void excluir() {
		try {
			if(OptionPane.showConfirmSimNaoWarning("Deseja excluir '"+aluno.getNome()+"'?")){
				controle.excluir(aluno);
				tmAlunos.remove(aluno);
				limparCampos();
			}
		} catch (Throwable e) {
			OptionPane.showError(e);
		}
	}

	public void salvar() {
		try {
			Aluno a = getAluno();
			boolean novo = controle.salvar(a);
			setAluno(a);
			tmAlunos.put(a);
			OptionPane.showMessage("Cadastro "+(novo?"gravado":"alterado")+" com sucesso!");
		}catch (IllegalArgumentException il) {
			OptionPane.showWarning(il.getMessage());
		} catch (Throwable e) {
			OptionPane.showError(e);
		}
	}
	
	private class AlunoSelecionadoMouseAdapter extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent evt) {
			try {
				Aluno a = tmAlunos.getSelected();
				if(a!=null){
					setAluno(controle.buscar(a.getId()));
				}
			} catch (Throwable e) {
				OptionPane.showError(e);
			}
		}
	}
	
	public void pesquisar() {
		try {
			tmAlunos.setData(controle.listar(jtfPesquisa.getText()));
		} catch (Exception e) {
			OptionPane.showError(e);
		}
	}
	
	private void limparCampos() {
		try {
			aluno = null;
			jtfCodigo.setText("");
			jtfNome.setText("");
			jtfCidade.setText("");
			jtfEmail.setText("");
			jftfCelular.setText("");
			jpDataNascimento.clear();
			jtfDataCadastro.setText("");
			jcbGenero.setSelectedItem(Genero.I);
			jbExcluir.setEnabled(false);
		} catch (Throwable e) {
			OptionPane.showError(e);
		}
	}
	
	public Aluno getAluno() {
		if(aluno==null){
			aluno = new Aluno();
		}
		aluno.setNome(jtfNome.getText());
		aluno.setCelular(jftfCelular.getFormattedText());
		aluno.setCidade(jtfCidade.getText());
		aluno.setEmail(jtfEmail.getText());
		aluno.setGenero((Genero)jcbGenero.getSelectedItem());
		aluno.setDataNascimento(jpDataNascimento.getLocalDate());
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		limparCampos();
		if(aluno == null){
			return;
		}
		this.aluno = aluno;
		jtfCodigo.setText(""+aluno.getId());
		jtfNome.setText(aluno.getNome());
		jtfCidade.setText(aluno.getCidade());
		jtfEmail.setText(aluno.getEmail());
		jftfCelular.setText(aluno.getCelular());
		jtfDataCadastro.setText(aluno.formatDataCadastro());
		jpDataNascimento.setLocalDate(aluno.getDataNascimento());
		jcbGenero.setSelectedItem(aluno.getGenero());
		jbExcluir.setEnabled(true);
		tab.setSelectedIndex(0);
	}
}