package br.com.margel.gp.control;

import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.margel.gp.control.bd.Db;
import br.com.margel.gp.models.BasicBean;

public abstract class BasicContr<T extends BasicBean, F> {

	protected Class<T> clazz;
	
	public BasicContr(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public boolean salvar(T obj){
		if(obj.getId()>0){
			alterar(obj);
			return false;
		}
		gravar(obj);
		return true;
	}
	
	public void gravar(T obj){
		try {
			
			Db.begin();
			
			Db.em().persist(obj);
			
			Db.commit();
			
		} catch (Throwable e) {
			Db.rollback();
			throw new RuntimeException("Erro ao gravar!", e);
		}
	}
	
	public T alterar(T obj){
		try {
			Db.begin();
			
			T ret = Db.em().merge(obj);
			
			Db.commit();
			
			return ret;
		} catch (Throwable e) {
			Db.rollback();
			throw new RuntimeException("Erro ao alterar!", e);
		}
	}
	
	public void excluir(T obj){
		try {
			Db.begin();
			
			Db.em().remove(obj);
			
			Db.commit();
		} catch (Throwable e) {
			Db.rollback();
			throw new RuntimeException("Erro ao excluir!", e);
		}
	}
	
	public T buscar(long id){
		try {
			return Db.em().find(clazz, id);
		} catch (Throwable e) {
			throw new RuntimeException("Erro ao buscar!", e);
		}
	}
	
	public List<T> listarTodos(){
		try {
			CriteriaBuilder builder = Db.em().getCriteriaBuilder();
			
			CriteriaQuery<T> query = builder.createQuery(clazz);
			Root<T> r = query.from(clazz);
			query.select(r);
			TypedQuery<T> typed = Db.em().createQuery(query);
			
			List<T> list = typed.getResultList();
			
			return list;
		} catch (Throwable e) {
			throw new RuntimeException("Erro ao listar todos!", e);
		}
	}
	
	public abstract List<T> listar(F filtro);
}