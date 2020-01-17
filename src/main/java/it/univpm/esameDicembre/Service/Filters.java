package it.univpm.esameDicembre.Service;

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

	public static boolean check2(Object word, Object word2) {
	return word.equals(word2);
	}
	
	
	public boolean checkup(String op) {
		if(!(">".contains(op) || "<".contains(op) || "=".contains(op)))
			return false;
		else 
			return true;
		}
/*	
	public boolean checkfield(ArrayList<T> header, String field) {
		for (T item : header) {
				Method m = item.getClass().getMethod();
				Object tmp = m.invoke(item);
				if () 
					return false;
				else
					return true;						
		}
		
	}
	*/
	
	public Collection<T> select(ArrayList<T> data, String field, String operator, Object value) {
		Collection<T> tmpout = new ArrayList<T>();
		for (T item : data) { //per ogni oggetto (T) item all'interno della collezione data
			try {
				Method m = item.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);
				try {
					Object tmp = m.invoke(item);
					if (tmp instanceof Number  && value instanceof Number) {
						Float tmp1 = ((Number) tmp).floatValue();
						Float value1 = ((Number) value).floatValue();
					if (Filters.check1(tmp1, operator, value1))
						tmpout.add(item);
					}
					else if(tmp instanceof String && value instanceof String) {
							if (Filters.check2(tmp, value)) {
								tmpout.add(item);
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
		return tmpout;
	}

	public ArrayList<Element> merge(ArrayList<Element> out1, ArrayList<Element> out2) {
		int k;
		for (k = 0; k < out2.size(); k++) {
			if (!out1.contains((out2).get(k))) {
				out1.add(out2.get(k));
			}
		}
		return out1;
	}
	public ArrayList<Element> subtract(ArrayList<Element> out1, ArrayList<Element> out2) {
		int i;
		for (i = 0; i < out2.size(); i++) {
			if (out1.contains((out2).get(i))) {
				out1.remove(out2.get(i));
			}
		}
		return out1;
	}

}
