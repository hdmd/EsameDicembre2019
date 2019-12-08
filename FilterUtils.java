package it.univpm.esameDicembre.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import it.univpm.esameDicembre.Model.Element;

public class FilterUtils<T> {
	public static boolean check(Object value, String operator, Object th) {
		if (value instanceof Number && th instanceof Number) {
			Double thC = ((Number) th).doubleValue();
			Double valuec = ((Number) value).doubleValue();
			if (operator.equals("eq"))
				return value.equals(th);
			else if (operator.equals("gt"))
				return valuec > thC;
			else if (operator.equals("lt"))
				return valuec < thC;
		} else if (th instanceof String && value instanceof String) {
			return value.equals(th);
		} else {
		}
		return false;
	}
	
	public Collection<T> select(Collection<T> src, String fieldName, String operator, Object value) {
		Collection<T> out = new ArrayList<T>();
		for (T item : src) {
			try {
				Method m = item.getClass()
						.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), null);
				try {
					Object tmp = m.invoke(item);
					if (FilterUtils.check(tmp, operator, value))
						out.add(item);
				} catch (IllegalAccessException e) {
					throw new RuntimeException("Causa di errore: " + e.getMessage());

				} catch (IllegalArgumentException e) {
					throw new RuntimeException("Causa di errore: " + e.getMessage());
				} catch (InvocationTargetException e) {
					throw new RuntimeException("Causa di errore: " + e.getMessage());
				}
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("Field errato");
			} catch (SecurityException e) {
				throw new RuntimeException("Causa di errore: " + e.getMessage());
			}
		}
		return out;
	}

	public ArrayList<Element> merge(ArrayList<Element> list1, ArrayList<Element> list2) {
		int i;
		for (i = 0; i < list2.size(); i++) {
			if (!list1.contains((list2).get(i))) {
				list1.add(list2.get(i));
			}
		}
		return list1;
	}
	
	public boolean rightOperator(String... operator) {
		for (String x : operator) {
			// if(!Arrays.asList("gt","lt","eq").contains(x))
			if (!("gt".equals(x) || "lt".equals(x) || "eq".equals(x)))
				return false;
		}
		// throw new RuntimeException("Operatore di confronto non valido");
		return true;
	}
}
