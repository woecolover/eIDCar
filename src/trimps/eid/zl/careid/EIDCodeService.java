package trimps.eid.zl.careid;

import java.nio.ByteBuffer;

import trimps.eid.zl.util.Base64Helper;

public class EIDCodeService {

	public static String eIDCodeOfCar(String car_num, String owner_name, String owner_num, String rfid_num)
			throws Exception {
		String eidcodeStr = "";
		//		byte[] random128 = GenRandomData.genrandom128();
		byte[] d47 = car_num.getBytes();
		byte[] d48 = owner_name.getBytes();
		byte[] d49 = owner_num.getBytes();
		byte[] d50 = rfid_num.getBytes();

		//		ByteBuffer bb = ByteBuffer.allocate(d47.length + d48.length + d49.length + d50.length + random128.length);
		ByteBuffer bb = ByteBuffer.allocate(d47.length + d48.length + d49.length + d50.length);
		bb.put(d47);
		bb.put(d48);
		bb.put(d49);
		bb.put(d50);
		//	bb.put(random128);
		String sm3Hash = Base64Helper.encodeBytes(SM3Helper.hash(bb.array()));
		eidcodeStr = "1" + sm3Hash + "000";
		return eidcodeStr;
	}

}
