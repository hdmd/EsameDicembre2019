package it.univpm.esameDicembre.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Service.AppService;

/**
 * La classe AppController gestisce le richieste HTTP da parte del cliente.
 * Selezionando la rotta desiderata, è possibile eseguire una serie comandi
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0
 */

@RestController
public class AppController {
	
	@Autowired
	AppService appService; 
	
	/**
	 * Descrizione comandi per la rotta GET
	 */
	
	/**
	 * Restituisce tutto il dataset organizzato secondo una successione di elementi ordinati in base alla rispettiva posizione nel dataset
	 * 
	 * {@link Element}.
	 * 
	 * @return Tutti gli elementi del dataset
	 */
	
	@GetMapping("/data")
	public ArrayList<Element> data() {
		return appService.printdata();
	}

	/**
	 * Restituisce l'elenco dei metadati del dataset, ognuno di essi identificato dal rispettivo nome e tipo
	 * 
	 * @return Elenco di tutti i metadati.
	 * @see it.univpm.esameDicembre.Model.Metadata
	 */
	
	@GetMapping("/metadata")
	public ArrayList<Metadata> metadata() {
		return appService.printMetadata();
	}
	
	/**
	 * Restituisce l'i-esimo elemento del dataset
	 * 
	 * @param index Indice identificativo dell'elemento
	 * @return {@link Element}
	 */
	
	@GetMapping("/data/{index}")
	public Element element(@PathVariable int index) {
		return appService.printElement(index);
	}
	
	/**
	 * Restituisce il risultato dell'operazione indicata, relativa ad un certo campo del dataset
	 * 
	 * @param field Campo su cui effettuare l'operazione.
	 * @param operation Operazione applicata ai valori relativi al suddetto campo.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @return Valore dell'operazione.
	 */
	
	@GetMapping("/{operation}/{field}")
	public Stats operation(@PathVariable("field") String field, @PathVariable("operation") String operation) throws NoSuchFieldException, SecurityException {
		if(!operation.contentEquals("sum") && !operation.contentEquals("min") && !operation.contentEquals("max") && !operation.contentEquals("devstd")) {
			throw new RuntimeException("Operatore di calcolo non valido. Operatori validi: sum, min, max, devstd");
		} else {
		return appService.calculus(field, operation);
		}
	}
	
	/**
	 * Restituisce la media aritmetica dei valori relativi ad un determinato campo del dataset
	 * 
	 * @param field Campo su cui effettuare l'operazione.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @return Media dei valori relativi al campo indicato.
	 */
	
	@GetMapping("/average/{field}")
	public Stats average(@PathVariable("field") String field) {
		return appService.avg(field);
	}
	
	/**
	 * Restituisce l'elenco delle operazioni matematiche effettuate per un determinato campo del dataset.
	 * @param field Campo su cui effettuare l'operazione.
	 * @return Elenco delle operazioni relative al campo indicato.
	 * @see it.univpm.esameDicembre.Model.Stats
	 */
	
	@GetMapping("/stats/{field}")
	public ArrayList<Stats> stats(@PathVariable("field") String field) throws NoSuchFieldException, SecurityException {
		return appService.Statistics(field);
	}
	
	/**
	 * Restituisce il numero di occorrenze per ciascun valore unico all'interno del dataset.
	 * @param field Campo su cui effettuare l'operazione.
	 * @return Elenco dei valori unici con le rispettive occorrenze.
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws RuntimeException
	 * @throws ReflectiveOperationException
	 */
	
	@GetMapping("/uniqueElements/{field}")
	public HashMap<String, Integer> uniqueElem(@PathVariable String field)
			throws NoSuchMethodException, IllegalAccessException, RuntimeException, ReflectiveOperationException {
		return appService.counter(field);
	}
	
