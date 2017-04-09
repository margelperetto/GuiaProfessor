package br.com.margel.controle;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.margel.controle.bd.Db;
import br.com.margel.modelos.Aluno;
import br.com.margel.modelos.Aluno.Genero;

public class AlunoContr extends BasicContr<Aluno, String> {

	public AlunoContr() {
		super(Aluno.class);
	}

	@Override
	public List<Aluno> listar(String pesq) {
		try {

			CriteriaBuilder builder = Db.em().getCriteriaBuilder();
			CriteriaQuery<Aluno> query = builder.createQuery(clazz);

			Root<Aluno> root = query.from(Aluno.class);

			String pesqLow = "%"+pesq.toLowerCase()+"%";
			
			query.select(root)
			.where(
					builder.or(
							builder.like(
									builder.lower(root.get("nome")), pesqLow
									), 
							builder.like(
									builder.lower(root.get("email")), pesqLow
									), 
							builder.like(
									builder.lower(root.get("celular")), pesqLow
									)
							),
					builder.and()

					);

			TypedQuery<Aluno> typed = Db.em().createQuery(query);

			List<Aluno> list = typed.getResultList();

			return list;
		} catch (Throwable e) {
			throw new RuntimeException("Erro ao pesquisar alunos!", e);
		}
	}

	@Override
	public void gravar(Aluno aluno) {
		validarCampos(aluno);
		if(aluno.getId()<=0 && aluno.getDataHoraCadastro()==null){
			aluno.setDataHoraCadastro(LocalDateTime.now());
		}
		super.gravar(aluno);
	}
	
	@Override
	public Aluno alterar(Aluno obj) {
		validarCampos(obj);
		return super.alterar(obj);
	}

	private void validarCampos(Aluno aluno){
		StringBuilder b = new StringBuilder();
		if(aluno.getNome().trim().isEmpty()){
			b.append("Nome\n");
		}
		if(aluno.getEmail().trim().isEmpty()){
			b.append("Email\n");
		}
		if(!b.toString().isEmpty()){
			throw new IllegalArgumentException(
					"Campos obrigatórios não informados!\n"+b.toString());
		}
		if(aluno.getGenero() == null){
			aluno.setGenero(Genero.I);
		}
	}
}
