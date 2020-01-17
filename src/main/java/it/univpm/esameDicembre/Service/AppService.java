package it.univpm.esameDicembre.Service;

import java.io.File;

import java.lang.reflect.Method;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Utils.Calculator;
import it.univpm.esameDicembre.Utils.Utils;

/**
 * Classe che ha la funzione di elaborare i dati da fornire al controller
 *
 * @author Mattia Dignani, Antonio Cozzolino
 *@version 1.0
 */
@Service 
public class AppService {

	static ArrayList<Element> data = new ArrayList<Element>();
	static ArrayList<Metadata> header = new ArrayList<Metadata>();
	private Calculator<Element> values = new Calculator<Element>();
	private Filters<Element> filteredData = new Filters<Element>();
	
	/**
	 * Verifica se il dataset è presente nella directory e, in tal caso, ne effettua direttamente il parsing
	 * dei dati, altrimenti esegue la decodifica del JSON all'url specificato e successivamente ne effettua il download
	 * 
	 * @see it.univpm.esameDicembre.Utils
	 */
	
	@Autowired
	public AppService() throws IOException {
		File f = new File("dataset.tsv");
		if (!f.exists()) {
		Utils.JSONDecode("https://data.europa.eu/euodp/data/api/3/action/package_show?id=3h1d8YdPl3KCgkeQNbjkA", "file.tsv");
		}
		Utils.tsvParse(data, header, "file.tsv");
	}
	
	/**
	 * Restituisce tutti gli elementi del dataset
	 * 
	 */
	
	public ArrayList<Element> printdata() {
		return data;
	}
	
	/**
	 * Restituisce i metadati del dataset, specificando il nome del campo e il tipo di valore contenuto al suo interno
	 * 
	 */
	
	public ArrayList<Metadata> printMetadata() {
		return header;
	}
	
	/**
	 * Restituisce l'i-esimo elemento del dataset.
	 * 
	 * @param index indice dell'elemento da restituire
	 */

	public Element printElement(int index) {
		if (index > data.size() || index < 0) {
			throw new RuntimeException("Indice non valido");
		}
		return data.get(index);
	}
	
	/**
	 * Dopo aver effettuato il controllo sull'operatore,
	 * Restituisce il risultato dell'operazione desiderata
	 * 
	 * @param field Campo su cui effettuare l'operazione
	 * @param operation operazione matematica richiesta
	 * @return Risultato dell'operazione richiesta
	 */

