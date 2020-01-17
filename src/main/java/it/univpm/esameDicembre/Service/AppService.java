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
	private Filters<Element> filteredData = new Filters<Element>();
	@Autowired
	public AppService() throws IOException, NoSuchFieldException, SecurityException {
		File f = new File("dataset.tsv");
		if (!f.exists()) {
			Utils.JSONDecode("https://data.europa.eu/euodp/data/api/3/action/package_show?id=3h1d8YdPl3KCgkeQNbjkA", "file.tsv");
		}
		Utils.tsvParse(data, header, "file.tsv");
	}

	public Element printElement(int index) {
		if (index > data.size() || index < 0) {
			throw new RuntimeException("Indice non valido");
		}	
		return data.get(index);
	}

	public ArrayList<Element> printElement() {
		return data;
	}

	public Stats calculus(String field, String operation) { // throws NoSuchFieldException, SecurityException { 
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		return new Stats(operation, operation + " dei valori di " + field, values.calculation(data, field, operation));
	}

	public Stats avg(String field) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		return new Stats("average", "Valore medio per " + field, values.calculation(data, field, "sum") / data.size());
	}

	public ArrayList<Metadata> printMetadata() {
		return header;
	}

	public ArrayList<Stats> Stats(String field) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		ArrayList<Stats> statistics = new ArrayList<Stats>();
		statistics.add(calculus(field,"sum"));
		statistics.add(avg(field));
		statistics.add(calculus(field,"min"));
		statistics.add(calculus(field,"max"));
		statistics.add(calculus(field,"devstd"));
		return statistics;
	}

	public HashMap<String, Integer> counter(String field) //metodo di tipo hashmap rappresentato da una chiave che indentifica una stringa e da un valore di tipo intero. Gli viene passato field come parametro.
				throws NoSuchMethodException, RuntimeException, IllegalAccessException, ReflectiveOperationException {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		ArrayList<String> inputColumn = new ArrayList<String>(); //creazione di un ArrayList da riempire.
		int i; //indice di data.size(), usato per l'iterazione
		for (i = 0; i < data.size(); i++) { //iterazione su tutti gli elementi di data
			Method m = data.get(i).getClass()
					.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1), null);  
			inputColumn.add((String) m.invoke(data.get(i)));
		}
		return Calculator.counter(inputColumn);
	}
	

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
	

	public ArrayList<Element> multifilter(String field, String logicOperator, String operator1, Object value1, String operator2, Object value2) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		if ((filteredData.checkup(operator1) && filteredData.checkup(operator2))) {	
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
				throw new RuntimeException("Operatore di confronto non valido. Inserire >, <, =");
			}
		  
	}	

	public ArrayList<Element> multifilter(String field1, Object word, String field2, String operator, Float value) {
		try {
			Filters.checkfield(field1); 
			Filters.checkfield(field2);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Uno dei due campo è errato, riprovare");
		}
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
	
	public ArrayList<Element> deleteVal(int i) {
		ArrayList<Element> deleteline = new ArrayList<Element>();
		if (i > data.size() || i < 0) {
			throw new RuntimeException("Indice non valido");
		} else {
			deleteline.add(data.get(i));
		}
		data.remove(i);
		return deleteline;
	}
	
	public ArrayList<Element> deleteMoreVal(String field, String operator, float value) {
		try {
			Filters.checkfield(field); 
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException ("Campo errato, riprovare");
		}
		if (!filteredData.checkup(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: >, <, =");
		} else {
		ArrayList<Element> eliminatedData = new ArrayList<Element> ();
		eliminatedData = (ArrayList<Element>) filteredData.select(data, field, operator, value);
		data = (ArrayList<Element>) filteredData.merge2(data, eliminatedData);
		return data;	
		}
	}	
}
