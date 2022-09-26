package com.cielicki.db.type;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cielicki.gui.util.Util;

public class Pracownik extends Uzytkownik {
	
	/**
	 * Data zatrudnienia.
	 */
	private Date dataZatrudnienia;

	/**
	 * Kontruktor obiektu Pracownik.
	 * 
	 * @param id Id pracownika.
	 * @param imie Imie pracownika.
	 * @param nazwisko Nazwisko pracownika.
	 * @param PESEL Numer PESEL pracownika.
	 * @param adres Adres pracownika.
	 * @param dataZatrudnienia Data zatrudnienia.
	 */
	public Pracownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres, Date dataZatrudnienia) {
		super(id, imie, nazwisko, PESEL, adres);
		this.dataZatrudnienia = dataZatrudnienia;
	}
	
	/**
	 * Kontruktor obiektu Pracownik.
	 * 
	 * @param id Id pracownika.
	 * @param imie Imie pracownika.
	 * @param nazwisko Nazwisko pracownika.
	 * @param PESEL Numer PESEL pracownika.
	 * @param adres Adres pracownika.
	 * @param login Login pracownika.
	 * @param password Has³o pracownika.
	 * @param dataZatrudnienia Data zatrudnienia.
	 */
	public Pracownik(Integer id, String imie, String nazwisko, String PESEL, Adres adres, String login, String password, Date dataZatrudnienia) {
		super(id, imie, nazwisko, PESEL, adres, login, password);
		this.dataZatrudnienia = dataZatrudnienia;
	}
	
	/**
	 * Kontruktor obiektu Pracownik.
	 * 
	 * @param uzytkownik Obiekt pracownika.
	 * @param dataZatrudnienia Data zatrudnienia.
	 */
	public Pracownik(Uzytkownik uzytkownik, Date dataZatrudnienia) {
		super(uzytkownik.getId(), uzytkownik.getImie(), uzytkownik.getNazwisko(), uzytkownik.getPESEL(), uzytkownik.getAdres(), uzytkownik.login, uzytkownik.password);
		this.dataZatrudnienia = dataZatrudnienia;
	}

	public Date getDataZatrudnienia() {
		return dataZatrudnienia;
	}

	public void setDataZatrudnienia(Date dataZatrudnienia) {
		this.dataZatrudnienia = dataZatrudnienia;
	}
	
	/**
	 * Zwraca String Insertu do bazy danych.
	 * 
	 * @return String Insertu do bazy danych.
	 */
	public String getInsertPracownicyQuery() {
		return "INSERT INTO pracownicy (id, data_zatrudnienia) VALUES (" + super.getId() + ", '" + Util.df.format(dataZatrudnienia) + "')";
	}
}