	public Stats calculus(String field, String operation) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
			return new Stats(operation, operation + " dei valori di " + field, values.calculation(data, field, operation));
	}
	/**
	 * Restituisce la media aritmetica dei valori contenuti nella colonna indicata (field)
	 * @param field Campo su cui effettuare l'operazione
	 * @return Media aritmetica dei valori
	 */

	public Stats avg(String field) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		return new Stats("average", "Valore medio per " + field, values.calculation(data, field, "sum") / data.size());
	}
	
	/**
	 * Restituisce l'elenco delle operazioni matematiche quali: sum, min, max, average, devstd
	 * @param field Campo su cui effettuare le operazioni
	 * @return Elenco delle operazioni matematiche 
	 */
	
	public ArrayList<Stats> Statistics(String field) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		ArrayList<Stats> stats = new ArrayList<Stats>();
		stats.add(calculus(field,"sum"));
		stats.add(avg(field));
		stats.add(calculus(field,"min"));
		stats.add(calculus(field,"max"));
		stats.add(calculus(field,"devstd"));
		return stats;
	}
	
	/**
	 * Conteggia i valori unici assunti da un determinato attributo, per ogni valore
	 * unico indica il numero di occorrenze.
	 * 
	 * @param field Campo su cui effettuare l'operazione
	 * @return Elenco dei valori unici con le rispettive occorrenze
	 */

	public HashMap<String, Integer> counter(String field) throws NoSuchMethodException, RuntimeException, IllegalAccessException, ReflectiveOperationException {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		ArrayList<String> inputColumn = new ArrayList<String>(); //creazione di un ArrayList da riempire.
		int i; //indice di data.size(), usato per l'iterazione
		for (i = 0; i < data.size(); i++) { //iterazione su tutti gli elementi di data
			Method m = data.get(i).getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);  
			inputColumn.add((String) m.invoke(data.get(i)));
		}
		return Calculator.counter(inputColumn);
	}
	
	/**
	 * Esegue un filtraggio sul dataset
	 * Se l'esito del confronto è positivo l'elemento viene aggiunto
	 * all'ArrayList contenente gli elementi filtrati.
	 * Per gli attributi di tipo Stringa l'operator è sempre =
	 * se un elemento del dataset ha come valore dell'attributo considerato lo stesso
	 * di value allora tale elemento viene aggiunto all'ArrayList contenente gli
	 * elementi filtrati.
	 * Restituisce gli elementi che soddisfano la condizione imposta
	 * 
	 * @param field Campo su cui effettuare le operazioni
	 * @param operator operatore
	 * @param word_value parola/valore di confronto
	 * @return Elenco di elementi filtrati
	 */
	
	public ArrayList<Element> filter(String field, String operator, Object word_value) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		if (!filteredData.checkup(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		}
		return (ArrayList<Element>) filteredData.select(data, field, operator, word_value);	
 	}
	
	/**
	 * Esegue un filtraggio combinato sul dataset
	 * Il parametro logicOperator può essere scelto tra or oppure and; indica se deve essere effettuata l'unione
	 * o l'intersezione dei 2 filtri.
	 * Per l'unione si applicano i filtri separatamente e successivamente si uniscono gli ArrayList risultanti.
	 * Per l'intersezione il secondo filtro viene applicato all'ArrayList risultante dal primo filtro e non all'intero dataset.
	 * Restituisce l'elenco di elementi che soddisfano le condizioni imposte dal confronto
	 *  
	 * @param field Campo su cui effettuare l'operazione.
	 * @param operator1 Primo operatore di confronto.
	 * @param value1 Primo valore di confronto.
	 * @param logicOperator Operatore logico.
	 * @param operator2 Secondo operatore di confronto.
	 * @param value2 Secondo valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */

	public ArrayList<Element> multifiltervalue(String field, String logicOperator, String operator1, Object value1, String operator2, Object value2) {
		if (filteredData.checkup(operator1) && filteredData.checkup(operator2)) {
			ArrayList<Element> list1 = new ArrayList<Element>();
			list1 = (ArrayList<Element>) filteredData.select(data, field, operator1, value1);
			if (logicOperator.equals("and")) {
				return (ArrayList<Element>) filteredData.select(list1, field, operator2, value2);
			} else if (logicOperator.equals("or")) {
				ArrayList<Element> list2 = new ArrayList<Element>();
				list2 = (ArrayList<Element>) filteredData.select(data, field, operator2, value2);
				return filteredData.merge(list1, list2);
				} else {
					throw new RuntimeException("Operatore logico non valido");
				}
			} else {
			throw new RuntimeException("Operatori inseriti errati. Inserire >, <, =");
		}
	}
	
	/**
	 * Esegue un filtraggio combinato sul dataset
	 * Restituisce l'elenco di elementi che contengono la stringa indicata (word) nel campo field1
	 * e allo stesso tempo rispettano la condizione imposta dal confronto numerico.
	 *
	 * @param field1 Campo contenente una stringa su cui effettuare l'operazione
	 * @param word Stringa di confronto.
	 * @param field2 Campo contenente un valore numerico su cui effettuare l'operazione
	 * @param operator Operatore di confronto.
	 * @param value Valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */

	public ArrayList<Element> multifilter(String field1, Object word, String field2, String operator, Float value) {
		if (!filteredData.checkup(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		}
		ArrayList<Element> list1 = new ArrayList<Element>();
		list1 = (ArrayList<Element>) filteredData.select(data, field1, "=", word);
		ArrayList<Element> list2 = new ArrayList<Element>();
		list2 = (ArrayList<Element>) filteredData.select(list1, field2, operator, value);
		return list2;
	}
	
	public ArrayList<Element> addBody(Element body1) {
		 data.add(body1);
		 return data;
	}
	
	/**
	 * Elimina un elemento dal dataset
	 *
	 * @param i indice dell'elemento da eliminare
	 * @return elemento eliminato
	 */
	
	public ArrayList<Element> deleteVal(int i) {
		ArrayList<Element> deleteline = new ArrayList<Element>();
		if (i > data.size() || i < 0) {
			throw new RuntimeException("Indice non valido");
		} else {
			deleteline.add( data.get(i) );
		}
		data.remove(i);
		return deleteline;
	}
	
	/**
	 * Elimina una serie di elementi che soddisfano le condizioni indicate
	 *
	 * @param field Campo su cui effettuare le operazioni
	 * @return Elementi rimanenti nel dataset
	 */
	
	public ArrayList<Element> deleteMoreVal(String field, String operator, float value) {
		if (!filteredData.checkup(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		} else {
		ArrayList<Element> eliminatedData = new ArrayList<Element> ();
		eliminatedData = (ArrayList<Element>) filteredData.select(data, field, operator, value);
		
		data = (ArrayList<Element>) filteredData.subtract(data, eliminatedData);
		return data;	
		}
	}	
}
