package trimps.eid.zl.CDI;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ThreadContextMap {
	static final Logger log = LogManager.getLogManager().getLogger(
			ThreadContextMap.class.getName());
	private static final Map<String, String> initMap = new HashMap<String, String>();
	private final ThreadLocal<Map<String, String>> localMap = new InheritableThreadLocal<Map<String, String>>() {
		@Override
		protected Map<String, String> childValue(
				final Map<String, String> parentValue) {
			/*log.info("Thread[" + Thread.currentThread().getId()
					+ "] initalizing local HashMap...");*/
			/*if (parentValue == null) {
				log.severe("Thread[" + Thread.currentThread().getId()
						+ "] parentValue == null");
			}*/
			Map<String, String> newMap = new HashMap<String, String>(
					parentValue);
			return newMap;
		}

		@Override
		protected Map<String, String> initialValue() {
			/*log.info("Thread[" + Thread.currentThread().getId()
					+ "] initialValue()...");*/
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.putAll(initMap);
			return newMap;
		}

	};

	static {
		/*log.info("Thread[" + Thread.currentThread().getId()
				+ "] initalizing init HashMap...");*/
		initMap.put("eIDVersion", "V1.0.0");
	}

	void put(final String key, final String value) {
		if (localMap.get() == null) {
			log.severe("Thread[" + Thread.currentThread().getId()
					+ "] localMap.get() == null");
		} else {
			localMap.get().put(key, value);
		}
	}

	String get(final String key) {
		return localMap.get().get(key);
	}

	void remove(final String key) {
		localMap.get().remove(key);
	}

	void clear() {
		localMap.get().clear();
	}

	void init() {
		clear();
		localMap.get().putAll(initMap);
	}

	Map<String, String> getContext() {
		return localMap.get();
	}

	ThreadContextMap() {

	}

}
