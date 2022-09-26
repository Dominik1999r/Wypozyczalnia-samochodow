package com.cielicki.db.type;

public class Uzytkownik {
	/**
	 * Id u�ytkownika.
	 */
	private Integer id;
	
	/**
	 * Imie u�ytkownika.
	 */
	private String imie;
	
	/**
	 * Nazwisko u�ytkownika.
	 */
	private String nazwisko;
	
	/**
	 * Numer PESEL u�ytkownika.
	 */
	private String PESEL;
	
	/**
	 * Adres u�ytkownika.
	 */
	private Adres adres;
	
	/**
	 * Login u�ytkownika.
	 */
	String login;
	
	/**
	 * Has�o u�ytkowika.
	 */
	String password;
	
	/**
	 * Konstruktor obiektu U�ytkownik.
	 * 
	 * @param id Id u�ytkownika.
	 * @param imie Imie u�ytkownika.
	 * @param nazwisko Nazwisko u�ytkownika.
	 * @param PESEL Numer PESEL u�ytkownika.
	 * @param adres Adres u�ytkownika.
	 */
	public Uzytkownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.PESEL = PESEL;
		this.adres = adres;
	}
	
	/**
	 * Konstruktor obiektu U�ytkownik.
	 * 
	 * @param id Id u�ytkownika.
	 * @param imie Imie u�ytkownika.
	 * @param nazwisko Nazwisko u�ytkownika.
	 * @param PESEL Numer PESEL u�ytkownika.
	 * @param adres Adres u�ytkownika.
	 * @param login Login u�ytkownika.
	 * @param password Has�o u�ytkowika.
	 */
	public Uzytkownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres, String login, String password) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.PESEL = PESEL;
		this.adres = adres;
		this.login = login;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getPESEL() {
		return PESEL;
	}

	public void setPESEL(String pESEL) {
		PESEL = pESEL;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	
	/**
	 * Zwraca String do Insertu ddo bazy danych.
	 * 
	 * @return String do Insertu ddo bazy danych. 
	 */
	public String getInsertQuery() {
		return "INSERT INTO uzytkownicy (imie, nazwisko, pesel, login, password) VALUES ('" + imie + "', '" + nazwisko + "', '" + PESEL + "', '" + login +"', '" + password + "')";
	}
	
	@Override
	public String toString() {
		return this.getImie() + " " + this.getNazwisko();
	}
}
