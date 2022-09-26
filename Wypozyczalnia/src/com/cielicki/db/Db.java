package com.cielicki.db;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.cielicki.db.type.Adres;
import com.cielicki.db.type.Pracownik;
import com.cielicki.db.type.Samochod;
import com.cielicki.db.type.Umowa;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class Db {
	/**
	 * Instacja obiektu do ubs³ugi bazy danych.
	 */
	public static Db INSTANCE = new Db();
	
	/**
	 * Po³¹czenie do bazy danych.
	 */
	private Connection conn = null;
	
	/**
	 * Kontruktor obiektu Db.
	 */
	private Db() {
		try {
            conn = DriverManager.getConnection("jdbc:sqlite:samochody.db");
        }   catch (SQLException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Zwraca listê samochodów.
	 * 
	 * @return Zwraca listê samochodów.
	 */
	public ArrayList<Samochod> getListaSamochodow() {
		ArrayList<Samochod> lista = new ArrayList<Samochod>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, marka, model, rok_produkcji, vin, numer_rej  FROM samochody");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Samochod samochod = new Samochod(rs.getInt("id"), rs.getString("marka"), rs.getString("model"), rs.getString("rok_produkcji"), rs.getString("vin"), rs.getString("numer_rej"));
				lista.add(samochod);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Zwraca listê samochodów dostêpnych od podanej daty.
	 * 
	 * @param odData Data od której dostêpne maj¹ byæ samochody na liœcie.
	 * 
	 * @return Zwraca listê samochodów dostêpnych od podanej daty.
	 */
	public ArrayList<Samochod> getListaSamochodow(Date odData) {
		ArrayList<Samochod> lista = getListaSamochodow();
		ArrayList<Umowa> listaUmow = new ArrayList<Umowa>();
		
		try {
			for (Samochod samochod : lista) {
				PreparedStatement stmt = conn.prepareStatement("SELECT id, id_prac, id_klient, id_samochodu, data_podpisania, data_od, data_do, akceptacja FROM umowy WHERE id_samochodu = " + samochod.getId());
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					Umowa umowa = new Umowa(rs.getInt("id"), rs.getInt("id_prac"), rs.getInt("id_klient"), rs.getInt("id_samochodu"), rs.getString("data_podpisania"), rs.getString("data_od"), rs.getString("data_do"), (rs.getInt("akceptacja") == 1 ? true : false));
					listaUmow.add(umowa);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator<Samochod> iter = lista.iterator();
		
		while (iter.hasNext()) {
			Samochod samochod = iter.next();
			
			for (Umowa umowa : listaUmow) {
				if (samochod.getId() == umowa.getIdSamochodu()) {
					if (odData.before(umowa.getDataDoAsDate())) {
						iter.remove();
					}
				}
			}
		}
			
		return lista;
	}
	
	/**
	 * Zwraca listê samochodów danego u¿ytkownika.
	 * 
	 * @param idUzytkownika Id u¿ytkownika.
	 * 
	 * @return Zwraca listê samochodów danego u¿ytkownika.
	 */
	public ArrayList<Samochod> getListaSamochodow(int idUzytkownika) {
		ArrayList<Samochod> lista = new ArrayList<Samochod>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT s.id id_sam, s.marka, s.model, s.rok_produkcji, s.vin, s.numer_rej, u.data_do  FROM samochody s JOIN umowy u ON s.id = u.id_samochodu WHERE u.id_klient = " + idUzytkownika);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Date data = new Date();
				
				Samochod samochod = new Samochod(rs.getInt("id_sam"), rs.getString("marka"), rs.getString("model"), rs.getString("rok_produkcji"), rs.getString("vin"), rs.getString("numer_rej"));
				Date dataDo = new Date();
				
				try {
					dataDo  = Util.df.parse(rs.getString("data_do"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if (data.after(dataDo)) {
					continue;
				} else {
					lista.add(samochod);
				}
			} 
		}catch (SQLException e) {
				e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Zwraca samochód o danym id.
	 * 
	 * @param idSamochodu Id samochodu.
	 * 
	 * @return Zwraca samochód o danym id.
	 */
	public Samochod getSamochod(int idSamochodu) {
		Samochod samochod = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, marka, model, rok_produkcji, vin, numer_rej FROM samochody WHERE id = " + idSamochodu);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				samochod = new Samochod(rs.getInt("id"), rs.getString("marka"), rs.getString("model"), rs.getString("rok_produkcji"), rs.getString("vin"), rs.getString("numer_rej"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return samochod;
	}
	
	/**
	 * Zwraca listê u¿ytkowników.
	 * 
	 * @return Zwraca listê u¿ytkowników.
	 */
	public ArrayList<Uzytkownik> getListaUzytkownikow() {
		ArrayList<Uzytkownik> lista = new ArrayList<Uzytkownik>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT u.id, u.imie, u.nazwisko, u.pesel, a.ulica, a.numer_domu, a.numer_mieszkania, a.miasto, a.kod_pocztowy FROM uzytkownicy u join adres a on u.id = a.id");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				PreparedStatement stmtPracownicy = conn.prepareStatement("SELECT count(*) FROM pracownicy WHERE id = " + rs.getInt("id"));
				ResultSet rsPracownicy = stmtPracownicy.executeQuery();
				
				if (rsPracownicy.next()) {
					if (rsPracownicy.getInt(1) > 0) {
						continue;
					}
				}
				
				Adres adres = new Adres(rs.getString("ulica"), rs.getString("numer_domu"), rs.getString("numer_mieszkania"), rs.getString("miasto"), rs.getString("kod_pocztowy"));
				Uzytkownik uzytkownik = new Uzytkownik(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("pesel"), adres);
				
				lista.add(uzytkownik);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Dodaje u¿ytkownika.
	 * 
	 * @param uzytkownik Obiekt u¿ytkownika.
	 * 
	 * @return Zwraca True, jeœli uda³o siê dodaæ u¿ytkownika.
	 */
	public boolean dodajUzytkownika(Uzytkownik uzytkownik) {
		try {
			// Sprawdzenie czy nie istnieje
			PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM uzytkownicy WHERE login = '' OR pesel = ''");
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					return false;
				}
			}
			
			// Dodanie u¿ytkownika
			stmt = conn.prepareStatement(uzytkownik.getInsertQuery());
			stmt.execute();
			
			// Pobranie id dodanego uzytkownika
			stmt = conn.prepareStatement("SELECT last_insert_rowid()");
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				stmt = conn.prepareStatement(uzytkownik.getAdres().getInsertQuery(rs.getInt(1)));
				stmt.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Dodaje pracownika.
	 * 
	 * @param pracownik Obiekt pracownika.
	 * 
	 * @return Zwraca True, jeœli uda³o siê dodaæ pracownika.
	 */
	public boolean dodajPracownika(Pracownik pracownik) {
		try {
			// Sprawdzenie czy nie istnieje
			PreparedStatement stmt = conn.prepareStatement("SELECT count(*) FROM uzytkownicy WHERE login = '' OR pesel = ''");
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					return false;
				}
			}
			
			// Dodanie u¿ytkownika
			stmt = conn.prepareStatement(pracownik.getInsertQuery());
			stmt.execute();
			
			// Pobranie id dodanego uzytkownika
			stmt = conn.prepareStatement("SELECT last_insert_rowid()");
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				pracownik.setId(rs.getInt(1));
				
				stmt = conn.prepareStatement(pracownik.getAdres().getInsertQuery(pracownik.getId()));
				stmt.execute();
				
				stmt = conn.prepareStatement(pracownik.getInsertPracownicyQuery());
				stmt.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Zwraca listê umów u¿ytkownika.
	 * 
	 * @param idUzytkownika Id u¿ytkownika.
	 * 
	 * @return Zwraca listê umów u¿ytkownika.
	 */
	public ArrayList<Umowa> getListaUmowUzytkownika (int idUzytkownika) {
		ArrayList<Umowa> lista =  new ArrayList<Umowa>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, id_prac, id_klient, id_samochodu, data_podpisania, data_od, data_do, akceptacja FROM umowy where id_klient = " + idUzytkownika);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Date data = new Date();
				Umowa umowa = new Umowa(rs.getInt("id"), rs.getInt("id_prac"), rs.getInt("id_klient"), rs.getInt("id_samochodu"), rs.getString("data_podpisania"), rs.getString("data_od"), rs.getString("data_do"), (rs.getInt("akceptacja") == 1 ? true : false));
				
				if (data.after(umowa.getDataDoAsDate())) {
					continue;
				} else {
					lista.add(umowa);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Zwraca listê umów pracownika.
	 * 
	 * @param idPracownika Id pracownika.
	 * 
	 * @return Zwraca listê umów pracownika.
	 */
	public ArrayList<Umowa> getListaUmowPracownika (int idPracownika) {
		ArrayList<Umowa> lista =  new ArrayList<Umowa>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, id_prac, id_klient, id_samochodu, data_podpisania, data_od, data_do, akceptacja FROM umowy where id_prac is null OR id_prac = " + idPracownika);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Date data = new Date();
				Umowa umowa = new Umowa(rs.getInt("id"), rs.getInt("id_prac"), rs.getInt("id_klient"), rs.getInt("id_samochodu"), rs.getString("data_podpisania"), rs.getString("data_od"), rs.getString("data_do"), (rs.getInt("akceptacja") == 1 ? true : false));
				
				if (data.after(umowa.getDataDoAsDate())) {
					continue;
				} else {
					lista.add(umowa);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Zwraca listê umów zwi¹zanych z samochodem.
	 * 
	 * @param idSamochodu Id samochodu.
	 * 
	 * @return Zwraca listê umów zwi¹zanych z samochodem.
	 */
	public ArrayList<Umowa> getListaUmowSamochodu(int idSamochodu) {
		ArrayList<Umowa> lista = new ArrayList<Umowa>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, id_prac, id_klient, id_samochodu, data_podpisania, data_od, data_do, akceptacja FROM umowy where id_samochodu = " + idSamochodu);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Umowa umowa = new Umowa(rs.getInt("id"), rs.getInt("id_prac"), rs.getInt("id_klient"), rs.getInt("id_samochodu"), rs.getString("data_podpisania"), rs.getString("data_od"), rs.getString("data_do"), (rs.getInt("akceptacja") == 1 ? true : false));
				lista.add(umowa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Zwraca u¿ytkownika.
	 * 
	 * @param id Id u¿ytkownika.
	 * 
	 * @return Zwraca u¿ytkownika.
	 */
	public Uzytkownik getUzytkownik(int id) {
		Uzytkownik uzytkownik = null;
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT u.id, u.imie, u.nazwisko, u.pesel, a.ulica, a.numer_domu, a.numer_mieszkania, a.miasto, a.kod_pocztowy FROM uzytkownicy u join adres a on u.id = a.id where u.id = " + id);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Adres adres = new Adres(rs.getString("ulica"), rs.getString("numer_domu"), rs.getString("numer_mieszkania"), rs.getString("miasto"), rs.getString("kod_pocztowy"));
				uzytkownik = new Uzytkownik(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("pesel"), adres);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return uzytkownik;
	}
	
	/**
	 * Zwraca listê u¿ytkowników.
	 * 
	 * @return Zwraca listê u¿ytkowników.
	 */
	public ArrayList<Pracownik> getListaPracownikow() {
		ArrayList<Pracownik> lista = new ArrayList<Pracownik>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id, data_zatrudnienia FROM pracownicy");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				try {
					Pracownik pracownik = new Pracownik(getUzytkownik(rs.getInt("id")), Util.df.parse(rs.getString("data_zatrudnienia")));
					lista.add(pracownik);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	/**
	 * Sprawdza czy u¿ytkownik o podanym loginie i haœle istnieje w bazie danych.
	 * 
	 * @param login Login u¿ytkownika.
	 * @param password Has³o u¿ytkownika.
	 * 
	 * @return Zwraca True, jeœli uda³o siê zalogowaæ.
	 */
	public Uzytkownik zaloguj(String login, String password) {
		boolean wynik = false;
		
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT u.id, u.imie, u.nazwisko, u.pesel, a.ulica, a.numer_domu, a.numer_mieszkania, a.miasto, a.kod_pocztowy FROM uzytkownicy u JOIN adres a ON u.id = a.id where u.login = '" + login + "' AND u.password = '" + password + "'");
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				Adres adres = new Adres(rs.getString("ulica"), rs.getString("numer_domu"), rs.getString("numer_mieszkania"), rs.getString("miasto"), rs.getString("kod_pocztowy"));
				
				int id = rs.getInt("id");
				
				PreparedStatement stmtPracownik = conn.prepareStatement("SELECT data_zatrudnienia FROM pracownicy WHERE id = " + id);
				ResultSet rsPracownik = stmtPracownik.executeQuery();
				
				if (rsPracownik.next()) {
					try {
						Pracownik pracownik = new Pracownik(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("pesel"), adres, Util.df.parse(rsPracownik.getString("data_zatrudnienia")));
						
						
						return pracownik;
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				} else {
					Uzytkownik uzytkownik = new Uzytkownik(rs.getInt("id"), rs.getString("imie"), rs.getString("nazwisko"), rs.getString("pesel"), adres);
					return uzytkownik;
				}
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Dodaje umowê u¿ytkownika.
	 * 
	 * @param umowa Obiekt umowy u¿ytkownika.
	 */
	public void dodajUmoweUzytkownika(Umowa umowa) {
		try {
			PreparedStatement stmt = conn.prepareStatement(umowa.getInsertUzytkownikaQuery());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Akceptuje umowê.
	 * 
	 * @param idUmowy Id umowy.
	 * @param uzytkownik Obiekt u¿ytkownika.
	 */
	public void akceptujUmowe(int idUmowy, Uzytkownik uzytkownik) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE UMOWY SET id_prac = " + uzytkownik.getId() + ", data_podpisania = '" + Util.df.format(new Date())  + "' WHERE id = " + idUmowy);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Usuwa umowê u¿ytkownika o danym id.
	 * 
	 * @param id Id u¿ytkownika.
	 */
	public void usunUmoweUzytkownika(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM UMOWY WHERE id = " + id);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dodaje samochód.
	 * 
	 * @param samochod Obiekt samochodu.
	 * 
	 * @return Zwraca True, jeœli uda³o siê dodaæ samochód.
	 */
	public boolean dodajSamochod(Samochod samochod) {
		try {
			PreparedStatement stmtCheck = conn.prepareStatement("SELECT count(*) FROM SAMOCHODY WHERE NUMER_REJ = '" + samochod.getNrRejestracyjny() + "' OR VIN = '" + samochod.getVIN() + "'");
			ResultSet rs = stmtCheck.executeQuery();
			
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					return false;
				}
			}
			
			PreparedStatement stmt = conn.prepareStatement(samochod.getInsertQuery());
			stmt.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Usuwa samochód o danym id.
	 * 
	 * @param idSamochodu Id samochodu.
	 * 
	 * @return Zwraca True, jeœli uda³o siê usun¹æ samochód.
	 */
	public boolean usunSamochod(int idSamochodu) {
		try {
			ArrayList<Umowa> listaUmow = getListaUmowSamochodu(idSamochodu);
			Date data = new Date();
			
			for (Umowa umowa : listaUmow) {
				if (data.before(umowa.getDataDoAsDate())) {
					return false;
				}
			}
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM SAMOCHODY WHERE id = " + idSamochodu);
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
