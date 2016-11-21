package trimps.eid.zl.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import trimps.eid.zl.DAO.CarBatchDAO;
import trimps.eid.zl.entity.CarBatch;

@Stateless
public class CarBatchDAOImpl extends DaoSupport<CarBatch> implements CarBatchDAO {
	@Override
	public CarBatch getByBatchNum(int firstindex, int maxresult, String batch_num) {
		String whereJpql;
		List<Object> params = new ArrayList<Object>();
		whereJpql = "1=1";
		int i = 1;
		CarBatch result = null;

		if (batch_num != null && !batch_num.equals("")) {
			batch_num = batch_num.trim();
			whereJpql += " AND batch_num = ?" + String.valueOf(i++);
			params.add(batch_num);
		}
		List<CarBatch> alllists = getScrollData(firstindex, maxresult, whereJpql, params.toArray()).getResultlist();
		if (alllists != null && !alllists.isEmpty()) {
			result = alllists.get(0);
		}
		return result;
	}
}
