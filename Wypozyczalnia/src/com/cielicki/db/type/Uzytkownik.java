package com.cielicki.db.type;

public class Uzytkownik {
	/**
	 * Id użytkownika.
	 */
	private Integer id;
	
	/**
	 * Imie użytkownika.
	 */
	private String imie;
	
	/**
	 * Nazwisko użytkownika.
	 */
	private String nazwisko;
	
	/**
	 * Numer PESEL użytkownika.
	 */
	private String PESEL;
	
	/**
	 * Adres użytkownika.
	 */
	private Adres adres;
	
	/**
	 * Login użytkownika.
	 */
	String login;
	
	/**
	 * Hasło użytkowika.
	 */
	String password;
	
	/**
	 * Konstruktor obiektu Użytkownik.
	 * 
	 * @param id Id użytkownika.
	 * @param imie Imie użytkownika.
	 * @param nazwisko Nazwisko użytkownika.
	 * @param PESEL Numer PESEL użytkownika.
	 * @param adres Adres użytkownika.
	 */
	public Uzytkownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.PESEL = PESEL;
		this.adres = adres;
	}
	
	/**
	 * Konstruktor obiektu Użytkownik.
	 * 
	 * @param id Id użytkownika.
	 * @param imie Imie użytkownika.
	 * @param nazwisko Nazwisko użytkownika.
	 * @param PESEL Numer PESEL użytkownika.
	 * @param adres Adres użytkownika.
	 * @param login Login użytkownika.
	 * @param password Hasło użytkowika.
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
