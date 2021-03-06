/**
 */
package br.com.devnagui.project.dto;

import java.io.Serializable;
import java.util.List;

/**
 */
public class PageObjectDTO<E extends Serializable> {

	// ATRIBUTOS E CONSTRUTORES

	private List<E> dados;
	private int tamanhoDaPagina = 10;

	/**
	 * Sempre inicia pela primeira pagina
	 */
	private int indicePagina = 1;
	private int totalRegistros;

	/**
	 * 
	 */
	public PageObjectDTO(int indicePagina) {
		this.indicePagina = indicePagina;
	}

	// METODOS PUBLICOS

	// METODOS PRIVADOS

	// GETS E SETS

	/**
	 * Serve para obter o indice inicial da busca na base de dados
	 * 
	 * @return
	 */
	public int getFromIndex() {
		int indiceInicioBase = (getIndicePagina() - 1) * getDatasetLength();

		return indiceInicioBase;
	}

	/**
	 * Serve para obter o ultimo indice no dataset(NORMALMENTE N�O DEVE SER
	 * UTILIZADO)
	 * 
	 * @return
	 */
	public int getIndiceFimDataset() {
		int indiceInicioDataset = getFromIndex();

		if (indiceInicioDataset + getDatasetLength() > getTotalRegistros()) {
			// Neste caso, estaremos na ultima pagina, e devemos obter todos o
			// registros a partir do primeiro.
			return indiceInicioDataset
					+ (getTotalRegistros() - indiceInicioDataset);
		}

		return indiceInicioDataset + getDatasetLength();
	}

	/**
	 * @return the dados
	 */
	public List<E> getDados() {
		return dados;
	}

	/**
	 * @param dados
	 *            the dados to set
	 */
	public void setDados(List<E> dados) {
		this.dados = dados;
	}

	/**
	 * @return the tamanhoDaPagina
	 */
	public int getDatasetLength() {
		return tamanhoDaPagina;
	}

	/**
	 * @param tamanhoDaPagina
	 *            the tamanhoDaPagina to set
	 */
	public void setTamanhoDaPagina(int tamanhoDaPagina) {
		this.tamanhoDaPagina = tamanhoDaPagina;
	}

	/**
	 * @return the indicePagina
	 */
	public int getIndicePagina() {
		return indicePagina;
	}

	/**
	 * @param indicePagina
	 *            the indicePagina to set
	 */
	public void setIndicePagina(int indicePagina) {
		this.indicePagina = indicePagina;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros
	 *            the totalRegistros to set
	 */
	public void setPageLength(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dados == null) ? 0 : dados.hashCode());
		result = prime * result + indicePagina;
		result = prime * result + tamanhoDaPagina;
		result = prime * result + totalRegistros;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaginaDTO [dados=" + (dados != null ? dados.size() : 0)
				+ ", tamanhoDaPagina=" + tamanhoDaPagina + ", indicePagina="
				+ indicePagina + ", totalRegistros=" + totalRegistros + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PageObjectDTO))
			return false;
		@SuppressWarnings("unchecked")
		PageObjectDTO<E> other = (PageObjectDTO<E>) obj;
		if (dados == null) {
			if (other.dados != null)
				return false;
		} else if (!dados.equals(other.dados))
			return false;
		if (indicePagina != other.indicePagina)
			return false;
		if (tamanhoDaPagina != other.tamanhoDaPagina)
			return false;
		if (totalRegistros != other.totalRegistros)
			return false;
		return true;
	}

}
