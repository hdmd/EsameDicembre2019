package it.univpm.esameDicembre.Controller;

import java.io.File;

import java.lang.reflect.Method;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univpm.esameDicembre.Utils.FilterUtils;
import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Utils.Calculator;
import it.univpm.esameDicembre.Utils.Utils;

/**
 * Classe che ha la funzione di elaboare i dati e fornirli al Controller
 * per essere esposti verso l'utente.
 * @author Mattia Dignani, Antonio Cozzolino
 *@version 1.0
 */
@Service 
public class AppService {

	static ArrayList<Element> data = new ArrayList<Element>();
	static ArrayList<Metadata> header = new ArrayList<Metadata>();
	private Calculator<Element> values = new Calculator<Element>();
	private FilterUtils<Element> filteredData = new FilterUtils<Element>();
	
	/**
	 * Verifica se il dataset è presente nella directory salvato come file
	 * <em>dataset.tsv</em>, altrimenti effettua direttamente il parsing
	 * dei dati. Se non è presente effettua la decodifica del JSON all'url
	 * specificato seguita dalla ricerca dell'url contenente il dataset ed infine il
	 * download di quest'ultimo.
	 * 
	 * @see it.univpm.esameDicembre.Utils.Utils
	 * @throws IOException
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
	 * Restituisce l'i-esima riga (elemento) del dataset.
	 * 
	 * @param index Numero riga/elemento da restituire.
	 * @see it.univpm.esameDicembre.Model.Element
	 * @return {@link Element}
	 */
	public Element printElement(int index) {
		if (index > data.size() || index < 0) {
			throw new RuntimeException("Indice non valido");
		}
		return data.get(index);
	}
	
	/**
	 * Restituisce l'intero dataset rappresentato da un ArrayList di oggetti di tipo {@link Element}.
	 * 
	 * @return tutti gli elementi/record del dataset
	 */
	public ArrayList<Element> printElement() {
		return data;
	}
	
	/**
	 * Restituisce l'operazione relativa a un determinato indice del dataset.
	 * Operazioni consentite: somma, minimo, massimo, deviazione standard. 
	 * @param field Indice del dataset.
	 * @param operation Operazione applicata ai valori relativi all'indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @see it.univpm.esameDicembre.Utils.Calculator
	 * @return Valore dell'operazione.
	 */
	public Stats calculus(String field, String operation) {
		if(!operation.contentEquals("sum") && !operation.contentEquals("min") && !operation.contentEquals("max") && !operation.contentEquals("devstd")) {
			throw new RuntimeException("Operatore di calcolo non valido. Operatori validi: sum, min, max, devstd");
		}
		else 
			return new Stats(operation, operation + " dei valori di " + field, values.calculation(data, field, operation));
	}
	
	/**
	 * Restituisce il valore medio calcolato su tutti i valori relativi 
	 * a un indice del dataset.
	 * @param field Indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @return Valore medio dei valori relativi all'indice.
	 */
	public Stats avg(String field) {
		return new Stats("average", "Valore medio per " + field, values.calculation(data, field, "sum") / data.size());
	}
	
	/**
	 * Restituisce l'elenco dei metadati del dataset.
	 * 
	 * @return Elenco di tutti i metadati (nome e tipo).
	 * @see it.univpm.esameDicembre.Model.Metadata 
	 */
	public ArrayList<Metadata> printMetadata() {
		return header;
	}
	
	/**
	 * Restituisce l'elenco delle operazioni matematiche relative a un 
	 * determinato indice del dataset.
	 * @param field Indice del dataset.
	 * @return Elenco delle operazioni matematiche relative ad un 
	 * indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 */
	public ArrayList<Stats> Stats(String field) {
		ArrayList<Stats> statistics = new ArrayList<Stats>();
		statistics.add(calculus(field,"sum"));
		statistics.add(avg(field));
		statistics.add(calculus(field,"min"));
		statistics.add(calculus(field,"max"));
		statistics.add(calculus(field,"devstd"));
		return statistics;
	}
	
