package trimps.eid.zl.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import trimps.eid.zl.DAO.DAO;
import trimps.eid.zl.util.GenericsUtils;
import trimps.eid.zl.util.QueryResult;

@SuppressWarnings("unchecked")
@TransactionAttribute
public abstract class DaoSupport<T> implements DAO<T> {
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this
			.getClass());

	@PersistenceContext(unitName = "VerifyManager_unit")
	protected EntityManager em;

	public void clear() {
		em.clear();
	}

	public void delete(Serializable... entityids) {
		for (Object id : entityids) {
			em.remove(em.getReference(this.entityClass, id));
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public T find(Serializable entityId) {
		if (entityId == null)
			throw new RuntimeException(this.entityClass.getName()
					+ ":�����ʵ��id����Ϊ��");
		return em.find(this.entityClass, entityId);
	}

	public void save(Object entity) {
		em.persist(entity);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public long getCount() {
		return (Long) em.createQuery(
				"select count(" + getCountField(this.entityClass) + ") from "
						+ getEntityName(this.entityClass) + " o")
				.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getMax(String col) {
		Object obj = em.createQuery(
				"select max(o." + col + ") from "
						+ getEntityName(this.entityClass) + " o")
				.getSingleResult();
		if (obj != null) {
			return Long.parseLong(obj.toString());
		} else {
			return Long.parseLong("0");
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long getSum(String col) {
		Object obj = em.createQuery(
				"select sum(o." + col + ") from "
						+ getEntityName(this.entityClass) + " o")
				.getSingleResult();
		if (obj != null) {
			return Long.parseLong(obj.toString());
		} else {
			return Long.parseLong("0");
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getMaxLike(String col, String strLike) {
		Object obj = em.createQuery(
				"select max(o." + col + ") from "
						+ getEntityName(this.entityClass) + " o" + " where  "
						+ col + " like '%" + strLike + "%'").getSingleResult();
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getMaxLike2(String col, String strLike) {
		Object obj1 = em.createQuery(
				"select max(length(o." + col + ")) from "
						+ getEntityName(this.entityClass) + " o" + " where  "
						+ col + " like '%" + strLike + "%'").getSingleResult();
		int lenString = Integer.valueOf((obj1 == null) ? "0" : obj1.toString());
		Object obj = em.createQuery(
				"select max(o." + col + ") from "
						+ getEntityName(this.entityClass) + " o"
						+ " where  length(" + col + " ) = " + lenString
						+ " AND " + col + " like '%" + strLike + "%' ")
				.getSingleResult();
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> getByCondition(String col, String val) {
		String whereJpql = "1=1";
		List<Object> queryParams = new ArrayList<Object>();

		int i = 1;
		whereJpql += " AND " + col + "=?" + String.valueOf(i++);
		queryParams.add(val);

		Query query = em.createQuery("select o from "
				+ getEntityName(this.entityClass) + " o " + "where "
				+ whereJpql);
		setQueryParams(query, queryParams.toArray());
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<T> getAll() {
		return em.createQuery(
				"select o from " + getEntityName(this.entityClass) + " o")
				.getResultList();
	}

	public void update(Object entity) {
		em.merge(entity);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstindex, int maxresult,
			LinkedHashMap<String, String> orderby) {
		return getScrollData(firstindex, maxresult, null, null, orderby);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams) {
		return getScrollData(firstindex, maxresult, wherejpql, queryParams,
				null);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstindex, int maxresult) {
		return getScrollData(firstindex, maxresult, null, null, null);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public QueryResult<T> getScrollData() {
		return getScrollData(-1, -1);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public QueryResult<T> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(this.entityClass);
		Query query = em.createQuery("select o from "
				+ entityname
				+ " o "
				+ (wherejpql == null || "".equals(wherejpql.trim()) ? ""
						: "where " + wherejpql) + buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if (firstindex != -1 && maxresult != -1)
			query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setResultlist(query.getResultList());
		query = em.createQuery("select count("
				+ getCountField(this.entityClass)
				+ ") from "
				+ entityname
				+ " o "
				+ (wherejpql == null || "".equals(wherejpql.trim()) ? ""
						: "where " + wherejpql));
		setQueryParams(query, queryParams);
		qr.setTotalrecord((Long) query.getSingleResult());
		return qr;
	}

	protected static void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i + 1, queryParams[i]);
			}
		}
	}

	/**
	 * ��װorder by���
	 * 
	 * @param orderby
	 * @return
	 */
	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ")
						.append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}
		return orderbyql.toString();
	}

	/**
	 * ��ȡʵ�������
	 * 
	 * @param <E>
	 * @param clazz
	 *            ʵ����
	 * @return
	 */
	protected static <E> String getEntityName(Class<E> clazz) {
		String entityname = clazz.getSimpleName();
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	protected static <E> String getCountField(Class<E> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null
						&& method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(
							propertydesc.getPropertyType())
							.getPropertyDescriptors();
					out = "o."
							+ propertydesc.getName()
							+ "."
							+ (!ps[1].getName().equals("class") ? ps[1]
									.getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
