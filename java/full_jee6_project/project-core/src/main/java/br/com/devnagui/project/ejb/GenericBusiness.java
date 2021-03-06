package br.com.devnagui.project.ejb;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.devnagui.project.dto.PageObjectDTO;
import br.com.devnagui.project.ejb.exception.BusinessException;

/**
 * 
 * Criado por @author 006159C0 em 14/03/2013
 * 
 * @param <Entidade>
 */
public interface GenericBusiness<Entidade extends Serializable> extends
		Serializable {

	public void insert(Entidade entidade) throws BusinessException;

	public Entidade update(Entidade entidade) throws BusinessException;

	public void remove(Entidade entidade) throws BusinessException;

	void refresh(Entidade entidade) throws BusinessException;
	
	public Entidade searchForId(Long id) throws BusinessException;

	public List<Entidade> getAll() throws BusinessException;
	
	public EntityManager getEntityManager();

	public PageObjectDTO<Entidade> getSimplePagedList(PageObjectDTO<Entidade> paginacao)
			throws BusinessException;

	/**
	 * Serve para...
	 * 
	 * @param entidadeAtual
	 * @throws BusinessException
	 */
	void detach(Entidade entidade) throws BusinessException;

	/**
	 * Inicializar um objeto proxy ou colecao.
	 * 
	 * @param colecao
	 */
	void initiateObjectOrCollection(Object colecao);

}