	/**
	 * Conteggia gli elementi unici e per ognuno di essi indica il numero di occorrenze.
	 * @param field Indice del dataset.
	 * @return Elenco dei valori unici con le rispettive occorrenze.
	 * @throws NoSuchMethodException
	 * @throws RuntimeException
	 * @throws IllegalAccessException
	 * @throws ReflectiveOperationException
	 */
	public HashMap<String, Integer> counter(String field)
				throws NoSuchMethodException, RuntimeException, IllegalAccessException, ReflectiveOperationException {
		ArrayList<String> inputColumn = new ArrayList<String>();
		int i;
		for (i = 0; i < data.size(); i++) {
			Method m = data.get(i).getClass()
					.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);
				//Method m = v.get(i).getClass().getMethod("get" + nome_param, null);
			inputColumn.add((String) m.invoke(data.get(i)));
		}
		return Calculator.counter(inputColumn);
	}
	
	/**
	 * Effettua il filtraggio del dataset. I criteri sono descritti dai parametri variabili 
	 * dati in ingresso, immessi dall'utente e restituisce gli elementi filtrati. Il parametro
	 * word_value (Stringa o numero) identifica l'indice del dataset su cui il filtro deve essere 
	 * applicato. Per ciascun elemento (Stringa o numerico) del dataset relativo all'indice scelto
	 * viene effettuato il confronto, definito da operator, tra il valore o la stringa dell'indice
	 * considerato e word_value. Se l'esito del confronto è positivo, l'elemento viene aggiunto 
	 * all'ArrayList contenente gli elementi filtrati.
	 * @param field Indice del dataset.
	 * @param operator Operatore di confronto.
	 * @param word_value Stringa o valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	public ArrayList<Element> filter(String field, String operator, Object word_value) {
		if (!filteredData.rightOperator(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		}
		return (ArrayList<Element>) filteredData.select(data, field, operator, word_value);

	}
	
	/** 
	 * Effettua la combinazione di due filtri i cui criteri sono descritti dai
	 * parametri dati in ingresso e restituisce tutti elementi filtrati. L'attributo
	 * da considerare è value.
	 * Il parametro logicOperator sta ad indicare se deve essere effettuata l'unione
	 * o l'intersezione dei risultati dei due singoli filtri.
	 * I singoli filtri applicano, per ogni elemento del dataset, un semplice
	 * confronto definito da operatorX tra il valore dell'attributo considerato e
	 * valueX. Se l'esito del confronto è positivo l'elemento viene aggiunto
	 * all'ArrayList contenente gli elementi filtrati.
	 * Per l'operazione di unione si applicano i filtri separatamente e
	 * successivamente si uniscono gli ArrayList risultanti. Per l'operazione di
	 * intersezione il secondo filtro viene applicato all'ArrayList risultante dal
	 * primo filtro e non all'intero dataset.
	 * <strong>NOTA</strong> -Tale filtro viene applicato solamente agli attributi
	 * numerici di {@link Element}
	 * @param field Indice del dataset.
	 * @param logicOperator Operatore logico.
	 * @param operator1 Primo operatore di confronto.
	 * @param value1 Primo valore di confronto.
	 * @param operator2 Secondo operatore di confronto.
	 * @param value2 Secondo valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	public ArrayList<Element> multifilter(String field, String logicOperator, String operator1, Object value1, String operator2, Object value2) {
		if (!filteredData.rightOperator(operator1, operator2)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: gt, lt, eq");
		}
		ArrayList<Element> list1 = new ArrayList<Element>();
		list1 = (ArrayList<Element>) filteredData.select(data, field, operator1, value1);
		if (logicOperator.equals("and")) {
			return (ArrayList<Element>) filteredData.select(list1, field, operator2, value2);
		} else if (logicOperator.equals("or")) {
			ArrayList<Element> list2 = new ArrayList<Element>();
			list2 = (ArrayList<Element>) filteredData.select(data, field, operator2, value2);
			return filteredData.merge(list1, list2);
		}

		else {
			throw new RuntimeException("Operatore logico non valido");
		}
	}
	
	/**
	 * Effettua un filtraggio combinato dato da due filtri. Il risultato del primo è un elenco
	 * caratterizzato da un indice del dataset rappresentante una stringa, 
	 * quello del secondo è un elenco caratterizzato da un indice del dataset rappresentante
	 * un valore numerico; il secondo filtraggio è gestito da operator.
	 * La combinazione di questi due filtri ritorna un elenco filtrato dalla stringa desiderata
	 * e dal valore di soglia scelto, a seocoda dell'operatore.
	 * @param field1 Indice del dataset rappresentante una stringa.	
	 * @param word Stringa per il confronto.
	 * @param field2 Indice del dataset rappresentante un valore numerico.
	 * @param operator Operatore di confronto.
	 * @param value Valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	public ArrayList<Element> multifilter(String field1, Object word, String field2, String operator, Object value) {
		if (!filteredData.rightOperator(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		}
		ArrayList<Element> list1 = new ArrayList<Element>();
		list1 = (ArrayList<Element>) filteredData.select(data, field1, "=", word);
		ArrayList<Element> list2 = new ArrayList<Element>();
		list2 = (ArrayList<Element>) filteredData.select(list1, field2, operator, value);
		return list2;
	}
	
}
