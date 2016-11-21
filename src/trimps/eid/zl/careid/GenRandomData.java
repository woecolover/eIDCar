package trimps.eid.zl.careid;

import java.util.Random;
import java.util.UUID;

public class GenRandomData {
	public static String getRandomString(int length) {
		String base = "a#b&c=de����f[ghi]jk#&[lmn&op#qͨ��r#&[s]����tu=vw]xyz0ͨ��1234[56]78#&[9ABC=DE����FGH=IJKL=MNͨ��O=PQRST=UVWX=YZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static byte[] genrandom128() {
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString();
		while (uid.length() < 128) {
			uid += uid;
		}
		byte[] bytes = uid.getBytes();
		byte[] random128 = new byte[128];
		for (int i = 0; i < 128; i++) {
			random128[i] = bytes[i];
		}
		return random128;

	}

	public static void main(String[] args) {
		String dd = getRandomString(45);
		System.out.println(dd);
	}
}
