package it.univpm.esameDicembre.Controller;

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
@Service 
public class appService {

	static ArrayList<Element> v = new ArrayList<Element>();
	static ArrayList<Metadata> header = new ArrayList<Metadata>();
	private Calculator<Element> valori = new Calculator<Element>();
	
	@Autowired
	public appService() throws IOException {
		File f = new File("dataset.tsv");
		if (!f.exists()) {
			Utils.JSONDecode("http://data.europa.eu/euodp/data/api/3/action/package_show?id=3h1d8YdPl3KCgkeQNbjkA", "file.tsv");
		}
		Utils.tsvParse(v, header, "file.tsv");
		System.out.println(v.getClass());
	}
	
	public Element printElement(int i) {
		if (i > v.size() || i < 0) {
			throw new RuntimeException("Indice non valido");
		}
		return v.get(i);
	}
	
	public ArrayList<Element> printElement() {
		return v;
	}
	
	public Stats calcolo(String nome_param, String operazione) {
		if(!operazione.contentEquals("sum") && !operazione.contentEquals("min") && !operazione.contentEquals("max") && !operazione.contentEquals("devstd")) {
			throw new RuntimeException("Operatore di calcolo non valido. Operatori validi: sum, min, max, devstd");
		}
		else 
			return new Stats(operazione, operazione + " dei valori di " + nome_param, valori.calcolo(v, nome_param, operazione));
	}
	
	public Stats avg(String nome_param) {
		return new Stats("Avg", "Valore medio per " + nome_param, valori.calcolo(v, nome_param, "sum") / v.size());
	}
	
	public ArrayList<Metadata> printMetadata() {
		return header;
	}
	
	public ArrayList<Stats> Stats(String nome_param) {
		ArrayList<Stats> statistics = new ArrayList<Stats>();
		statistics.add(calcolo(nome_param,"sum"));
		statistics.add(avg(nome_param));
		statistics.add(calcolo(nome_param,"min"));
		statistics.add(calcolo(nome_param,"max"));
		statistics.add(calcolo(nome_param, "devstd"));
		return statistics;
	}
	
		public HashMap<String, Integer> counter(String nome_param)
				throws NoSuchMethodException, RuntimeException, IllegalAccessException, ReflectiveOperationException {
			ArrayList<String> inputColumn = new ArrayList<String>();
			int i;
			for (i = 0; i < v.size(); i++) {
				Method m = v.get(i).getClass()
						.getMethod("get" + nome_param.substring(0, 1).toUpperCase() + nome_param.substring(1), null);
				//Method m = v.get(i).getClass().getMethod("get" + nome_param, null);
				inputColumn.add((String) m.invoke(v.get(i)));
			}
			return Calculator.counter(inputColumn);
		}
	
		public ArrayList<Element> filter(String nome_param, String operator, Object value) {
		if (!filteredData.rightOperator(operator)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: gt, lt, eq");
		}
		return (ArrayList<Element>) filteredData.select(v, nome_param, operator, value);

		}
	public ArrayList<Element> multifilter(String field, String logicOperator, String operator1, Object value1, String operator2,
			Object value2) {
		// check dell'operatore
		if (!filteredData.rightOperator(operator1, operator2)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: gt, lt, eq");
		}
		ArrayList<Element> list1 = new ArrayList<Element>();
		list1 = (ArrayList<Element>) filteredData.select(v, field, operator1, value1);
		if (logicOperator.equals("and")) {
			return (ArrayList<Element>) filteredData.select(list1, field, operator2, value2);
		} else if (logicOperator.equals("or")) {
			ArrayList<Element> list2 = new ArrayList<Element>();
			list2 = (ArrayList<Element>) filteredData.select(v, field, operator2, value2);
			return filteredData.merge(list1, list2);
		}

		else {
			throw new RuntimeException("Operatore logico non valido");
		}
	}
	
		public ArrayList<Element> multifilter(String field, Object value1, String operator2, Object value2) {
		if (!filteredData.rightOperator(operator2)) {
			throw new RuntimeException("Operatore di confronto non valido. Operatori validi: gt, lt, eq");
		}
		ArrayList<Element> list1 = new ArrayList<Element>();
		list1 = (ArrayList<Element>) filteredData.select(v, field, "eq", value1);
		ArrayList<Element> list2 = new ArrayList<Element>();
		list2 = (ArrayList<Element>) filteredData.select(list1, field, operator2, value2);
		return list2;
	}
}
