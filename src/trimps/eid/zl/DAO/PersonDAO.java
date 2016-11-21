package trimps.eid.zl.DAO;

import javax.ejb.Local;

import trimps.eid.zl.entity.Person;

@Local
public interface PersonDAO extends DAO<Person> {
	public Person getByIdcarrier(int firstindex, int maxresult, String idcarrier);

}
