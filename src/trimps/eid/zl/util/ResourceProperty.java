package trimps.eid.zl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourceProperty {

	public static final String pfile = "../standalone/data/eIDCar.properties";
	public static final Properties prop = new Properties();

	public static String getProperty(String key) {

		try {
			prop.load(new FileInputStream(pfile));
		} catch (IOException e) {
			System.out.println("open config file failed!");
			return null;
		}
		String val = prop.getProperty(key);
		return val;
	}
}
