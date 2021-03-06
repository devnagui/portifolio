package br.com.devnagui.project.ejb;

import java.util.List;

import br.com.devnagui.project.ejb.exception.BusinessException;
import br.com.devnagui.project.entities.User;

public interface UserBusiness extends GenericBusiness<User>{
		
	public List<User> getUserByRegistrationNumberOrName(String userName);
	
	public User getUserByRegistrationNumberAndName(String matriculaUsuario, String nomeUsuario) throws BusinessException;
	
	public void validateUser(User usuario) throws BusinessException;

	public void validateUserFields(User usuario) throws BusinessException;

	public void validateIfUserExists(User usuario) throws BusinessException;	
}
