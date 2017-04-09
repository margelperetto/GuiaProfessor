package controle.bd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Db {
	private static EntityManagerFactory factory;
	private static EntityManager em;
	
	private static EntityManagerFactory getFactory(){
		if(factory == null){
			factory = Persistence.createEntityManagerFactory("GuiaProfessorH2");
		}
		return factory;
	}

	public static EntityManager em(){
		if(em==null){
			em = getFactory().createEntityManager();
			System.out.println("EntityManager iniciado!");
		}
		return em;
	}

	public static void begin() {
		if(em!=null && em.getTransaction()!=null && !em.getTransaction().isActive()){
			em.getTransaction().begin();;
		}
	}
	
	public static void rollback() {
		if(em!=null && em.getTransaction()!=null && em.getTransaction().isActive()){
			em.getTransaction().rollback();
		}
	}
	
	public static void commit() {
		if(em!=null && em.getTransaction()!=null && em.getTransaction().isActive()){
			em.getTransaction().commit();
		}
	}

	public static void close() {
		if(em!=null){
			em.close();
			System.out.println("EntityManager fechado!");
		}
	}

}