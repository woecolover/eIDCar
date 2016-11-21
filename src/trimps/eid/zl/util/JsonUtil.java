/* created by zhangling at 2016-8-17 上午10:48:55 */
package trimps.eid.zl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

public class JsonUtil {

	public static String convertStramToString(InputStream is) throws IOException {
		InputStreamReader reader = new InputStreamReader(is, "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		String res = URLDecoder.decode(buffer.toString(), "UTF-8");
		return res;
	}

}
