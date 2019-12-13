package it.univpm.esameDicembre.Model;

/**
 * Classe che definisce il singolo riga/elemento del dataset creando oggetti
 * i cui campi rappresentano le singole colonne di dati del file .tsv 
 * utilizzato nel progetto. Sono implementati i setter e i getter, il 
 * metodo toString e un metodo che fornisce come stringa il tipo di ogni 
 * campo.
 * 
 * @author Mattia Dignani, Antonio Cozzolino
 * @version 1.0 
 */
public class Element {
	private String duration;
	private String deg_urb;
	private String sex;
	private String age;
	private String unit;
	private int timegeo; 
	private float EU28;
	private float BE;
	private float BG;
	private float CZ;
	private float DK;
	private float DE;
	private float EE;
	private float IE;
	private float EL;
	private float ES;
	private float FR;
	private float HR;
	private float IT;
	private float CY;
	private float LV;
	private float LT;
	private float LU;
	private float HU;
	private float MT;
	private float NL;
	private float AT;
	private float PL;
	private float PT;
	private float RO;
	private float SI;
	private float SK;
	private float FI;
	private float SE;
	private float UK;
	private float IS;
	private float NO;
	private float TR;
	
	public Element(String duration, String deg_urb, String sex, String age, String unit, int timegeo, float EU28, float BE, float BG, float CZ, float DK,
			float DE, float EE, float IE, float EL, float ES, float FR, float HR, float IT, float CY, float LV, float LT, float LU, float HU, 
			float MT, float NL, float AT, float PL, float PT, float RO, float SI, float SK, float FI, float SE, float UK, float IS, float NO, float TR) {
		this.duration = duration;
		this.deg_urb = deg_urb;
		this.sex = sex;
		this.age = age;
		this.unit = unit;
		this.timegeo = timegeo;
		this.EU28 = EU28;
		this.BE = BE;
		this.BG = BG;
		this.CZ = CZ;
		this.DK = DK;
		this.DE = DE;
		this.EE = EE;
		this.IE = IE;
		this.EL = EL;
		this.ES = ES;
		this.FR = FR;
		this.HR = HR;
		this.IT = IT;
		this.CY = CY;
		this.LV = LV;
		this.LT = LT;
		this.LU = LU;
		this.HU = HU;
		this.MT = MT;
		this.NL = NL;
		this.AT = AT;
		this.PL = PL;
		this.PT = PT;
		this.RO = RO;
		this.SI = SI;
		this.SK = SK;
		this.FI = FI;
		this.SE = SE;
		this.UK = UK;
		this.IS = IS;
		this.NO = NO;
		this.TR = TR;
	}

	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDeg_urb() {
		return deg_urb;
	}
	public void setDeg_urb(String deg_urb) {
		this.deg_urb = deg_urb;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getTimegeo() {
		return timegeo;
	}
	public void setTimegeo(int timegeo) {
		this.timegeo = timegeo;
	}
	public float getEU28() {
		return EU28;
	}
	public void setEU28(float eU28) {
		EU28 = eU28;
	}
	public float getBE() {
		return BE;
	}
	public void setBE(float bE) {
		BE = bE;
	}
	public float getBG() {
		return BG;
	}
	public void setBG(float bG) {
		BG = bG;
	}
	public float getCZ() {
		return CZ;
	}
	public void setCZ(float cZ) {
		CZ = cZ;
	}
	public float getDK() {
		return DK;
	}
	public void setDK(float dK) {
		DK = dK;
	}
	public float getDE() {
		return DE;
	}
	public void setDE(float dE) {
		DE = dE;
	}
	public float getEE() {
		return EE;
	}
	public void setEE(float eE) {
		EE = eE;
	}
	public float getIE() {
		return IE;
	}
	public void setIE(float iE) {
		IE = iE;
	}
	public float getEL() {
		return EL;
	}
	public void setEL(float eL) {
		EL = eL;
	}
	public float getES() {
		return ES;
	}
	public void setES(float eS) {
		ES = eS;
	}
	public float getFR() {
		return FR;
	}
	public void setFR(float fR) {
		FR = fR;
	}
	public float getHR() {
		return HR;
	}
	public void setHR(float hR) {
		HR = hR;
	}
	public float getIT() {
		return IT;
	}
	public void setIT(float iT) {
		IT = iT;
	}
	public float getCY() {
		return CY;
	}
	public void setCY(float cY) {
		CY = cY;
	}
	public float getLV() {
		return LV;
	}
	public void setLV(float lV) {
		LV = lV;
	}
	public float getLT() {
		return LT;
	}
	public void setLT(float lT) {
		LT = lT;
	}
	public float getLU() {
		return LU;
	}
	public void setLU(float lU) {
		LU = lU;
	}
	public float getHU() {
		return HU;
	}
	public void setHU(float hU) {
		HU = hU;
	}
	public float getMT() {
		return MT;
	}
	public void setMT(float mT) {
		MT = mT;
	}
	public float getNL() {
		return NL;
	}
	public void setNL(float nL) {
		NL = nL;
	}
	public float getAT() {
		return AT;
	}
	public void setAT(float aT) {
		AT = aT;
	}
	public float getPL() {
		return PL;
	}
	public void setPL(float pL) {
		PL = pL;
	}
	public float getPT() {
		return PT;
	}
	public void setPT(float pT) {
		PT = pT;
	}
	public float getRO() {
		return RO;
	}
	public void setRO(float rO) {
		RO = rO;
	}
	public float getSI() {
		return SI;
	}
	public void setSI(float sI) {
		SI = sI;
	}
	public float getSK() {
		return SK;
	}
	public void setSK(float sK) {
		SK = sK;
	}
	public float getFI() {
		return FI;
	}
	public void setFI(float fI) {
		FI = fI;
	}
	public float getSE() {
		return SE;
	}
	public void setSE(float sE) {
		SE = sE;
	}
	public float getUK() {
		return UK;
	}
	public void setUK(float uK) {
		UK = uK;
	}
	public float getIS() {
		return IS;
	}
	public void setIS(float iS) {
		IS = iS;
	}
	public float getNO() {
		return NO;
	}
	public void setNO(float nO) {
		NO = nO;
	}
	public float getTR() {
		return TR;
	}
	public void setTR(float tR) {
		TR = tR;
	}
	@Override
	public String toString() {
		return "Element [duration=" + duration + ", deg_urb=" + deg_urb + ", sex=" + sex + ", age=" + age + ", unit="
				+ unit + ", timegeo=" + timegeo + ", EU28=" + EU28 + ", BE=" + BE + ", BG=" + BG + ", CZ=" + CZ
				+ ", DK=" + DK + ", DE=" + DE + ", EE=" + EE + ", IE=" + IE + ", EL=" + EL + ", ES=" + ES + ", FR=" + FR
				+ ", HR=" + HR + ", IT=" + IT + ", CY=" + CY + ", LV=" + LV + ", LT=" + LT + ", LU=" + LU + ", HU=" + HU
				+ ", MT=" + MT + ", NL=" + NL + ", AT=" + AT + ", PL=" + PL + ", PT=" + PT + ", RO=" + RO + ", SI=" + SI
				+ ", SK=" + SK + ", FI=" + FI + ", SE=" + SE + ", UK=" + UK + ", IS=" + IS + ", NO=" + NO + ", TR=" + TR
				+ "]";
	}
	
	/** 
	 * Restituisce come Stringa il tipo dell'attributo il cui nome viene dato come
	 * parametro in ingresso.
	 * @param x
	 * @return Tipo dell'indice del dataset passato come parametro. 
	 */
	public static String type(String x) {
		if (x.equals("duration"))
			return "String";
		if (x.equals("deg_urb"))
			return "String";
		if (x.equals("sex"))
			return "String";
		if (x.equals("age"))
			return "String";
		if (x.equals("unit"))
			return "String";
		if (x.contains("time\\geo"))
			return "Int";
		if (x.contains("EU28"))
			return "Float";
		if (x.contains("BE"))
			return "Float";
		if (x.contains("BG"))
			return "Float";
		if (x.contains("CZ"))
			return "Float";
		if (x.contains("DK"))
			return "Float";
		if (x.contains("DE"))
			return "Float";
		if (x.contains("EE"))
			return "Float";
		if (x.contains("IE"))
			return "Float";
		if (x.contains("EL"))
			return "Float";
		if (x.contains("ES"))
			return "Float";
		if (x.contains("FR"))
			return "Float";
		if (x.contains("HR"))
			return "Float";
		if (x.contains("IT"))
			return "Float";
		if (x.contains("CY"))
			return "Float";
		if (x.contains("LV"))
			return "Float";
		if (x.contains("LT"))
			return "Float";
		if (x.contains("LU"))
			return "Float";
		if (x.contains("HU"))
			return "Float";
		if (x.contains("MT"))
			return "Float";
		if (x.contains("NL"))
			return "Float";
		if (x.contains("AT"))
			return "Float";
		if (x.contains("PL"))
			return "Float";
		if (x.contains("PT"))
			return "Float";
		if (x.contains("RO"))
			return "Float";
		if (x.contains("SI"))
			return "Float";
		if (x.contains("SK"))
			return "Float";
		if (x.contains("FI"))
			return "Float";
		if (x.contains("SE"))
			return "Float";
		if (x.contains("SE"))
			return "Float";
		if (x.contains("UK"))
			return "Float";
		if (x.contains("IS"))
			return "Float";
		if (x.contains("NO"))
			return "Float";
		if (x.contains("TR"))
			return "Float";
		return null;
	}

}
