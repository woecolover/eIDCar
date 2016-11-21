package trimps.eid.zl.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {
	public static String encodeBytes(byte[] source) {
		return new String(Base64.encodeBase64(source));
	}

	public static byte[] decodeBytes(byte[] source) {
		return Base64.decodeBase64(source);
	}
}
