package com.cielicki.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.cielicki.db.Db;
import com.cielicki.db.type.Pracownik;
import com.cielicki.db.type.Samochod;
import com.cielicki.db.type.Umowa;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class OknoGlowne extends JFrame {
	DefaultTableModel samochodyTableModel;
	
	/**
	 * Tworzy g³owne okno programu dla u¿ytkownika.
	 * 
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	public OknoGlowne(Uzytkownik uzytkownik) {
		this.setTitle("Panel klienta");
		this.setSize(new Dimension(1024, 800));
		this.setBackground(Color.white);
		Util.centerWindow(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel górny
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(1024, 50));
		
		JLabel powitanieLabel = new JLabel("Dzieñ dobry " + uzytkownik.getImie() + "!");
		powitanieLabel.setFont(new Font("Default", Font.BOLD, 25));
		JLabel czasLabel = new JLabel(); 
		topPanel.add(powitanieLabel, BorderLayout.CENTER);
		topPanel.add(czasLabel, BorderLayout.EAST);
		
		OdswiezanieCzasu odswiezanieCzasu = new OdswiezanieCzasu(czasLabel);
		odswiezanieCzasu.start();
		
		// Zak³adki
		JTabbedPane tabPanel = new JTabbedPane();
		tabPanel.setBackground(Color.white);
		
		// Panel z umowami
		dodajPanelUmowy(tabPanel, uzytkownik);
		
		// Panel z samochodami
		dodajPanelSamochody(tabPanel, uzytkownik);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(tabPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * Tworzy g³owne okno programu dla pracownika.
	 * 
	 * @param pracownik Zalogowany pracownik.
	 */
	public OknoGlowne(Pracownik pracownik) {
		this.setTitle("Panel zarz¹dzania");
		this.setSize(new Dimension(1024, 800));
		this.setBackground(Color.white);
		Util.centerWindow(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel górny
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(1024, 50));
		
		JLabel powitanieLabel = new JLabel("Dzieñ dobry " + pracownik.getImie() + "!");
		powitanieLabel.setFont(new Font("Default", Font.BOLD, 25));
		JLabel czasLabel = new JLabel(); 
		topPanel.add(powitanieLabel, BorderLayout.CENTER);
		topPanel.add(czasLabel, BorderLayout.EAST);
		
		OdswiezanieCzasu odswiezanieCzasu = new OdswiezanieCzasu(czasLabel);
		odswiezanieCzasu.start();
		
		// Zak³adki
		JTabbedPane tabPanel = new JTabbedPane();
		tabPanel.setBackground(Color.white);
		
		// Panel z umowami
		dodajPanelUmowy(tabPanel, pracownik);
		
		// Panel z samochodami
		dodajPanelSamochody(tabPanel, pracownik);
		
		// Panel z klientami
		dodajPanelKlienci(tabPanel, pracownik);
		
		// Panel z pracownikami
		dodajPanelPracownicy(tabPanel, pracownik);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(tabPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * Tworzy zak³adkê z umowami.
	 * 
	 * @param tabPanel Panel zak³adek.
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	private void dodajPanelUmowy(JTabbedPane tabPanel, Uzytkownik uzytkownik) {
		JPanel umowyPanel = new JPanel(new BorderLayout());
		umowyPanel.setPreferredSize(new Dimension(1024, 650));
		ImageIcon umowyImage = new ImageIcon(this.getClass().getClassLoader().getResource("umowyIcon.png"));
		tabPanel.addTab("Umowy", umowyImage, umowyPanel);
		
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(824, 650));
		
		JTable tabela = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(0, 5);
		tabela.setModel(tableModel);
		tableModel.setColumnIdentifiers(new String[] {"ID", "Data podpisania", "Pocz¹tek", "Koniec", "Pracownik", "Samochód"});
		tabela.getColumnModel().removeColumn(tabela.getColumnModel().getColumn(0));
		
		odswiezListeUmow(uzytkownik, tableModel);
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setPreferredSize(listPanel.getPreferredSize());
		listPanel.add(scrollPane);
		
		umowyPanel.add(listPanel, BorderLayout.WEST);
		
		// Panel z przyciskami
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 650));
		
		umowyPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton dodajButton = new JButton("Dodaj umowê");
		dodajButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DodajUmowe(OknoGlowne.this, uzytkownik);
			}
		});
		
		buttonPanel.add(dodajButton);
		
		JButton usunButton = new JButton("Usuñ umowê");
		
		usunButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabela.getSelectedRow();
				
				if (selectedRow != -1) {
					Db.INSTANCE.usunUmoweUzytkownika((int)tableModel.getValueAt(selectedRow, 0));
					odswiezListeUmow(uzytkownik, tableModel);
					odswiezListeSamochodow(uzytkownik, samochodyTableModel);
				}
			}
		});
		
		buttonPanel.add(usunButton);
		
		JButton akceptujButton = new JButton("Zaakceptuj");
		
		akceptujButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tabela.getSelectedRow();
				
				if (selectedRow != -1) {
					if (! ((String)tableModel.getValueAt(selectedRow, 1)).isEmpty()) {
						JOptionPane.showMessageDialog(OknoGlowne.this, "Umowa zosta³a ju¿ zaakceptowana!", "Akceptacja?", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					if (JOptionPane.showConfirmDialog(OknoGlowne.this, "Czy napewno chcesz zaakceptowaæ t¹ umowê?", "Akceptacja?", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
						Db.INSTANCE.akceptujUmowe((int)tableModel.getValueAt(selectedRow, 0), uzytkownik);
						odswiezListeUmow(uzytkownik, tableModel);
					}
				}
			}
		});
		
		if (uzytkownik instanceof Pracownik) {
			akceptujButton.setVisible(true);
		} else {
			akceptujButton.setVisible(false);
		}
		
		buttonPanel.add(akceptujButton);
	}
	
	/**
	 * Tworzy zak³adkê z samochodami.
	 * 
	 * @param tabPanel Panel zak³adek.
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	private void dodajPanelSamochody(JTabbedPane tabPanel, Uzytkownik uzytkownik) {
		JPanel samochodyPanel = new JPanel(new BorderLayout());
		samochodyPanel.setPreferredSize(new Dimension(1024, 650));
		ImageIcon samochodyImage = new ImageIcon(this.getClass().getClassLoader().getResource("samochodyIcon.png"));
		tabPanel.addTab("Samochody", samochodyImage, samochodyPanel);
		
		JPanel listPanel = new JPanel();
		if (uzytkownik instanceof Pracownik) {
			listPanel.setPreferredSize(new Dimension(824, 650));
		} else {
			listPanel.setPreferredSize(samochodyPanel.getPreferredSize());
		}
		
		JTable tabela = new JTable();
		samochodyTableModel = new DefaultTableModel(0, 6);
		tabela.setModel(samochodyTableModel);
		samochodyTableModel.setColumnIdentifiers(new String[] {"ID", "Marka", "Model", "Rok produkcji", "VIN", "Numer rejestracyjny"});
		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setWidth(0);
		
		odswiezListeSamochodow(uzytkownik, samochodyTableModel);
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setPreferredSize(listPanel.getPreferredSize());
		listPanel.add(scrollPane);
		
		samochodyPanel.add(listPanel, BorderLayout.WEST);
		
		if (uzytkownik instanceof Pracownik) {
			// Panel z przyciskami
			JPanel buttonPanel = new JPanel();
			buttonPanel.setPreferredSize(new Dimension(200, 650));
			
			samochodyPanel.add(buttonPanel, BorderLayout.EAST);
			
			JButton dodajButton = new JButton("Dodaj samochód");
			
			dodajButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new DodajSamochod(OknoGlowne.this, (Pracownik) uzytkownik);
				}
			});
			
			buttonPanel.add(dodajButton);
			
			JButton usunButton = new JButton("Usuñ samochód");
			
			usunButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = tabela.getSelectedRow();
					
					if (selectedRow != -1) {
						if (Db.INSTANCE.usunSamochod((int)samochodyTableModel.getValueAt(selectedRow, 0))) {
							JOptionPane.showMessageDialog(OknoGlowne.this, "Pomyœlnie usuniêto samochód.", "Usuniêto samochód", JOptionPane.INFORMATION_MESSAGE);
							odswiezListeSamochodow(uzytkownik, samochodyTableModel);
						} else {
							JOptionPane.showMessageDialog(OknoGlowne.this, "Samochód jest podmiotem aktualnej umowy najmu.\nNie mo¿na w tej chwili usun¹æ.", "Niepowodzenie", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});
			
			buttonPanel.add(usunButton);
		}
	}
	
	/**
	 * Tworzy zak³adkê z  klienci.
	 * 
	 * @param tabPanel Panel zak³adek.
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	private void dodajPanelKlienci(JTabbedPane tabPanel, Uzytkownik uzytkownik) {
		JPanel klienciPanel = new JPanel(new BorderLayout());
		klienciPanel.setPreferredSize(new Dimension(1024, 650));
		ImageIcon klienciImage = new ImageIcon(this.getClass().getClassLoader().getResource("klientIcon.png"));
		tabPanel.addTab("Klienci", klienciImage, klienciPanel);
		
		JTable tabela = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(0, 5);
		tabela.setModel(tableModel);
		tableModel.setColumnIdentifiers(new String[] {"ID", "Imie", "Nazwisko", "PESEL", "Adres"});
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setWidth(0);
		tabela.getColumnModel().getColumn(4).setMinWidth(0);
		tabela.getColumnModel().getColumn(4).setMaxWidth(0);
		tabela.getColumnModel().getColumn(4).setWidth(0);
		
		ArrayList<Uzytkownik> listaUzytkownikow = Db.INSTANCE.getListaUzytkownikow();
		
		for (Uzytkownik user : listaUzytkownikow) {
			tableModel.addRow(new Object[] {user.getId(), user.getImie(), user.getNazwisko(), user.getPESEL(), user.getAdres()});
		}
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		scrollPane.setPreferredSize(klienciPanel.getPreferredSize());
		klienciPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * Tworzy zak³adkê z pracownikami.
	 * 
	 * @param tabPanel Panel zak³adek.
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	private void dodajPanelPracownicy(JTabbedPane tabPanel, Uzytkownik uzytkownik) {
		JPanel pracownicyPanel = new JPanel(new BorderLayout());
		pracownicyPanel.setPreferredSize(new Dimension(1024, 650));
		ImageIcon pracownicyImage = new ImageIcon(this.getClass().getClassLoader().getResource("pracownikIcon.png"));
		tabPanel.addTab("Pracownicy", pracownicyImage, pracownicyPanel);
		
		JTable tabela = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel(0, 6);
		tabela.setModel(tableModel);
		tableModel.setColumnIdentifiers(new String[] {"ID", "Imie", "Nazwisko", "PESEL", "Zatrudniony", "Adres"});
		tabela.getColumnModel().getColumn(0).setMinWidth(0);
		tabela.getColumnModel().getColumn(0).setMaxWidth(0);
		tabela.getColumnModel().getColumn(0).setWidth(0);
		tabela.getColumnModel().getColumn(5).setMinWidth(0);
		tabela.getColumnModel().getColumn(5).setMaxWidth(0);
		tabela.getColumnModel().getColumn(5).setWidth(0);
		
		ArrayList<Pracownik> listaPracownikow = Db.INSTANCE.getListaPracownikow();
		
		for (Pracownik user : listaPracownikow) {
			tableModel.addRow(new Object[] {user.getId(), user.getImie(), user.getNazwisko(), user.getPESEL(), Util.df.format(user.getDataZatrudnienia()), user.getAdres()});
		}
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		scrollPane.setPreferredSize(new Dimension(824, 650));
		pracownicyPanel.add(scrollPane, BorderLayout.WEST);
		
		// Panel z przyciskami
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 650));
		
		pracownicyPanel.add(buttonPanel, BorderLayout.EAST);
		
		JButton dodajButton = new JButton("Dodaj pracownika");
		
		dodajButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Rejestracja(OknoGlowne.this, (Pracownik) uzytkownik);
				OknoGlowne.this.dispose();
			}
		});
		
		buttonPanel.add(dodajButton);
	}
	
	/**
	 * Odœwie¿a listê umów.
	 * 
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 * @param tabelModel Model tabeli.
	 */
	public void odswiezListeUmow(Uzytkownik uzytkownik, DefaultTableModel tableModel) {
		ArrayList<Umowa> umowyUzytkownika = new ArrayList<Umowa>();
		
		if (uzytkownik instanceof Pracownik) {
			umowyUzytkownika = Db.INSTANCE.getListaUmowPracownika(uzytkownik.getId());
		} else {
			umowyUzytkownika = Db.INSTANCE.getListaUmowUzytkownika(uzytkownik.getId());
		}
		
		int rowCount = tableModel.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		for (Umowa umowa : umowyUzytkownika) {
			tableModel.addRow(new Object[] {umowa.getId(), umowa.getDataPodpisania(), umowa.getDataOd(), umowa.getDataDo(), Db.INSTANCE.getUzytkownik(umowa.getIdPrac()), Db.INSTANCE.getSamochod(umowa.getIdSamochodu())});
		}
	}
	
	/**
	 * Odœwie¿a listê samochodów.
	 * 
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 * @param tableModel Model tabeli.
	 */
	public void odswiezListeSamochodow(Uzytkownik uzytkownik, DefaultTableModel tableModel) {
		ArrayList<Samochod> samochodyUzytkownika = new ArrayList<Samochod>();
		
		if (uzytkownik instanceof Pracownik) {
			samochodyUzytkownika = Db.INSTANCE.getListaSamochodow();
		} else {
			samochodyUzytkownika = Db.INSTANCE.getListaSamochodow(uzytkownik.getId());
		}
		
		int rowCount = tableModel.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		
		for (Samochod samochod : samochodyUzytkownika) {
			tableModel.addRow(new Object[] {samochod.getId(), samochod.getMarka(), samochod.getModel(), samochod.getRokProdukcji(), samochod.getVIN(), samochod.getNrRejestracyjny()});
		}
	}
}

class OdswiezanieCzasu extends Thread {
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
	JLabel label;
	
	public OdswiezanieCzasu(JLabel label) {
		this.label = label;
	}
	
	@Override
	public void run() {
		while (true) {
			label.setText(df.format(new Date()));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
