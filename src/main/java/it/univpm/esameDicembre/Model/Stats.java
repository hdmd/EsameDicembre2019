package it.univpm.esameDicembre.Model;

/**
 * Classe che definisce gli indicatori matematici/statistici relativi
 * al detaset. Sono implementati i getter e setter relativi a ciascun campo.
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0
 */
public class Stats {

		private String name;
		private String description;
		private float value;

		public Stats(String name, String description, float value) {
			this.name = name;
			this.description = description;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public float getValue() {
			return value;
		}

		public void setValue(float value) {
			this.value = value;
		}

	}

