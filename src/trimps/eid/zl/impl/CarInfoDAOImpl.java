package trimps.eid.zl.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import trimps.eid.zl.DAO.CarInfoDAO;
import trimps.eid.zl.entity.CarInfo;

@Stateless
public class CarInfoDAOImpl extends DaoSupport<CarInfo> implements CarInfoDAO {
	@Override
	public CarInfo getByOwnerNum(int firstindex, int maxresult, String owner_num) {
		String whereJpql;
		List<Object> params = new ArrayList<Object>();
		whereJpql = "1=1";
		int i = 1;
		CarInfo result = null;

		if (owner_num != null && !owner_num.equals("")) {
			owner_num = owner_num.trim();
			whereJpql += " AND owner_num = ?" + String.valueOf(i++);
			params.add(owner_num);
		}
		List<CarInfo> alllists = getScrollData(firstindex, maxresult, whereJpql, params.toArray()).getResultlist();
		if (alllists != null && !alllists.isEmpty()) {
			result = alllists.get(0);
		}
		return result;
	}

	@Override
	public CarInfo getByRfidNum(int firstindex, int maxresult, String rfid_num) {
		String whereJpql;
		List<Object> params = new ArrayList<Object>();
		whereJpql = "1=1";
		int i = 1;
		CarInfo result = null;

		if (rfid_num != null && !rfid_num.equals("")) {
			rfid_num = rfid_num.trim();
			whereJpql += " AND rfid_num = ?" + String.valueOf(i++);
			params.add(rfid_num);
		}
		List<CarInfo> alllists = getScrollData(firstindex, maxresult, whereJpql, params.toArray()).getResultlist();
		if (alllists != null && !alllists.isEmpty()) {
			result = alllists.get(0);
		}
		return result;
	}
}
