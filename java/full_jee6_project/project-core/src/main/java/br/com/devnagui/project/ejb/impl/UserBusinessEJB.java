package br.com.devnagui.project.ejb.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.devnagui.project.ejb.UserBusiness;
import br.com.devnagui.project.ejb.exception.BusinessException;
import br.com.devnagui.project.entities.User;

@Stateless
@WebService
public class UserBusinessEJB extends GenericBusinessEJB<User> implements UserBusiness {

	/**
	 * uid
	 */
	private static final long serialVersionUID = 9097552051222245731L;
	
	private static final Logger LOG = Logger.getLogger(UserBusinessEJB.class);

	private static final int QUERY_PARAM_MAX_RESULTS_FOR_AUTOCOMPLETE = 20;
	
	// METODOS PUBLICOS
	
	@Override
	public List<User> getUserByRegistrationNumberOrName(final String paramBusca) {
		LOG.debug("getUserByRegistrationNumberOrName");
		final String LIKE = "%";
		TypedQuery<User> query = getEntityManager().createNamedQuery(User.QUERY_SEARCH_BY_REGISTRATION_OR_NAME, User.class);
		query.setParameter("param", (paramBusca != null ? paramBusca.toUpperCase() : "") + LIKE);
		query.setParameter("param",LIKE+ (paramBusca != null ? paramBusca.toUpperCase() : "") + LIKE);
		query.setMaxResults(QUERY_PARAM_MAX_RESULTS_FOR_AUTOCOMPLETE);
		return query.getResultList();
	}
	
	
	@Override
	public User getUserByRegistrationNumberAndName(String matricula, String nome) throws BusinessException {
		LOG.debug("getUserByRegistrationNumberAndName");
		try {
			TypedQuery<User> query = getEntityManager().createNamedQuery(User.QUERY_SEARCH_BY_NAME, User.class);
			query.setParameter("matricula", (matricula != null? matricula:""));
			query.setParameter("nome", (nome!=null? nome.toUpperCase().trim():""));
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new BusinessException("mensagem.usuario.nao.existe");
		}		
	}
	
	
	@Override
	public void validateUser(User usuario) throws BusinessException {
		LOG.debug("validateUser");
		this.validateUserFields(usuario);
		this.validateIfUserExists(usuario);
	}

	@Override
	public void validateUserFields(User usuario) throws BusinessException {
		LOG.debug("validateUserFields");
		if( usuario == null || usuario.getRegistrationNumber() == null || usuario.getName() == null){
			throw new BusinessException("mensagem.usuario.nao.informado");
		}
	}

	@Override
	public void validateIfUserExists(User usuario) throws BusinessException {
		LOG.debug("validateIfUserExists");
		final String matriculaUsuario = usuario.getRegistrationNumber();
		final String nomeUsuario = usuario.getName();
		final boolean usuarioExiste = matriculaUsuario != null && nomeUsuario != null && getUserByRegistrationNumberAndName(matriculaUsuario, nomeUsuario) != null;
		
		if( !usuarioExiste ){
			throw new BusinessException("mensagem.usuario.nao.existe");
		}
	}
}