	/**
	 * Effettua un'operazione di filtraggio sul dataset.
	 * I criteri sono descritti dai parametri dati in ingresso, immessi dall'utente e restituisce gli elementi filtrati. Il parametro field 
	 * identifica il campo del dataset di tipo Stringa su cui applicare il filtro.
	 * Se un elemento (Stringa) del dataset relativo all'indice è uguale ( operatore: = ) 
	 * alla stringa word allora tale elemento viene aggiunto all'ArrayList contenente gli elementi filtrati.
	 * 
	 * Restituisce tutti gli elementi che presentano il rispettivo
	 * 
	 * @param field Campo su cui effettuare l'operazione.
	 * @param word Stringa di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	
	@GetMapping("/stringFilter/{field}/{word}")
	public ArrayList<Element> stringFilter(@PathVariable("field") String field, @PathVariable("word") String word) {
		return appService.filter(field, "=", word);
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
	
	@GetMapping("/filter/{field1}/{word}/{field2}/{operator}/{value}")
	public ArrayList<Element> filter(@PathVariable("field1") String field1, @PathVariable("word") String word, @PathVariable("field2") String field2, @PathVariable("operator") String operator,
			@PathVariable("value") float value) {
		return appService.multifilter(field1, word, field2, operator, value);
	}
	
	/**
	 * Effettua il filtraggio del dataset. I criteri sono descritti dai parametri variabili 
	 * dati in ingresso, immessi dall'utente e restituisce gli elementi filtrati. Il parametro 
	 * field di tipo numerico identifica l'indice del dataset su cui il filtro deve essere 
	 * applicato. Per ciascun elemento (numerico) del dataset relativo all'indice scelto viene 
	 * effettuato il confronto, definito da operator, tra il valore relativo all'indice
	 * considerato e value. Se l'esito del confronto è positivo, l'elemento viene aggiunto 
	 * all'ArrayList contenente gli elementi filtrati.
	 * 
	 * @param field Campo su cui effettuare l'operazione.
	 * @param operator Operatore di confronto.
	 * @param value Valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	
	@GetMapping("/valueFilter/{field}/{operator}/{value}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) {
		return appService.filter(field, operator, value);
	}
	
	/**
	 * Effettua la combinazione di due filtri applicati al dataset i cui criteri
	 * sono descritti dai parametri dati in ingresso e restituisce tutti elementi
	 * filtrati. L'attributo considerato è <strong>value</strong>.
	 * Il parametro logicOperator sta ad indicare se deve essere effettuata l'unione
	 * o l'intersezione dei 2 singoli filtri.
	 * I singoli filtri applicano, per ogni elemento del dataset, un semplice
	 * confronto definito da operatorX tra il valore dell'attributo considerato e
	 * valueX. Se l'esito del confronto è positivo, l'elemento viene aggiunto
	 * all'ArrayList contenente gli elementi filtrati.
	 * Per l'operazione di unione si applicano i filtri separatamente e
	 * successivamente si uniscono gli ArrayList risultanti. Per l'operazione di
	 * intersezione il secondo filtro viene applicato all'ArrayList risultante dal
	 * primo filtro e non all'intero dataset.
	 *  
	 * @param field Campo su cui effettuare l'operazione.
	 * @param operator1 Primo operatore di confronto.
	 * @param value1 Primo valore di confronto.
	 * @param logicOperator Operatore logico.
	 * @param operator2 Secondo operatore di confronto.
	 * @param value2 Secondo valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	
	@GetMapping("/valueFilter/{field}/{operator1}/{value1}/{logicOperator}/{operator2}/{value2}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field, @PathVariable("operator1") String operator1,
			@PathVariable("value1") float value1, @PathVariable("logicOperator") String logicOperator,
			@PathVariable("operator2") String operator2, @PathVariable("value2") float value2) {
		return appService.multifiltervalue(field, logicOperator, operator1, value1, operator2, value2);
	}
	
	
	@PostMapping("/data")
	public ArrayList<Element> insert(@RequestBody Element body) {
		return appService.addBody(body);
	}
	
	
	@DeleteMapping("/deleteElement/{index}")
	public ArrayList<Element> deleteValue(@PathVariable int index) {
		return appService.deleteVal(index);
	}

	
	@DeleteMapping("/deletefilter/{field}/{operator}/{value}")
	public ArrayList<Element> delete(@PathVariable("field") String field,
			@PathVariable("operator") String operator, @PathVariable("value") float value) {
			return appService.deleteMoreVal(field, operator, value);
	}	
}




