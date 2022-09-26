package com.cielicki.db.type;

public class Uzytkownik {
	/**
	 * Id u퓓tkownika.
	 */
	private Integer id;
	
	/**
	 * Imie u퓓tkownika.
	 */
	private String imie;
	
	/**
	 * Nazwisko u퓓tkownika.
	 */
	private String nazwisko;
	
	/**
	 * Numer PESEL u퓓tkownika.
	 */
	private String PESEL;
	
	/**
	 * Adres u퓓tkownika.
	 */
	private Adres adres;
	
	/**
	 * Login u퓓tkownika.
	 */
	String login;
	
	/**
	 * Has쿽 u퓓tkowika.
	 */
	String password;
	
	/**
	 * Konstruktor obiektu U퓓tkownik.
	 * 
	 * @param id Id u퓓tkownika.
	 * @param imie Imie u퓓tkownika.
	 * @param nazwisko Nazwisko u퓓tkownika.
	 * @param PESEL Numer PESEL u퓓tkownika.
	 * @param adres Adres u퓓tkownika.
	 */
	public Uzytkownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.PESEL = PESEL;
		this.adres = adres;
	}
	
	/**
	 * Konstruktor obiektu U퓓tkownik.
	 * 
	 * @param id Id u퓓tkownika.
	 * @param imie Imie u퓓tkownika.
	 * @param nazwisko Nazwisko u퓓tkownika.
	 * @param PESEL Numer PESEL u퓓tkownika.
	 * @param adres Adres u퓓tkownika.
	 * @param login Login u퓓tkownika.
	 * @param password Has쿽 u퓓tkowika.
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
