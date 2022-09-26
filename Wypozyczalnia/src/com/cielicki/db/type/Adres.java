package com.cielicki.db.type;

public class Adres {
	/**
	 * Nazwa ulicy.
	 */
	private String ulica;
	
	/**
	 * Numer domu.
	 */
	private String numerDomu;
	
	/**
	 * Numer mieszkania.
	 */
	private String numerMieszkania;
	
	/**
	 * Nazwa miasta.
	 */
	private String miasto;
	
	/**
	 * Kod pocztowy.
	 */
	private String kodPocztowy;
	
	/**
	 * Flaga umówi¹ca czy u¿ytkownik jest pracownikiem.
	 */
	private Boolean czyPracownika;
	
	/**
	 * Konstruktor obiektu Adres.
	 * 
	 * @param ulica Nazwa ulicy.
	 * @param numerDomu Numer domu.
	 * @param numerMieszkania Numer mieszkania.
	 * @param miasto Nazwa miasta.
	 * @param kodPocztowy Kod pocztowy.
	 */
	public Adres(String ulica, String numerDomu, String numerMieszkania, String miasto, String kodPocztowy) {
		this.ulica = ulica;
		this.numerDomu = numerDomu;
		this.numerMieszkania = numerMieszkania;
		this.miasto = miasto;
		this.kodPocztowy = kodPocztowy;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getNumerDomu() {
		return numerDomu;
	}

	public void setNumerDomu(String numerDomu) {
		this.numerDomu = numerDomu;
	}

	public String getNumerMieszkania() {
		return numerMieszkania;
	}

	public void setNumerMieszkania(String numerMieszkania) {
		this.numerMieszkania = numerMieszkania;
	}

	public String getMiasto() {
		return miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getKodPocztowy() {
		return kodPocztowy;
	}

	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}
	
	/**
	 * Zwraca String do Insertu do bazy danych. 
	 * 
	 * @param idUzytkownika Id u¿ytkownika.
	 * 
	 * @return String do Insertu do bazy danych.
	 */
	public String getInsertQuery(Integer idUzytkownika) {
		return "INSERT INTO adres (id, ulica, numer_domu, numer_mieszkania, miasto, kod_pocztowy) VALUES (" + idUzytkownika + ", '" + ulica + "', '" + numerDomu + "', '" + numerMieszkania + "', '" + miasto + "', '" + kodPocztowy + "')";
	}
}
