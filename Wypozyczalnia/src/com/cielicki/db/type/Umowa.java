package com.cielicki.db.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cielicki.gui.util.Util;

public class Umowa {
	/**
	 * Id umowy.
	 */
	private Integer id;
	
	/**
	 * Id pracownika.
	 */
	private Integer idPrac;
	
	/**
	 * Id klienta.
	 */
	private Integer idKlienta;
	
	/**
	 * Id samochodu.
	 */
	private Integer idSamochodu;
	
	/**
	 * Data podpisania.
	 */
	private Date dataPodpisania;
	
	/**
	 * Data pocz¹tku wynajmu.
	 */
	private Date dataOd;
	
	/**
	 * Data koñca wynajmu.
	 */
	private Date dataDo;
	
	/**
	 * Czy umowa zosta³¹ zaakceptowana?
	 */
	private Boolean akceptacja;
	
	/**
	 * Konstruktor obiektu Umowa.
	 * 
	 * @param id Id umowy.
	 * @param idPrac Id pracownika.
	 * @param idKlienta Id klienta.
	 * @param idSamochodu Id samochodu.
	 * @param dataPodpisania Data podpisania.
	 * @param dataOd Data pocz¹tku wynajmu.
	 * @param dataDo Data koñca wynajmu.
	 * @param akceptacja Flaga oznaczaj¹ca status akceptacji umowy.
	 */
	public Umowa(Integer id, Integer idPrac, Integer idKlienta, Integer idSamochodu, String dataPodpisania, String dataOd, String dataDo, Boolean akceptacja) {
		this.id = id;
		this.idPrac = idPrac;
		this.idKlienta = idKlienta;
		this.idSamochodu = idSamochodu;
		
		try {
			this.dataPodpisania = dataPodpisania != null ? Util.df.parse(dataPodpisania) : null;
			this.dataOd = dataOd != null ? Util.df.parse(dataOd) : null;
			this.dataDo = dataDo != null ? Util.df.parse(dataDo) : null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.akceptacja = akceptacja;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPrac() {
		return idPrac;
	}

	public void setIdPrac(Integer idPrac) {
		this.idPrac = idPrac;
	}

	public Integer getIdKlienta() {
		return idKlienta;
	}

	public void setIdKlienta(Integer idKlienta) {
		this.idKlienta = idKlienta;
	}

	public Integer getIdSamochodu() {
		return idSamochodu;
	}

	public void setIdSamochodu(Integer idSamochodu) {
		this.idSamochodu = idSamochodu;
	}

	public String getDataPodpisania() {
		return dataPodpisania != null ? Util.df.format(dataPodpisania) : "";
	}
	
	public Date getDataPodpisaniaAsDate() {
		return dataPodpisania;
	}

	public void setDataPodpisania(Date dataPodpisania) {
		this.dataPodpisania = dataPodpisania;
	}

	public String getDataOd() {
		return dataOd != null ? Util.df.format(dataOd) : "";
	}
	
	public Date getDataOdAsDate() {
		return dataOd;
	}

	public void setDataOd(Date dataOd) {
		this.dataOd = dataOd;
	}

	public String getDataDo() {
		return dataDo != null ? Util.df.format(dataDo) : "";
	}
	
	public Date getDataDoAsDate() {
		return dataDo;
	}

	public void setDataDo(Date dataDo) {
		this.dataDo = dataDo;
	}

	public Boolean getAkceptacja() {
		return akceptacja;
	}

	public void setAkceptacja(Boolean akceptacja) {
		this.akceptacja = akceptacja;
	}
	
	/**
	 * Zwraca String do Insertu do bazy danych dla pracownika.
	 * 
	 * @return String do Insertu do bazy danych dla pracownika.
	 */
	public String getInsertQuery() {
		return "INSERT INTO umowy (id_prac, id_klient, id_samochodu, data_podpisania, data_od, data_do, akceptacja) VALUES (" + idPrac + ", " + idKlienta + ", " + idSamochodu + ", '" + Util.df.format(dataPodpisania) + "', '" + Util.df.format(dataOd) + "', '" + Util.df.format(dataDo) + "', " + (akceptacja ? 1 : 0) + ")";
	}
	
	/**
	 * Zwraca String do Insertu do bazy danych dla u¿ytkownika.
	 * 
	 * @return String do Insertu do bazy danych dla u¿ytkownika.
	 */
	public String getInsertUzytkownikaQuery() {
		return "INSERT INTO umowy (id_klient, id_samochodu, data_od, data_do, akceptacja) VALUES (" + idKlienta + ", " + idSamochodu + ", '" + Util.df.format(dataOd) + "', '" + Util.df.format(dataDo) + "', " + (akceptacja ? 1 : 0) + ")";
	}
}
