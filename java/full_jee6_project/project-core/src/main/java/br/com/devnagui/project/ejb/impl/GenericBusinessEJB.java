package br.com.devnagui.project.ejb.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import br.com.devnagui.project.dto.PageObjectDTO;
import br.com.devnagui.project.ejb.GenericBusiness;
import br.com.devnagui.project.ejb.exception.BusinessException;

/**
 * 
 * Created by Me :D
 * 
 * @param <Entity>
 */
public class GenericBusinessEJB<Entity extends Serializable> implements GenericBusiness<Entity> {
	/**
	 * Serial UID.
	 */
	private static final long serialVersionUID = -7759790807104078131L;
	
	protected static final int STANDARD_MAX_RESULT = 10;

	private static final Logger LOG = Logger.getLogger(GenericBusinessEJB.class);

	@PersistenceContext(name = "projectPU")
	private EntityManager entityManager;

	private Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	protected Class<Entity> getEntityClass() {

		if (entityClass != null) {
			return entityClass;
		}
		//Gizzz
		return (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected String getEntityName() {
		return getEntityManager().getMetamodel().entity(getEntityClass()).getName();
	}

	@Override
	public void insert(Entity entity) throws BusinessException {
	    LOG.info("Inserting entity.");
        try {

			getEntityManager().persist(entity);
		} catch (PersistenceException e) {
			LOG.error("Error during insert  - " + getEntityClass(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public Entity update(Entity entity) throws BusinessException {
	    LOG.info("Updating  entity.");
	    
		try {
			Entity entidadeAtualizada = getEntityManager().merge(entity);
			getEntityManager().flush();
			return entidadeAtualizada;
		} catch (PersistenceException e) {
			LOG.error("Error during  update - " + getEntityClass(), e);
			throw new BusinessException(e);
		}

	}

	@Override
	public void remove(Entity entity) throws BusinessException {
	    LOG.info("Removing entity.");
		try {
		    entity=getEntityManager().merge(entity);
			getEntityManager().remove(entity);
		} catch (PersistenceException e) {
			LOG.error("Error during remove - " + getEntityClass(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public void refresh(Entity entity) throws BusinessException {
	    LOG.info("Refreshing entity.");
        
		try {
			getEntityManager().refresh(entity);
		} catch (PersistenceException e) {
			LOG.error("Error during refresh - " + getEntityClass(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public void detach(Entity entity) throws BusinessException {
	    LOG.info("Detaching entity.");
        
		try {
			getEntityManager().detach(entity);
		} catch (PersistenceException e) {
			LOG.error("Error during detach - " + getEntityClass(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public Entity searchForId(Long id) throws BusinessException {
	    LOG.info("Searching by ID.");
        
		try {
			return getEntityManager().find(getEntityClass(), id);
		} catch (PersistenceException e) {
			LOG.error(""
					+ "Error during find .", e);
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Entity> getAll() throws BusinessException {
	    LOG.info("Getting all entities.");
        
		try {
			CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Entity> cQuery = builder.createQuery(getEntityClass());
			Root<Entity> from = cQuery.from(getEntityClass());
			CriteriaQuery<Entity> select = cQuery.select(from);
			// Added orderBy clause
			TypedQuery<Entity> typedQuery = getEntityManager().createQuery(select);
			return typedQuery.getResultList();
		} catch (PersistenceException e) {
			LOG.error("Error during getAll.", e);
			throw new BusinessException(e);
		}
	}

	@Override
	public PageObjectDTO<Entity> getSimplePagedList(PageObjectDTO<Entity> pageObject) throws BusinessException {
	    LOG.info("Default paging without filters.");
        
		try {
			List<Entity> pagedData = retrivePagedData(pageObject);
			pageObject.setDados(pagedData);

			int pageLength = getTotalEntitiesForPagination(pageObject);
			pageObject.setPageLength(pageLength);

			return pageObject;
		} catch (PersistenceException e) {
			LOG.error("Error during pagination - " + getEntityClass(), e);
			throw new BusinessException(e);
		}
	}
	
	protected List<Entity> retrivePagedData(final PageObjectDTO<Entity> pagedObject) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Entity> cQuery = builder.createQuery(getEntityClass());
        Root<Entity> entityRoot = cQuery.from(getEntityClass());
        cQuery.select(entityRoot);
        cQuery.where(buildPredicate(builder, entityRoot,pagedObject));
        cQuery.orderBy(buildOrderBy(builder, entityRoot ,pagedObject));
        TypedQuery<Entity> typedQuery = getEntityManager().createQuery(cQuery);
        restrictLength(pagedObject, typedQuery);
        return typedQuery.getResultList();
    }

    
	protected int getTotalEntitiesForPagination(PageObjectDTO<Entity> pagedObject) {
	    CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
        Root<Entity> entityRoot = cQuery.from(getEntityClass());
        cQuery.select(builder.count(entityRoot));
        cQuery.where(buildPredicate(builder, entityRoot, pagedObject));
        Long totalEntities = getEntityManager().createQuery(cQuery).getSingleResult();
        return totalEntities.intValue();
    }
	
	
	protected void restrictLength(final PageObjectDTO<Entity> paginacao, TypedQuery<Entity> typedQuery) {
        int fromIndex = paginacao.getFromIndex();
        typedQuery.setFirstResult(fromIndex);
        typedQuery.setMaxResults(paginacao.getDatasetLength());
    }
    

	/**
	 * Standard predicate method.
	 * Method to be overrided by its subclasses for more specialized behavior 
	 * @param criteriaBuilder
	 * @param entityRoot
	 * @param pageObject - Used for more specialized ordenation.
	 */
	protected Predicate buildPredicate(final CriteriaBuilder builder, final Root<Entity> entidadeRoot, PageObjectDTO<Entity> paginacao) {
		return builder.conjunction();
	}

	/**
	 * Standard ordering method.
	 * Method to be overrided by its subclasses  for more specialized behavior 
	 * 
	 * @param criteriaBuilder
	 * @param entityRoot
	 * @param pageObject - Used for more specialized ordenation.
	 * @return Order
	 */
	protected Order buildOrderBy(CriteriaBuilder criteriaBuilder, Root<Entity> entityRoot, PageObjectDTO<Entity> pageObject) {
		return criteriaBuilder.asc(entityRoot);
	}
	
	public void initiateObjectOrCollection(Object colecao) {
	    LOG.info("Initializing collection.");
        
		Hibernate.initialize(colecao);
	}
	
    
	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	};

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}