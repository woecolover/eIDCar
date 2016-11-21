package trimps.eid.zl.CDI;

import java.util.Map;

public class ThreadContext {
	private static ThreadContextMap map = new ThreadContextMap();

	public static void put(String key, String value) {
		map.put(key, value);

	}

	public static String get(String key) {
		return map.get(key);
	}

	public static void remove(String key) {
		map.remove(key);
	}

	public static void clear() {
		map.clear();
	}

	public static void init() {
		map.init();
	}

	public static Map<String, String> getContext() {
		return map.getContext();
	}

	private ThreadContext() {

	}
}
