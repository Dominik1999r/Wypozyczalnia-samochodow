package com.cielicki.db.type;

public class Samochod {
	/**
	 * Id samochodu.
	 */
	private Integer id;
	
	/**
	 * Marka samochodu.
	 */
	private String marka;
	
	/**
	 * Model samochodu.
	 */
	private String model;
	
	/**
	 * Rok produkcji samochodu.
	 */
	private String rokProdukcji;
	
	/**
	 * Numer VIN samochodu.
	 */
	private String VIN;
	
	/**
	 * Numer rejestracyjny.
	 */
	private String nrRejestracyjny;
	
	/**
	 * Kontruktor obiektu Samochod.
	 * 
	 * @param id Id samochodu.
	 * @param marka Marka samochodu.
	 * @param model Model samochodu.
	 * @param rokProdukcji Rok produkcji samochodu.
	 * @param VIN Numer VIN samochodu.
	 * @param nrRejestracyjny Numer rejestracyjny.
	 */
	public Samochod(Integer id, String marka, String model, String rokProdukcji, String VIN, String nrRejestracyjny) {
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.rokProdukcji = rokProdukcji;
		this.VIN = VIN;
		this.nrRejestracyjny = nrRejestracyjny;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRokProdukcji() {
		return rokProdukcji;
	}

	public void setRokProdukcji(String rokProdukcji) {
		this.rokProdukcji = rokProdukcji;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getNrRejestracyjny() {
		return nrRejestracyjny;
	}

	public void setNrRejestracyjny(String nrRejestracyjny) {
		this.nrRejestracyjny = nrRejestracyjny;
	}
	
	/**
	 * Zwraca tekst zawieraj¹cy INSERT do bazy obiektu.
	 * 
	 * @return Tekst zawieraj¹cy INSERT do bazy obiektu.
	 */
	public String getInsertQuery() {
		return "INSERT INTO SAMOCHODY (marka, model, rok_produkcji, vin, numer_rej) VALUES ('" + marka + "', '" + model + "', '" + rokProdukcji + "', '" + VIN + "', '" + nrRejestracyjny + "')";
	}
	
	@Override
	public String toString() {
		return marka + ", " + model;
	}
}
