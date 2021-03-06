package br.com.devnagui.project.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USUARIO", schema = "SISSEG")
@NamedQueries(value = {
		@NamedQuery(name = User.QUERY_SEARCH_BY_REGISTRATION_OR_NAME, query = "select u from User u where u.registrationNumber like (:param) or u.name like (:param) and u.registrationNumber is not null "),
		@NamedQuery(name = User.QUERY_SEARCH_BY_NAME, query = "select u from User u where u.registrationNumber = :registrationNumber and UPPER(trim(u.name)) = :name"),
		@NamedQuery(name = User.QUERY_SEARCH_BY_EXACT_REGISTRATION_AND_NAME, query = "select u from User u where u.registrationNumber = :registrationNumber or trim(u.name) = :name and u.registrationNumber is not null") })
public class User implements Serializable {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -108829748495775901L;

	public static final String QUERY_SEARCH_BY_REGISTRATION_OR_NAME = "buscarUsuariosPorMatricula";
	public static final String QUERY_SEARCH_BY_NAME = "buscarUsuarioPorMatriculaNome";
	public static final String QUERY_SEARCH_BY_EXACT_REGISTRATION_AND_NAME = "buscarUsuarioPorMatriculaOuNome";

	@Id
	@Column(name = "SEQ_USER" )
	private Long id;

	@Column(name = "USER_NAME", length = 80)
	@NotNull
	private String name;

	@Column(name = "REGISTRATION_NUMBER", length = 8, unique = true)
	@NotNull
	private String registrationNumber;
	
	@Column(name ="ENABLED")
	private boolean enabled;
	
	
	
	
	public User() {
		super();
	}

	public User(String registrationNumber,String name) {
		super();
		this.registrationNumber = registrationNumber;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User castOther = (User) other;
		return (this.id == castOther.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = (int) (hash * prime + this.id);

		return hash;
	}
}
