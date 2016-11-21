package trimps.eid.zl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectMapper {
	public static String object2string(Object object) {
		if (null == object)
			return null;
		StringBuilder sb = new StringBuilder(object.getClass().getSimpleName());
		sb.append("[");
		boolean isFirst = true;
		Method[] methods = object.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
				if (isFirst) {
					isFirst = false;
				} else {
					sb.append(", ");
				}
				sb.append(method.getName()).append("=");
				Object value = null;
				try {
					value = method.invoke(object);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if (null != value) {
					sb.append(value.toString());
				} else {
					sb.append("null");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static String constructSubjectCN(String eIDcode) {
		return "CN=" + eIDcode + ",C=CN";
	}
}
