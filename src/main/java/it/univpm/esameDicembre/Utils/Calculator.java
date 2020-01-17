package it.univpm.esameDicembre.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 *
 * @author Mattia Dignani, Antonio Cozzolino
 */
public class Calculator<T> {
	

	private float value;
	/**
	 * 
	 */
	public float calculation(Collection<T> data, String field, String operation) {
		int k = 0;
		
		for (T item : data) {
			try {
				Method m = item.getClass().getMethod("get" + field, null);
				try { 
					if (k == 0) {
						value = (Float) m.invoke(item);
						 k++;
						}
					
					else {
					
						if (operation.equals("sum"))
						value += (Float) m.invoke(item);
					
						if (operation.equals("min")) {
							if ((Float) m.invoke(item) < value)
								value = (Float) m.invoke(item);
						}
						
						if (operation.equals("max")) {
							if ((Float) m.invoke(item) > value)
								value = (Float) m.invoke(item);
						}
						if (operation.equals("devstd")) {
							//value = 0;
							float sum = (Float) m.invoke(item);
							float avg = sum / data.size();
							float v = 0;
							v += Math.pow((Float) m.invoke(item) - avg, 2);
							v /= data.size();
							
							value = (float) Math.sqrt(v);
										
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
				throw new RuntimeException("Parametro errato o non presente nel dataset.");
			} catch (SecurityException e) {
				throw new RuntimeException("Causa di errore: " + e.getMessage());
			}
		}
		return value;
	}

	
	public static HashMap<String, Integer> counter(ArrayList<String> inputColumn) {
		int k;
		HashMap<String, Integer> uniques = new HashMap<String, Integer>();
		for (k = 0; k < inputColumn.size(); k++) {
			if (!uniques.containsKey(inputColumn.get(k))) {
				uniques.put(inputColumn.get(k), 1);
			} else {
				uniques.replace(inputColumn.get(k), uniques.get(inputColumn.get(k)),
						uniques.get(inputColumn.get(k)) + 1);
			}
		}
		return uniques;
	}
}
