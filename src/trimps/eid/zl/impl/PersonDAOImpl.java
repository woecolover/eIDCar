package trimps.eid.zl.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import trimps.eid.zl.DAO.PersonDAO;
import trimps.eid.zl.entity.Person;

@Stateless
public class PersonDAOImpl extends DaoSupport<Person> implements PersonDAO {
	@Override
	public Person getByIdcarrier(int firstindex, int maxresult, String idcarrier) {
		String whereJpql;
		List<Object> params = new ArrayList<Object>();
		whereJpql = "1=1";
		int i = 1;
		Person result = null;

		if (idcarrier != null && !idcarrier.equals("")) {
			idcarrier = idcarrier.trim();
			whereJpql += " AND idcarrier = ?" + String.valueOf(i++);
			params.add(idcarrier);
		}
		List<Person> alllists = getScrollData(firstindex, maxresult, whereJpql, params.toArray()).getResultlist();
		if (alllists != null && !alllists.isEmpty()) {
			result = alllists.get(0);
		}
		return result;
	}
}
