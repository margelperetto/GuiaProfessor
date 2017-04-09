package visualizacao.models;

import java.util.LinkedList;
import java.util.List;

public class TableFieldBuilder {
	private List<TableField> fields = new LinkedList<>();
	private final Class<?> clazz;
	private TableField current;
	
	public TableFieldBuilder(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public TableFieldBuilder field(String fieldName, String columnName){
		current = new TableField(clazz, fieldName, columnName);
		return this;
	}
	
	public TableFieldBuilder width(String width){
		if(current==null){
			throw new RuntimeException("É necessário chamar o método 'field' antes de adicionar o campo!");
		}
		current.setWidth(width);
		return this;
	}
	
	public TableFieldBuilder add(){
		if(current==null){
			throw new RuntimeException("É necessário chamar o método 'field' antes de adicionar o campo!");
		}
		fields.add(current);
		this.current = null;
		return this;
	}
	
	public TableField[] build() {
		return this.fields.toArray(new TableField[0]);
	}
}
