package it.univpm.esameDicembre.Model;

/**
 * Classe che definisce i metadati relativi al dataset. Sono implementati 
 * i getter e i setter realtivi a ciascun campo.
 * @author ASUS
 *
 */
public class Metadata {
	
	private String name;
	private String type;

	public Metadata(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public Metadata(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
