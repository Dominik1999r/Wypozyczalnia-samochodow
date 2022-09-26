package com.cielicki.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.cielicki.db.Db;
import com.cielicki.db.type.Pracownik;
import com.cielicki.db.type.Uzytkownik;
import com.cielicki.gui.util.Util;

public class LoginScreen extends JFrame {
	JPanel panel;
	JLabel loginLabel;
	JLabel passwordLabel;
	JTextField loginField;
	JPasswordField passwordField;
	JButton zalogujButton;
	JButton zarejestrujButton;
	
	/**
	 * Tworzy okno do Logowania.
	 */
	public LoginScreen () {
		this.setTitle("Wypo¿yczalnia Cielicki");
		this.setSize(new Dimension(1024, 800));
		Util.centerWindow(this);
		try {
			Image background = javax.imageio.ImageIO.read(this.getClass().getClassLoader().getResource("loginBackground.jpg"));
			
			this.setContentPane(new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(background, 0, 0, LoginScreen.this.getWidth(), LoginScreen.this.getHeight(), this);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpringLayout layout = new SpringLayout();
		panel = new JPanel(layout);
		panel.setPreferredSize(this.getSize());
		panel.setOpaque(false);
		
		this.add(panel);
		
		loginLabel = new JLabel("Login");
		loginLabel.setPreferredSize(new Dimension(50, 20));
		passwordLabel = new JLabel("Has³o");
		passwordLabel.setPreferredSize(new Dimension(50, 20));
		
		loginField = new JTextField();
		loginField.setPreferredSize(new Dimension(150, 20));
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(150, 20));
		
		panel.add(loginLabel);
		panel.add(loginField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		
		// Ustawienie loginLabel
		layout.putConstraint(SpringLayout.NORTH, loginLabel, 15, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, loginLabel, 15, SpringLayout.WEST, panel);
		
		// Ustawienie passwordLabel
		layout.putConstraint(SpringLayout.WEST, passwordLabel, 15, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel, 15, SpringLayout.SOUTH, loginLabel);
		
		// Ustawienie loginField
		layout.putConstraint(SpringLayout.WEST, loginField, 15, SpringLayout.EAST, loginLabel);
		layout.putConstraint(SpringLayout.NORTH, loginField, 15, SpringLayout.NORTH, panel);
		
		// Ustawienie passwordField		
		layout.putConstraint(SpringLayout.WEST, passwordField, 15, SpringLayout.EAST, passwordLabel);
		layout.putConstraint(SpringLayout.NORTH, passwordField, 15, SpringLayout.SOUTH, loginField);
		
		zalogujButton = new JButton("Zaloguj");
		zalogujButton.setPreferredSize(new Dimension(80, 20));
		zalogujButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Uzytkownik uzytkownik = Db.INSTANCE.zaloguj(loginField.getText(), new String(passwordField.getPassword()));
				
				if (uzytkownik != null) {
					if (uzytkownik instanceof Pracownik) {
						new OknoGlowne((Pracownik) uzytkownik);
						LoginScreen.this.dispose();
						
					} else {
						new OknoGlowne(uzytkownik);
						LoginScreen.this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(LoginScreen.this, "Niepoprawne dane logowania.\nSpróbuj ponownie lub zarejestruj siê.", "Nieudane logowanie", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		panel.add(zalogujButton);
		
		// Ustawienie zalogujButton
		layout.putConstraint(SpringLayout.NORTH, zalogujButton, 15, SpringLayout.SOUTH, passwordLabel);
		layout.putConstraint(SpringLayout.WEST, zalogujButton, 15, SpringLayout.WEST, panel);
		
		zarejestrujButton = new JButton("Zarejestruj siê");
		zarejestrujButton.setPreferredSize(new Dimension(120, 20));
		zarejestrujButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Rejestracja(LoginScreen.this);
			}
		});
		
		panel.add(zarejestrujButton);
		
		// Ustawienei zarejestrujButton
		layout.putConstraint(SpringLayout.WEST, zarejestrujButton, 15, SpringLayout.EAST, zalogujButton);
		layout.putConstraint(SpringLayout.NORTH, zarejestrujButton, 15, SpringLayout.SOUTH, passwordField);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
	
	/**
	 * G³owna metoda programu.
	 * 
	 * @param args Argumenty programu.
	 */
	public static void main(String[] args) {
		LoginScreen loginScreen = new LoginScreen();
	}
}
