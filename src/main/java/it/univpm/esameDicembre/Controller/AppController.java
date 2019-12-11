package it.univpm.esameDicembre.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import it.univpm.esameDicembre.Model.Element;
import it.univpm.esameDicembre.Model.Metadata;
import it.univpm.esameDicembre.Model.Stats;
import it.univpm.esameDicembre.Controller.AppService;

/**
 * Classe che gestisce le richieste HTTP da parte dell'utente.
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0
 */
@RestController
public class AppController {
	
	@Autowired
	AppService appService; 
	
	/**
	 * Restituisce l'i-esima riga (elemento) del dataset.
	 * 
	 * @param index
	 * @return {@link Element}
	 */
	@GetMapping("/data/{index}")
	public Element data(@PathVariable int index) {
		return appService.printElement(index);
	}
	
	/**
	 * Restituisce l'intero dataset rappresentato da un ArrayList di oggetti {@link Element}.
	 * 
	 * @return Tutte le righe (elementi) del dataset.
	 */
	@GetMapping("/data")
	public ArrayList<Element> dataAll() {
		return appService.printElement();
	}
	
	/**
	 * Restituisce l'operazione relativa a un determinato indice del dataset. 
	 * 
	 * @param field Indice del dataset.
	 * @param operation Operazione applicata ai valori relativi all'indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @return Valore dell'operazione.
	 */
	@GetMapping("/{operation}/{field}")
	public Stats computation(@PathVariable("field") String field, @PathVariable("operation") String operation) {
		return appService.calculus(field, operation);
	}
	
	/**
	 * Restituisce il valore medio calcolato su tutti i valori di un determinato 
	 * indice del dataset.
	 * @param field Indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 * @return Valore medio dei valori relativi all'indice.
	 */
	@GetMapping("/avg/{field}")
	public Stats average(@PathVariable("field") String field) {
		return appService.avg(field);
	}
	
	/**
	 * Restituisce l'elenco dei metadati del dataset.
	 * 
	 * @return Elenco di tutti i metadati (nome e tipo).
	 * @see it.univpm.esameDicembre.Model.Metadata
	 */
	@GetMapping("/metadata")
	public ArrayList<Metadata> metadata() {
		return appService.printMetadata();
	}
	
	/**
	 * Restituisce l'elenco delle operazioni matematiche relative a un 
	 * determinato indice del dataset.
	 * @param field Indice del dataset.
	 * @return Elenco delle operazioni matematiche relative ad un 
	 * indice del dataset.
	 * @see it.univpm.esameDicembre.Model.Stats
	 */
	@GetMapping("/stats/{field}")
	public ArrayList<Stats> stats(@PathVariable("field") String field) {
		return appService.Stats(field);
	}
	
	/**
	 * Conteggia gli elementi unici e per ognuno di essi indica il numero di occorrenze.
	 * @param field Indice del dataset.
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
	 * Effettua il filtraggio del dataset. I criteri sono descritti dai parametri
	 * dati in ingresso, immessi dall'utente e restituisce gli elementi filtrati. Il parametro field 
	 * identifica l'indice del dataset di tipo Stringa su cui il filtro deve essere applicato. 
	 * Se un elemento (Stringa) del dataset relativo all'indice è uguale ( operatore: = ) 
	 * alla stringa word allora tale elemento viene aggiunto all'ArrayList contenente gli elementi filtrati.
	 * 
	 * @param field Indice del dataset.
	 * @param word Stringa di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	@GetMapping("/stringFilter/{field}/{word}")
	public ArrayList<Element> stringFilter(@PathVariable("field") String field, @PathVariable("word") String word) {
		return appService.filter(field, "=", word);
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
	 * @param field Indice del dataset.
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
	 * @param field Indice dell'operatore.
	 * @param operator1 Primo operatore di confronto.
	 * @param value1 Primo valore di confronto.
	 * @param logicOperator Operatore logico.
	 * @param operator2 Secondo operatore di confronto.
	 * @param value2 Secondo valore di confronto.
	 * @return Elenco degli elementi filtrati.
	 */
	@GetMapping("/valueFilter/{field}/{logicOperator}/{operator1}/{value1}/{operator2}/{value2}")
	public ArrayList<Element> valueFilter(@PathVariable("field") String field, @PathVariable("operator1") String operator1,
			@PathVariable("value1") float value1, @PathVariable("logicOperator") String logicOperator,
			@PathVariable("operator2") String operator2, @PathVariable("value2") float value2) {
		return appService.multifilter(field, logicOperator, operator1, value1, operator2, value2);
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
}
