package br.com.margel.gp.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BasicBean {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !obj.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		return this.id == ((BasicBean)obj).id;
	}
}
