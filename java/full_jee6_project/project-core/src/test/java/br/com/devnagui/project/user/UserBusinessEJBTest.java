package br.com.devnagui.project.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import br.com.devnagui.project.ejb.exception.BusinessException;
import br.com.devnagui.project.ejb.impl.UserBusinessEJB;
import br.com.devnagui.project.entities.User;
public class UserBusinessEJBTest {

	private static final String USER_NAME = "Nathan";
	private static final String REGISTRATION_NUMBER = "12345678";
	private UserBusinessEJB userBusinessEJB;
	 
	@Before
	public void before(){
		userBusinessEJB = mock(UserBusinessEJB.class);
		when(userBusinessEJB.getEntityManager()).thenReturn(mock(EntityManager.class));
		when(userBusinessEJB.getEntityManager().createNamedQuery(anyString(),Matchers.any(Class.class))).thenReturn(mock(TypedQuery.class));
	
	}
	@Test
	public void testGetUserByRegistrationNumberOrName(){
		when(userBusinessEJB.getEntityManager().createNamedQuery(USER_NAME,User.class).getResultList()).thenReturn(new ArrayList<User>());
		when(userBusinessEJB.getUserByRegistrationNumberOrName(anyString())).thenCallRealMethod();
		userBusinessEJB.getUserByRegistrationNumberOrName(USER_NAME);
		
	}
	
	@Test
	public void testGetRegistrationUserSucess() throws BusinessException {
		when(userBusinessEJB.getEntityManager().createNamedQuery(anyString(),Matchers.any(Class.class)).getSingleResult())
				.thenReturn(new User(REGISTRATION_NUMBER, USER_NAME));
		when(
				userBusinessEJB.getUserByRegistrationNumberAndName(
						REGISTRATION_NUMBER, USER_NAME)).thenCallRealMethod();
		
		
		userBusinessEJB.getUserByRegistrationNumberAndName(REGISTRATION_NUMBER, USER_NAME);
	}
	
	@Test
	public void testValidateUserSucess() throws BusinessException {
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUser(new User(REGISTRATION_NUMBER, USER_NAME));
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUserFields(new User(REGISTRATION_NUMBER, USER_NAME));
		Mockito.doCallRealMethod().when(userBusinessEJB).validateIfUserExists(new User(REGISTRATION_NUMBER, USER_NAME));
		when(userBusinessEJB.getUserByRegistrationNumberAndName(REGISTRATION_NUMBER, USER_NAME)).thenReturn(new User(REGISTRATION_NUMBER, USER_NAME));
		userBusinessEJB.validateUser(new User(REGISTRATION_NUMBER, USER_NAME));
	}

	@Test
	public void testGetRegistrationUserErrorNotExisting() throws BusinessException  {
		
		when(userBusinessEJB.getEntityManager().createNamedQuery(User.QUERY_SEARCH_BY_NAME, User.class).getSingleResult()).thenThrow(NoResultException.class);
	
		try {
			when(	userBusinessEJB.getUserByRegistrationNumberAndName(anyString(), anyString())).thenCallRealMethod();
			userBusinessEJB.getUserByRegistrationNumberAndName(REGISTRATION_NUMBER, USER_NAME);
			fail();
		} catch (BusinessException e) {
			assertEquals("mensagem.usuario.nao.existe", e.getMessage());
		}
		
	}
	@Test
	public void testValidateUserErrorNullUser() throws BusinessException  {
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUser(null);
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUserFields(null);
		try {
			userBusinessEJB.validateUser(null);
			fail();
		} catch (BusinessException e) {
			assertEquals("mensagem.usuario.nao.informado", e.getMessage());
		}
	}
	@Test
	public void testValidateUserErrorParametersNull() throws BusinessException  {
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUser(new User(null, null));
		Mockito.doCallRealMethod().when(userBusinessEJB).validateUserFields(new User(null, null));
		try {
			userBusinessEJB.validateUser(new User(null, null));
			fail();
		} catch (BusinessException e) {
			assertEquals("mensagem.usuario.nao.informado", e.getMessage());
		}
	}

	

}
