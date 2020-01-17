	package it.univpm.esameDicembre.Service;

	import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import it.univpm.esameDicembre.Model.Element;

/**
 * Classe di metodi statici utilizzati per il filtraggio sul dataset.
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0
 */
public class Filters<T> {
	
	public static boolean check1(Float value, String op, Float lim) {
		if (op.equals("="))
			return value.equals(lim);
		else if (op.equals(">"))
			return value > lim;
		else if (op.equals("<"))
			return value < lim;
		else 
			return false;
	}

	public static boolean check2(Object word, Object word_filter) {
		return word.equals(word_filter);
	}

	public Collection<T> select(ArrayList<T> data, String field, String operator, Object value) {
		Collection<T> out = new ArrayList<T>();
		for (T item : data) { 
			try {
				Method m = item.getClass()
						.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);
				try {
					Object tmp = m.invoke(item);
					if (tmp instanceof Number  && value instanceof Number) {
						Float tmp1 = ((Number) tmp).floatValue();
						Float value1 = ((Number) value).floatValue();
					if (Filters.check1(tmp1, operator, value1))
						out.add(item);
					}
					else if(tmp instanceof String && value instanceof String) {
							if (Filters.check2(tmp, value)) {
								out.add(item);
							}
					}
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
	public ArrayList<Element> merge2(ArrayList<Element> list1, ArrayList<Element> list2) {
		int i;
		for (i = 0; i < list2.size(); i++) {
			if (list1.contains((list2).get(i))) {
				list1.remove(list2.get(i));
			}
		}
		return list1;
	}

	public boolean checkup(String op) {
		if(!(">".contains(op) || "<".contains(op) || "=".contains(op)))
			return false;
		else 
			return true;
		}
	
	public static boolean checkfield(String field) throws NoSuchFieldException, SecurityException {
		Field field1 = Element.class.getField(field);
		if (field1.getName().equals(field)) {
			return true;
		} else 
			return false;
	}
}
