package com.cielicki.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.cielicki.db.Db;
import com.cielicki.db.type.Adres;
import com.cielicki.db.type.Pracownik;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class Rejestracja extends JFrame {
	/**
	 * Tworzy okno do rejestracji.
	 * 
	 * @param parent Obiekt okna Loginu.
	 */
	public Rejestracja(JFrame parent) {
		this(parent, null);
	}
	
	/**
	 * Tworzy okno do rejestracji.
	 * 
	 * @param parent Obiekt okna Loginu.
	 * @param pracownik Zalogowany u¿ytkownik.
	 */
	public Rejestracja(JFrame parent, Pracownik pracownik) {
		parent.setVisible(false);
		this.setTitle("Rejestracja");
		this.setSize(new Dimension(500, 500));
		Util.centerWindow(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(Rejestracja.this, "Czy napewno chcesz zamkn¹æ okno?", "Zamykanie okna", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					parent.setVisible(true);
					Rejestracja.this.dispose();
				}
			}
		});
		
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		
		Dimension labelSize = new Dimension(150, 20);
		
		JLabel imieLabel = new JLabel("Imie");
		imieLabel.setPreferredSize(labelSize);
		
		panel.add(imieLabel);
		
		// Ustawienie imieLabel
		layout.putConstraint(SpringLayout.NORTH, imieLabel, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imieLabel, 60, SpringLayout.WEST, panel);
		
		JLabel nazwiskoLabel = new JLabel("Nazwisko");
		nazwiskoLabel.setPreferredSize(labelSize);
		
		panel.add(nazwiskoLabel);
		
		// Ustawienie nazwiskoLabel
		layout.putConstraint(SpringLayout.NORTH, nazwiskoLabel, 15, SpringLayout.SOUTH, imieLabel);
		layout.putConstraint(SpringLayout.WEST, nazwiskoLabel, 60, SpringLayout.WEST, panel);
		
		JLabel peselLabel = new JLabel("PESEL");
		peselLabel.setPreferredSize(labelSize);
		
		panel.add(peselLabel);
		
		// Ustawienie peselLabel
		layout.putConstraint(SpringLayout.NORTH, peselLabel, 15, SpringLayout.SOUTH, nazwiskoLabel);
		layout.putConstraint(SpringLayout.WEST, peselLabel, 60, SpringLayout.WEST, panel);
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setPreferredSize(labelSize);
		
		panel.add(loginLabel);
		
		// Ustawienie loginLabel
		layout.putConstraint(SpringLayout.NORTH, loginLabel, 15, SpringLayout.SOUTH, peselLabel);
		layout.putConstraint(SpringLayout.WEST, loginLabel, 60, SpringLayout.WEST, panel);
		
		JLabel passwordLabel = new JLabel("Has³o");
		passwordLabel.setPreferredSize(labelSize);
		
		panel.add(passwordLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, passwordLabel, 15, SpringLayout.SOUTH, loginLabel);
		layout.putConstraint(SpringLayout.WEST, passwordLabel, 60, SpringLayout.WEST, panel);
		
		JLabel ulicaLabel = new JLabel("Ulica");
		ulicaLabel.setPreferredSize(labelSize);
		
		panel.add(ulicaLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, ulicaLabel, 15, SpringLayout.SOUTH, passwordLabel);
		layout.putConstraint(SpringLayout.WEST, ulicaLabel, 60, SpringLayout.WEST, panel);
		
		JLabel numerDomuLabel = new JLabel("Numer domu");
		numerDomuLabel.setPreferredSize(labelSize);
		
		panel.add(numerDomuLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, numerDomuLabel, 15, SpringLayout.SOUTH, ulicaLabel);
		layout.putConstraint(SpringLayout.WEST, numerDomuLabel, 60, SpringLayout.WEST, panel);
		
		JLabel numerMieszkaniaLabel = new JLabel("Numer mieszkania");
		numerMieszkaniaLabel.setPreferredSize(labelSize);
		
		panel.add(numerMieszkaniaLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, numerMieszkaniaLabel, 15, SpringLayout.SOUTH, numerDomuLabel);
		layout.putConstraint(SpringLayout.WEST, numerMieszkaniaLabel, 60, SpringLayout.WEST, panel);
		
		JLabel miastoLabel = new JLabel("Miasto");
		miastoLabel.setPreferredSize(labelSize);
		
		panel.add(miastoLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, miastoLabel, 15, SpringLayout.SOUTH, numerMieszkaniaLabel);
		layout.putConstraint(SpringLayout.WEST, miastoLabel, 60, SpringLayout.WEST, panel);
		
		JLabel kodPocztowyLabel = new JLabel("Kod pocztowy");
		kodPocztowyLabel.setPreferredSize(labelSize);
		
		panel.add(kodPocztowyLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, kodPocztowyLabel, 15, SpringLayout.SOUTH, miastoLabel);
		layout.putConstraint(SpringLayout.WEST, kodPocztowyLabel, 60, SpringLayout.WEST, panel);
		
		
		
		
		// Ustawienie pól		
		Dimension fieldSize = new Dimension(150, 20);
		int odleglosc = 30;
		
		JTextField imieField = new JTextField();
		imieField.setPreferredSize(fieldSize);
		
		panel.add(imieField);
		
		// Ustawienie imieField
		layout.putConstraint(SpringLayout.NORTH, imieField, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, imieField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField nazwiskoField = new JTextField();
		nazwiskoField.setPreferredSize(fieldSize);
		
		panel.add(nazwiskoField);
		
		// Ustawienie nazwiskoField
		layout.putConstraint(SpringLayout.NORTH, nazwiskoField, 15, SpringLayout.SOUTH, imieField);
		layout.putConstraint(SpringLayout.WEST, nazwiskoField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField peselField = new JTextField();
		peselField.setPreferredSize(fieldSize);
		
		panel.add(peselField);
		
		// Ustawienie peselField
		layout.putConstraint(SpringLayout.NORTH, peselField, 15, SpringLayout.SOUTH, nazwiskoField);
		layout.putConstraint(SpringLayout.WEST, peselField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField loginField = new JTextField();
		loginField.setPreferredSize(fieldSize);
		
		panel.add(loginField);
		
		// Ustawienie loginField
		layout.putConstraint(SpringLayout.NORTH, loginField, 15, SpringLayout.SOUTH, peselField);
		layout.putConstraint(SpringLayout.WEST, loginField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setPreferredSize(fieldSize);
		
		panel.add(passwordField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, passwordField, 15, SpringLayout.SOUTH, loginField);
		layout.putConstraint(SpringLayout.WEST, passwordField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField ulicaField = new JTextField();
		ulicaField.setPreferredSize(fieldSize);
		
		panel.add(ulicaField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, ulicaField, 15, SpringLayout.SOUTH, passwordField);
		layout.putConstraint(SpringLayout.WEST, ulicaField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField numerDomuField = new JTextField();
		numerDomuField.setPreferredSize(fieldSize);
		
		panel.add(numerDomuField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, numerDomuField, 15, SpringLayout.SOUTH, ulicaField);
		layout.putConstraint(SpringLayout.WEST, numerDomuField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField numerMieszkaniaField = new JTextField();
		numerMieszkaniaField.setPreferredSize(fieldSize);
		
		panel.add(numerMieszkaniaField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, numerMieszkaniaField, 15, SpringLayout.SOUTH, numerDomuField);
		layout.putConstraint(SpringLayout.WEST, numerMieszkaniaField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField miastoField = new JTextField();
		miastoField.setPreferredSize(fieldSize);
		
		panel.add(miastoField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, miastoField, 15, SpringLayout.SOUTH, numerMieszkaniaField);
		layout.putConstraint(SpringLayout.WEST, miastoField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JTextField kodPocztowyField = new JTextField();
		kodPocztowyField.setPreferredSize(fieldSize);
		
		panel.add(kodPocztowyField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, kodPocztowyField, 15, SpringLayout.SOUTH, miastoField);
		layout.putConstraint(SpringLayout.WEST, kodPocztowyField, odleglosc, SpringLayout.EAST, imieLabel);
		
		JButton zapiszButton = new JButton("Zapisz");
		zapiszButton.setPreferredSize(new Dimension(100, 20));
		zapiszButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (
					ulicaField.getText().isEmpty() || numerDomuField.getText().isEmpty() || miastoField.getText().isEmpty() || kodPocztowyField.getText().isEmpty()
					|| imieField.getText().isEmpty() || nazwiskoField.getText().isEmpty() || peselField.getText().isEmpty() || loginField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()	
				) {
					JOptionPane.showMessageDialog(Rejestracja.this, "Nie podano wszystkich wymaganych danych.", "Niepowodzenia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Adres adres = new Adres(ulicaField.getText(), numerDomuField.getText(), numerMieszkaniaField.getText(), miastoField.getText(), kodPocztowyField.getText());
				Uzytkownik uzytkownik = new Uzytkownik(null, imieField.getText(), nazwiskoField.getText(), peselField.getText(), adres, loginField.getText(), new String(passwordField.getPassword()));
				
				boolean czyDodano = false;
				
				if (pracownik != null) {
					Pracownik pracownik = new Pracownik(uzytkownik, new Date());
					czyDodano = Db.INSTANCE.dodajPracownika(pracownik);
				} else {
					czyDodano = Db.INSTANCE.dodajUzytkownika(uzytkownik);
				}
				
				if (czyDodano) {
					JOptionPane.showMessageDialog(Rejestracja.this, "Pomyœlnie dodano u¿ytkownika.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
					Rejestracja.this.dispose();
					
					if (pracownik != null) {
						new OknoGlowne(pracownik);
					} else {
						parent.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(Rejestracja.this, "U¿ytkownik ju¿ istnieje.", "Niepowodzenia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		panel.add(zapiszButton);
		
		// Ustawienie zapiszButton
		layout.putConstraint(SpringLayout.NORTH, zapiszButton, 15, SpringLayout.SOUTH, kodPocztowyLabel);
		layout.putConstraint(SpringLayout.WEST, zapiszButton, 60, SpringLayout.WEST, panel);
		
		JButton wrocButton = new JButton("Wróæ");
		wrocButton.setPreferredSize(new Dimension(100, 20));
		wrocButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Rejestracja.this.dispose();
				parent.setVisible(true);
			}
		});
		
		panel.add(wrocButton);
		
		// Ustawienie wrocButton
		layout.putConstraint(SpringLayout.NORTH, wrocButton, 15, SpringLayout.SOUTH, kodPocztowyLabel);
		layout.putConstraint(SpringLayout.EAST, wrocButton, 0, SpringLayout.EAST, kodPocztowyField);
		
		this.add(panel);
		this.setVisible(true);
	}
}
