package br.com.margel.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="alunos")
public class Aluno extends BasicBean{

	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private String celular;
	
	@Column
	private String cidade;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	@Column(name="datanascimento")
	private LocalDate dataNascimento;
	
	@Column(name="datahoracadastro")
	private LocalDateTime dataHoraCadastro;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public LocalDateTime getDataHoraCadastro() {
		return dataHoraCadastro;
	}
	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public static enum Genero{
		I("Indefinido"),M("Masculino"),F("Feminino");
		private String descricao;
		private Genero(String descricao) {
			this.descricao = descricao;
		}
		@Override
		public String toString() {
			return descricao;
		}
	}

	public String formatDataCadastro() {
		return dataHoraCadastro==null?"":dataHoraCadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}
}