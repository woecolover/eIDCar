package trimps.eid.zl.DAO;

import javax.ejb.Local;

import trimps.eid.zl.entity.CarBatch;

@Local
public interface CarBatchDAO extends DAO<CarBatch> {
	public CarBatch getByBatchNum(int firstindex, int maxresult, String batch_num);

}
