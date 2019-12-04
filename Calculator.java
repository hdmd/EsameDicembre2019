package it.univpm.esameDicembre.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Utils.FilterUtils;
import it.univpm.esameDicembre.Controller.appService;
public class Calculator<T> {
	
	private float value;
	
	public float calcolo(Collection<T> data, String nome_param, String operazione) {
		int i = 0;
		
		for (T item : data) {
			try {
				Method m = item.getClass().getMethod("get" + nome_param, null);
				try { 
					if (i==0) {
						value=(Float) m.invoke(item);
						 i++;
						}
					
					else {
					
						if (operazione.equals("sum"))
						value += (Float) m.invoke(item);
					
						if (operazione.equals("min")) {
							if ((Float) m.invoke(item) < value)
								value = (Float) m.invoke(item);
						}
						
						if (operazione.equals("max")) {
							if ((Float) m.invoke(item) > value)
								value = (Float) m.invoke(item);
						}
						if (operazione.equals("devstd")) {
							//value = 0;
							Float sum = (Float) m.invoke(item);
							float avg = sum / data.size();
							
							value += Math.pow((Float) m.invoke(item) - avg, 2);
							
							value /= data.size();
				
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

	public static HashMap<String, Integer> counter(ArrayList<String> column) {
		int i;
		HashMap<String, Integer> uniqueElements = new HashMap<String, Integer>();
		for (i = 0; i < column.size(); i++) {
			if (!uniqueElements.containsKey(column.get(i))) {
				uniqueElements.put(column.get(i), 1);
			} else {
				uniqueElements.replace(column.get(i), uniqueElements.get(column.get(i)),
						uniqueElements.get(column.get(i)) + 1);
			}
		}
		return uniqueElements;
	}
}
