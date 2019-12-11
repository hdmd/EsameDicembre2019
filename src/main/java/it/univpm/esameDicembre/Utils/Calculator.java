package it.univpm.esameDicembre.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Classe di metodi statici che implementa le operazioni matematiche da effettuare
 * sui valori relativi agli indici del dataset. 
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 */
public class Calculator<T> {
	

	private float value;
	/**
	 * Data la collezione data in ingresso, per ogni suo elemento, relativo 
	 * all'indice del dataset scelto,  viene effettuata l'operazione somma, 
	 * minimo, massimo o deviazione standard (viene scelta in input dall'utente). 
	 * @param data Collezione su cui viene effettuata l'operazione
	 * @param field Indice del dataset.
	 * @param operation Operazione.
	 * @return
	 */
	public float calculation(Collection<T> data, String field, String operation) {
		int i = 0;
		
		for (T item : data) {
			try {
				Method m = item.getClass().getMethod("get" + field, null);
				try { 
					if (i == 0) {
						value = (Float) m.invoke(item);
						 i++;
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

	/**
	 * Effettua il controllo e il calcolo degli elementi unici presenti in un elemento.
	 * @param inputColumn 
	 * @return Numero di volte che viene ripetuta una stringa.
	 */
	public static HashMap<String, Integer> counter(ArrayList<String> inputColumn) {
		int i;
		HashMap<String, Integer> uniqueElements = new HashMap<String, Integer>();
		for (i = 0; i < inputColumn.size(); i++) {
			if (!uniqueElements.containsKey(inputColumn.get(i))) {
				uniqueElements.put(inputColumn.get(i), 1);
			} else {
				uniqueElements.replace(inputColumn.get(i), uniqueElements.get(inputColumn.get(i)),
						uniqueElements.get(inputColumn.get(i)) + 1);
			}
		}
		return uniqueElements;
	}
}
