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
import com.cielicki.db.type.Samochod;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class DodajSamochod extends JFrame {
	
	/**
	 * Tworzy okno dodawania samochodu.
	 * 
	 * @param parent Okno rodzic.
	 * @param pracownik Zalogowany u¿ytkownik.
	 */
	public DodajSamochod(JFrame parent, Pracownik pracownik) {
		parent.setVisible(false);
		this.setTitle("Rejestracja samochodu");
		this.setSize(new Dimension(500, 500));
		Util.centerWindow(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(DodajSamochod.this, "Czy napewno chcesz zamkn¹æ okno?", "Zamykanie okna", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					parent.setVisible(true);
					DodajSamochod.this.dispose();
				}
			}
		});
		
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		
		Dimension labelSize = new Dimension(150, 20);
		
		JLabel markaLabel = new JLabel("Marka");
		markaLabel.setPreferredSize(labelSize);
		
		panel.add(markaLabel);
		
		// Ustawienie imieLabel
		layout.putConstraint(SpringLayout.NORTH, markaLabel, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, markaLabel, 60, SpringLayout.WEST, panel);
		
		JLabel modelLabel = new JLabel("Model");
		modelLabel.setPreferredSize(labelSize);
		
		panel.add(modelLabel);
		
		// Ustawienie nazwiskoLabel
		layout.putConstraint(SpringLayout.NORTH, modelLabel, 15, SpringLayout.SOUTH, markaLabel);
		layout.putConstraint(SpringLayout.WEST, modelLabel, 60, SpringLayout.WEST, panel);
		
		JLabel rokProdukcjiLabel = new JLabel("Rok produkcji");
		rokProdukcjiLabel.setPreferredSize(labelSize);
		
		panel.add(rokProdukcjiLabel);
		
		// Ustawienie peselLabel
		layout.putConstraint(SpringLayout.NORTH, rokProdukcjiLabel, 15, SpringLayout.SOUTH, modelLabel);
		layout.putConstraint(SpringLayout.WEST, rokProdukcjiLabel, 60, SpringLayout.WEST, panel);
		
		JLabel vinLabel = new JLabel("Numer VIN");
		vinLabel.setPreferredSize(labelSize);
		
		panel.add(vinLabel);
		
		// Ustawienie loginLabel
		layout.putConstraint(SpringLayout.NORTH, vinLabel, 15, SpringLayout.SOUTH, rokProdukcjiLabel);
		layout.putConstraint(SpringLayout.WEST, vinLabel, 60, SpringLayout.WEST, panel);
		
		JLabel nrRejLabel = new JLabel("Numer rejestracyjny");
		nrRejLabel.setPreferredSize(labelSize);
		
		panel.add(nrRejLabel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.NORTH, nrRejLabel, 15, SpringLayout.SOUTH, vinLabel);
		layout.putConstraint(SpringLayout.WEST, nrRejLabel, 60, SpringLayout.WEST, panel);
		
		
		
		
		// Ustawienie pól		
		Dimension fieldSize = new Dimension(150, 20);
		int odleglosc = 30;
		
		JTextField markaField = new JTextField();
		markaField.setPreferredSize(fieldSize);
		
		panel.add(markaField);
		
		// Ustawienie imieField
		layout.putConstraint(SpringLayout.NORTH, markaField, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, markaField, odleglosc, SpringLayout.EAST, markaLabel);
		
		JTextField modelField = new JTextField();
		modelField.setPreferredSize(fieldSize);
		
		panel.add(modelField);
		
		// Ustawienie nazwiskoField
		layout.putConstraint(SpringLayout.NORTH, modelField, 15, SpringLayout.SOUTH, markaField);
		layout.putConstraint(SpringLayout.WEST, modelField, odleglosc, SpringLayout.EAST, markaLabel);
		
		JTextField rokProdukcjiField = new JTextField();
		rokProdukcjiField.setPreferredSize(fieldSize);
		
		panel.add(rokProdukcjiField);
		
		// Ustawienie peselField
		layout.putConstraint(SpringLayout.NORTH, rokProdukcjiField, 15, SpringLayout.SOUTH, modelField);
		layout.putConstraint(SpringLayout.WEST, rokProdukcjiField, odleglosc, SpringLayout.EAST, markaLabel);
		
		JTextField vinField = new JTextField();
		vinField.setPreferredSize(fieldSize);
		
		panel.add(vinField);
		
		// Ustawienie loginField
		layout.putConstraint(SpringLayout.NORTH, vinField, 15, SpringLayout.SOUTH, rokProdukcjiField);
		layout.putConstraint(SpringLayout.WEST, vinField, odleglosc, SpringLayout.EAST, markaLabel);
		
		JTextField nrRejField = new JTextField();
		nrRejField.setPreferredSize(fieldSize);
		
		panel.add(nrRejField);
		
		// Ustawienie passwordField
		layout.putConstraint(SpringLayout.NORTH, nrRejField, 15, SpringLayout.SOUTH, vinField);
		layout.putConstraint(SpringLayout.WEST, nrRejField, odleglosc, SpringLayout.EAST, markaLabel);
		
		JButton zapiszButton = new JButton("Zapisz");
		zapiszButton.setPreferredSize(new Dimension(100, 20));
		zapiszButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (
					markaField.getText().isEmpty() || modelField.getText().isEmpty() || rokProdukcjiField.getText().isEmpty() || vinField.getText().isEmpty() || nrRejField.getText().isEmpty()
				) {
					JOptionPane.showMessageDialog(DodajSamochod.this, "Nie podano wszystkich wymaganych danych.", "Niepowodzenia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Samochod samochod = new Samochod(null, markaField.getText(), modelField.getText(), rokProdukcjiField.getText(), vinField.getText(), nrRejField.getText());
				
				if (Db.INSTANCE.dodajSamochod(samochod)) {
					JOptionPane.showMessageDialog(DodajSamochod.this, "Pomyœlnie dodano nowy samochód.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
					DodajSamochod.this.dispose();
					
						new OknoGlowne(pracownik);
				} else {
					JOptionPane.showMessageDialog(DodajSamochod.this, "Samochód ju¿ istnieje.", "Niepowodzenia", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		panel.add(zapiszButton);
		
		// Ustawienie zapiszButton
		layout.putConstraint(SpringLayout.NORTH, zapiszButton, 15, SpringLayout.SOUTH, nrRejLabel);
		layout.putConstraint(SpringLayout.WEST, zapiszButton, 60, SpringLayout.WEST, panel);
		
		JButton wrocButton = new JButton("Wróæ");
		wrocButton.setPreferredSize(new Dimension(100, 20));
		wrocButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DodajSamochod.this.dispose();
				parent.setVisible(true);
			}
		});
		
		panel.add(wrocButton);
		
		// Ustawienie wrocButton
		layout.putConstraint(SpringLayout.NORTH, wrocButton, 15, SpringLayout.SOUTH, nrRejLabel);
		layout.putConstraint(SpringLayout.EAST, wrocButton, 0, SpringLayout.EAST, nrRejField);
		
		this.add(panel);
		this.setVisible(true);
	}
}
