# EsameDicembre2019
Repository dedicata all'esame di Programmazione ad Oggetti del 21 ottobre 2019.   
Il progetto in questione fornisce un'applicazione WEB in JAVA, implementata attraverso l'utilizzo di SpringBoot, che prevede la modellazione di un dataset in formato TSV reperibile al seguente URL: http://data.europa.eu/euodp/data/api/3/action/package_show?id=3h1d8YdPl3KCgkeQNbjkA

# Inizializzazione Dataset
All’avvio del programma viene eseguita l’elaborazione del dataset attraverso la classe Utils, che decodifica il JSON dell'URL sopra indicato per poi effettuare il download e successivamente il parsing del dataset. Il risultato è l’organizzazione dei dati in una tabella i cui elementi riguardano informazioni statistiche sulla popolazione: le prime cinque colonne, identificate rispettivamente dagli indici duration, deg_urb, sex, age, unit, contengono elementi di tipo stringa, mentre le successive contengono elementi numerici (i valori di timegeo e degli indici dei paesi europei sono rispettivamente di tipo intero e float).

# Avvio
L’applicazione avvia un web-server in locale sulla porta 8080 che rimane in attesa di richieste effettuate da client.  
Tramite richieste di tipo GET con determinate rotte, il programma prevede la restituzione di particolari dati o l’esecuzione di operazioni di filtraggio.  
URL per l’applicazione:   
_/http//localhost:8080_

# Restituzione dati:
•	_/data_ -> restituisce tutti gli elementi del dataset.  
•	_/data/{index}_ -> restituisce l’i-esimo elemento del dataset.  
•	_/metadata_ -> restituisce i metadati.

# Restituzione Statistiche:
•	Le richieste _/sum/, /min, /max, /avg, /devstd_ seguite da /{field}, forniscono le rispettive statistiche sui dati relativi a quell’indice.  
Esempio:  
_/min/EU28_ -> restituisce il valore più piccolo che fa riferimento alla colonna dell’indice EU28.
•	La richiesta /stats/{field} restituisce tutte le statistiche relative all’indice specificato.  
# Filtri

**1.Filtri semplici:**
-	Per gli attributi di tipo Stringa:  
_/stringFilter/{field}/{word}_  
Per gli attributi numerici:   
_/valueFilter/{field}/{operator}/{value}_  
Esempio:  
_/stringFilter/EU28/NEV_ -> restituisce tutti gli elementi contenenti la stringa NEV relativa a EU28.  
_/valueFilter/EU28/>/50_ -> restituisce tutti gli elementi con i valori relativi a EU28 maggiori di 50.  
Operator rappresenta l’operatore di confronto: >, <, =; value rappresenta invece il valore di soglia. 

**2.Filtri combinati:**
-	**and** o **or** di due richieste condizionali:  
_/valueFilter/{field}/{logicOperator}/{operator1}/{value1}/{operator2}/{value2}_  
Ciascuna richiesta è definita da operatorX e valueX; logicOperator rappresenta l’operazione di unione o intersezione degli elementi filtrati dai due confronti  
Esempio:
_/valueFilter/EU28/or/>/50/</20_ -> restituisce tutti gli elementi filtrati che sono maggiori di 50 o minori di 20 relativi all’indice EU28.  
-	Restituzione di elementi filtrati da una determinata parola e da un valore di soglia per due indici del dataset:  
_/filter/{field1}/{word}/{field2}/{operator}/{value}_  
field1 e field2 rappresentano sui cui valori verrà applicato il filtraggio; word è il che fa riferimento alla striga scelta; operator è l’operatore di confronto applicato a field2 e value è il valore di confronto.

# Diagrammi:
[Link](https://github.com/hdmd/EsameDicembre2019/blob/master/Diagramma%20dei%20casi%20d'uso.PNG) per il diagramma dei casi d'uso.  
[Link](https://github.com/hdmd/EsameDicembre2019/blob/master/Diagrammadelleclassi.png) per il diagramma delle classi.
