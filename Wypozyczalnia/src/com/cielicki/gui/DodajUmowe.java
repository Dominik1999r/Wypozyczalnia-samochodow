package com.cielicki.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;

import com.cielicki.db.Db;
import com.cielicki.db.type.Adres;
import com.cielicki.db.type.Pracownik;
import com.cielicki.db.type.Samochod;
import com.cielicki.db.type.Umowa;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class DodajUmowe extends JFrame {
	
	/**
	 * Tworzy okno dodawania umowy.
	 * 
	 * @param parent Okno rodzica.
	 * @param uzytkownik Zalogowany u¿ytkownik.
	 */
	public DodajUmowe(JFrame parent, Uzytkownik uzytkownik) {
		parent.dispose();
		this.setTitle("Rejestracja umowy");
		this.setSize(new Dimension(500, 500));
		Util.centerWindow(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parent.setVisible(true);
				DodajUmowe.this.dispose();
			}
		});
		
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		
		Dimension labelSize = new Dimension(150, 20);
		
		JLabel klientLabel = new JLabel("Klient");
		klientLabel.setPreferredSize(labelSize);
		
		if (uzytkownik instanceof Pracownik) {
			klientLabel.setVisible(true);
		} else {
			klientLabel.setVisible(false);
		}
		
		panel.add(klientLabel);
		
		// Ustawienie pracownikLabel
		layout.putConstraint(SpringLayout.NORTH, klientLabel, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, klientLabel, 60, SpringLayout.WEST, panel);
		
		
		JLabel dataOdLabel = new JLabel("Data od");
		dataOdLabel.setPreferredSize(labelSize);
		
		panel.add(dataOdLabel);
		
		// Ustawienie dataOdLabel
		layout.putConstraint(SpringLayout.NORTH, dataOdLabel, 15, SpringLayout.SOUTH, klientLabel);
		layout.putConstraint(SpringLayout.WEST, dataOdLabel, 60, SpringLayout.WEST, panel);
		
		JLabel dataDoLabel = new JLabel("Data do");
		dataDoLabel.setPreferredSize(labelSize);
		
		panel.add(dataDoLabel);
		
		// Ustawienie dataDoLabel
		layout.putConstraint(SpringLayout.NORTH, dataDoLabel, 15, SpringLayout.SOUTH, dataOdLabel);
		layout.putConstraint(SpringLayout.WEST, dataDoLabel, 60, SpringLayout.WEST, panel);
		
		
		JLabel samochodLabel = new JLabel("Samochód");
		samochodLabel.setPreferredSize(labelSize);
		
		panel.add(samochodLabel);
		
		// Ustawienie samochodLabel
		layout.putConstraint(SpringLayout.NORTH, samochodLabel, 15, SpringLayout.SOUTH, dataDoLabel);
		layout.putConstraint(SpringLayout.WEST, samochodLabel, 60, SpringLayout.WEST, panel);
		
		
		// Ustawienie pól		
		Dimension fieldSize = new Dimension(150, 20);
		int odleglosc = 30;
		
		JComboBox<Uzytkownik> klientField = new JComboBox<Uzytkownik>();
		
		if (uzytkownik instanceof Pracownik) {
			DefaultComboBoxModel<Uzytkownik> uzytkownikComboModel = new DefaultComboBoxModel<Uzytkownik>();
			klientField.setModel(uzytkownikComboModel);
			ArrayList<Uzytkownik> listaUzytkownikow = Db.INSTANCE.getListaUzytkownikow();
			
			for (Uzytkownik user : listaUzytkownikow) {
				uzytkownikComboModel.addElement(user);
			}
			
		} else {
			klientField.setVisible(false);
		}
		
		klientField.setPreferredSize(fieldSize);
		
		panel.add(klientField);
		
		// Ustawienie pracownikField
		layout.putConstraint(SpringLayout.NORTH, klientField, 30, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, klientField, odleglosc, SpringLayout.EAST, klientLabel);
		
		JDatePicker dataOdField = new JDatePicker(new UtilCalendarModel(), Util.dataFormatString);
		dataOdField.setPreferredSize(fieldSize);
		
		panel.add(dataOdField);
		
		// Ustawienie peselField
		layout.putConstraint(SpringLayout.NORTH, dataOdField, 15, SpringLayout.SOUTH, klientField);
		layout.putConstraint(SpringLayout.WEST, dataOdField, odleglosc, SpringLayout.EAST, klientLabel);
		
		JDatePicker dataDoField = new JDatePicker(new UtilCalendarModel(), Util.dataFormatString);
		dataDoField.setPreferredSize(fieldSize);
		
		panel.add(dataDoField);
		
		// Ustawienie loginField
		layout.putConstraint(SpringLayout.NORTH, dataDoField, 15, SpringLayout.SOUTH, dataOdField);
		layout.putConstraint(SpringLayout.WEST, dataDoField, odleglosc, SpringLayout.EAST, klientLabel);
		
		JComboBox<Samochod> samochodField = new JComboBox<Samochod>();
		DefaultComboBoxModel<Samochod> comboModel = new DefaultComboBoxModel<Samochod>();
		samochodField.setModel(comboModel);
		samochodField.setPreferredSize(fieldSize);
		
		panel.add(samochodField);
		
		// Ustawienie samochodField
		layout.putConstraint(SpringLayout.NORTH, samochodField, 15, SpringLayout.SOUTH, dataDoField);
		layout.putConstraint(SpringLayout.WEST, samochodField, odleglosc, SpringLayout.EAST, dataDoLabel);
		
		// Ustawienie listySamochodów po ustawieniu pola data od
		dataOdField.getFormattedTextField().getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				ArrayList<Samochod> lista = Db.INSTANCE.getListaSamochodow(((Calendar)dataOdField.getModel().getValue()).getTime());
				
				comboModel.removeAllElements();
				
				for (Samochod samochod : lista) {
					comboModel.addElement(samochod);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
			}
		});
		
		JButton zapiszButton = new JButton("Zapisz");
		zapiszButton.setPreferredSize(new Dimension(100, 20));
		zapiszButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (uzytkownik instanceof Pracownik) {
					if (
						dataOdField.getModel().getValue() == null || dataDoField.getModel().getValue() == null || klientField.getSelectedItem() == null || samochodField.getSelectedItem() == null
					) {
						JOptionPane.showMessageDialog(DodajUmowe.this, "Nie podano wszystkich wymaganych danych.", "Niepowodzenia", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				
				Umowa umowa = new Umowa(null, null, uzytkownik.getId(), ((Samochod) samochodField.getSelectedItem()).getId(), null, dataOdField.getFormattedTextField().getText(), dataDoField.getFormattedTextField().getText(), false);
				Db.INSTANCE.dodajUmoweUzytkownika(umowa);
				
				JOptionPane.showMessageDialog(DodajUmowe.this, "Pomyœlnie zarejestrowan¹ nowy wynajem.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
				DodajUmowe.this.dispose();
				
				if (uzytkownik instanceof Pracownik) {
					new OknoGlowne((Pracownik) uzytkownik);
				} else {
					new OknoGlowne(uzytkownik);
				}
			}
		});
		
		panel.add(zapiszButton);
		
		// Ustawienie zapiszButton
		layout.putConstraint(SpringLayout.NORTH, zapiszButton, 15, SpringLayout.SOUTH, samochodLabel);
		layout.putConstraint(SpringLayout.WEST, zapiszButton, 60, SpringLayout.WEST, panel);
		
		JButton wrocButton = new JButton("Wróæ");
		wrocButton.setPreferredSize(new Dimension(100, 20));
		wrocButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DodajUmowe.this.dispose();
				
				if (uzytkownik instanceof Pracownik) {
					new OknoGlowne((Pracownik) uzytkownik);
				} else {
					new OknoGlowne(uzytkownik);
				}
			}
		});
		
		panel.add(wrocButton);
		
		// Ustawienie wrocButton
		layout.putConstraint(SpringLayout.NORTH, wrocButton, 15, SpringLayout.SOUTH, samochodLabel);
		layout.putConstraint(SpringLayout.EAST, wrocButton, 0, SpringLayout.EAST, samochodField);
		
		this.add(panel);
		this.setVisible(true);
	}
}
