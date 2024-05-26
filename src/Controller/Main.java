package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Admin;
import Model.Client;
import Model.Database;
import Model.JButton;
import Model.JLabel;
import Model.JPasswordField;
import Model.JTextField;
import Model.User;

public class Main {

	private static Database database;

	public static void main(String[] args) {
		database = new Database();
		start();
	}

	public static void start() {
		JFrame frame = new JFrame("Login");
		frame.setSize(800, 330); // Sesuaikan ukuran frame untuk memberikan ruang bagi gambar
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("WELCOME TO RENTAL PS GDA", 35);
		title.setForeground(Color.BLUE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		// Panel dengan latar belakang gradasi
		JPanel gradientPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				int width = getWidth();
				int height = getHeight();
				Color color1 = new Color(224, 255, 255);
				Color color2 = new Color(0, 0, 255);
				GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, width, height);
			}
		};
		gradientPanel.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel emailLabel = new JLabel("Email:", 22);
		emailLabel.setForeground(Color.BLACK);
		panel.add(emailLabel);

		JTextField email = new JTextField(22);
		email.setForeground(Color.BLACK);
		email.setBackground(Color.LIGHT_GRAY);
		panel.add(email);

		JLabel passwordLabel = new JLabel("Password:", 22);
		passwordLabel.setForeground(Color.BLACK);
		panel.add(passwordLabel);

		JPasswordField password = new JPasswordField(22);
		password.setForeground(Color.BLACK);
		password.setBackground(Color.LIGHT_GRAY);
		panel.add(password);

		JButton createAcc = new JButton("Create New Account", 22);
		createAcc.setBackground(Color.GREEN);
		createAcc.setForeground(Color.WHITE);
		createAcc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddNewAccount(0).operation(database, frame, null);
				frame.dispose();
			}
		});
		panel.add(createAcc);

		ArrayList<User> users = new ArrayList<>();
		try {
			String select = "SELECT * FROM `users`;";
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				User user;
				int ID = rs.getInt("ID");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String em = rs.getString("Email");
				String phoneNumber = rs.getString("PhoneNumber");
				String pass = rs.getString("Password");
				int type = rs.getInt("Type");

				if (type == 0) {
					user = new Client();
					user.setID(ID);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(em);
					user.setPhoneNumber(phoneNumber);
					user.setPassword(pass);
					users.add(user);
				} else if (type == 1) {
					user = new Admin();
					user.setID(ID);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(em);
					user.setPhoneNumber(phoneNumber);
					user.setPassword(pass);
					users.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JButton login = new JButton("Login", 22);
		login.setBackground(Color.BLUE);
		login.setForeground(Color.WHITE);
		login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (email.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Email cannot be empty");
					return;
				}

				if (password.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Password cannot be empty");
					return;
				}

				boolean loggedIn = false;
				for (User u : users) {
					if (u.getEmail().equals(email.getText()) && u.getPassword().equals(password.getText())) {
						loggedIn = true;
						u.showList(database, frame);
						frame.dispose();
					}
				}
				if (!loggedIn) {
					JOptionPane.showMessageDialog(frame, "Email or password doesn't match");
				}
			}
		});
		panel.add(login);

		// Membuat panel utama untuk menampung panel input dan gambar
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(false);
		mainPanel.add(panel, BorderLayout.CENTER);

		// Memuat gambar dan menambahkannya ke label
		ClassLoader classLoader = Main.class.getClassLoader();
		ImageIcon imageIcon = new ImageIcon(classLoader.getResource("resources/lumba.png"));
		javax.swing.JLabel imageLabel = new javax.swing.JLabel(imageIcon); // Pastikan menggunakan javax.swing.JLabel

		// Menambahkan label gambar ke panel utama di sisi kanan
		mainPanel.add(imageLabel, BorderLayout.EAST);

		// Menambahkan panel utama ke panel gradasi
		gradientPanel.add(mainPanel, BorderLayout.CENTER);

		// Menambahkan panel gradasi ke frame
		frame.add(gradientPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}
}
