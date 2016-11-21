package trimps.eid.zl.DAO;

import javax.ejb.Local;

import trimps.eid.zl.entity.CarInfo;

@Local
public interface CarInfoDAO extends DAO<CarInfo> {
	public CarInfo getByOwnerNum(int firstindex, int maxresult, String owner_num);

	public CarInfo getByRfidNum(int firstindex, int maxresult, String rfid);
}
