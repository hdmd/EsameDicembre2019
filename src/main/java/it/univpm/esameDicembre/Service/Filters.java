package it.univpm.esameDicembre.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import it.univpm.esameDicembre.Model.Element;

/**
 * Classe di metodi utilizzati per il filtraggio sul dataset.
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0
 */
public class Filters<T> {
	/**
	 * Metodo statico di tipo boolean utilizzato per il controllo dell'operatore di confronto. Se l'operatore è corretto,
	 * si impone se il valore del campo è maggiore, minore o uguale del valore di soglia
	 * @param value Valore del campo
	 * @param op Operatore di confrontp
	 * @param lim Valore di soglia
	 * @return true se l'operatore è corretto, false altrimenti
	 */
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
	/**
	 * Metodo statico di tipo boolean utilizzato per eguagliare due oggetti (di tipo Stringa).
	 * @param word
	 * @param word2
	 * @return true se i due oggetti sono uguali, false se non lo sono
	 */
	public static boolean check2(Object word, Object word2) {
	return word.equals(word2);
	}
	
	
	/**
	 * Metodo di tipo boolean utilizzato per il controllo dell'operatore di confronto.
	 * @param op Operatore di confronto
	 * @return true se l'operatore inserito è corretto, false altrimenti
	 */
	public boolean checkup(String op) {
		if(!(">".contains(op) || "<".contains(op) || "=".contains(op)))
			return false;
		else 
			return true;
		}
	
	/**
	 * Metodo di tipo Collection<T> utilizzato iterando su tutti gli elementi del dataset, ottendendo il valore di ogni
	 * campo inserito come parametro ed effettuando il controllo dell'operatore. Se l'operatore è giusto, aggiunge quell'elemento 
	 * ad un ArrayList<Element> che corrisponde al ritorno del metodo.
	 * Questo metodo è utilizzato per il filttaggio di valori e stringhe.
	 * @param data ArrayList contenente gli elementi del dataset
	 * @param field Campo del dataset
	 * @param operator Operatore di confronto
	 * @param value Valore di soglia
	 * @return ArrayList<Element> contenente gli elementi filtrati
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
	/**
	 * Metodo utilizzato per l'unione di due liste (AttayList).
	 * @param out1 lista 1
	 * @param out2 lista 2
	 * @return
	 */
	public ArrayList<Element> merge(ArrayList<Element> out1, ArrayList<Element> out2) {
		int k;
		for (k = 0; k < out2.size(); k++) {
			if (!out1.contains((out2).get(k))) {
				out1.add(out2.get(k));
			}
		}
		return out1;
	}
	/**
	 * Metodo utilizzato per la rimozione di una lista da un'altra (ArrayList)
	 * @param out1 lista 1
	 * @param out2 lista 2
	 * @return
	 */
	public ArrayList<Element> subtract(ArrayList<Element> out1, ArrayList<Element> out2) {
		int i;
		for (i = 0; i < out2.size(); i++) {
			if (out1.contains((out2).get(i))) {
				out1.remove(out2.get(i));
			}
		}
		return out1;
	}
	/**
	 * Metodo statico di tipo boolean utilizzato per il controllo del campo del dataset.
	 * @param field Campo del dataset
	 * @return true se il campo inserito è corretto, false altrimenti
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static boolean checkfield(String field) throws NoSuchFieldException, SecurityException {
		Field field1 = Element.class.getField(field);
		if (field1.getName().equals(field)) {
			return true;
		} else 
			return false;
	}

}
