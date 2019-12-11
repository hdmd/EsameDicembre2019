package it.univpm.esameDicembre.Utils;

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
public class FilterUtils<T> {
	/**
	 * Confronto tra i parametri value e th. L'operazione di confronto è effettuata
	 * da operator.
	 * @param value Valore da confrontare.
	 * @param operator Operatore per il confronto.
	 * @param th Valore di soglia/confronto.
	 * @return <strong>true</strong> se il confronto ha avuto esito positivo.
	 *         <strong>false</strong> se il confronto ha avuto esito negativo.
	 */
	
	public static boolean check(Object value, String operator, Object th) {
		if (value instanceof Number && th instanceof Number) {
			Double thC = ((Number) th).doubleValue();
			Double valuec = ((Number) value).doubleValue();
			if (operator.equals("="))
				return value.equals(th);
			else if (operator.equals(">"))
				return valuec > thC;
			else if (operator.equals("<"))
				return valuec < thC;
		} else if (th instanceof String && value instanceof String) {
			return value.equals(th);
		} else {
		}
		return false;
	}
	
	/**
	 * Data la collezione data in ingresso, per ogni suo elemento viene confrontato 
	 * il valore relativo all'indice del dataset e il parametro value. Il confronto è
	 * definito da operator.
	 * @param data Collezione su cui viene applicato il filtro.
	 * @param field Indice del dataset.
	 * @param operator Operatore per il confronto.
	 * @param value Valore di soglia/confronto.
	 * @return Collezione contenente gli elementi filtrati.
	 */
	public Collection<T> select(Collection<T> data, String field, String operator, Object value) {
		Collection<T> out = new ArrayList<T>();
		for (T item : data) {
			try {
				Method m = item.getClass()
						.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);
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
	
	/**
	 * Effettua l'unione di due ArrayList di {@link Element}.
	 * @param list1 Prima lista. 
	 * @param list2 Seconda lista.
	 * @return Unione di list1 e list2 senza elementi duplicati.
	 */
	public ArrayList<Element> merge(ArrayList<Element> list1, ArrayList<Element> list2) {
		int i;
		for (i = 0; i < list2.size(); i++) {
			if (!list1.contains((list2).get(i))) {
				list1.add(list2.get(i));
			}
		}
		return list1;
	}
	
	/**
	 * Controllo sulla validità degli operatori per il confronto necessari
	 * per il filtraggio. 
	 * @param operator Operatore per il confronto.
	 * @return <strong>true</strong> se tutti gli operatori sono validi,
	 *         <strong>false</strong> se almeno un operatore è errato.
	 */
	public boolean rightOperator(String... operator) {
		for (String x : operator) {
			if (!(">".equals(x) || "<".equals(x) || "=".equals(x)))
				return false;
		}
		// throw new RuntimeException("Operatore di confronto non valido");
		return true;
	}
}
