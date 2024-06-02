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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.Database;
import Model.JButton;
import Model.JLabel;
import Model.JTextField;
import Model.Operation;
import Model.User;

public class AddNewps implements Operation {

	@Override
	public void operation(Database database, JFrame f, User user) {

		JFrame frame = new JFrame("Add New PS Unit");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(f);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		JLabel title = new JLabel("Add New PS Unit", 35);
		title.setForeground(Color.BLUE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		gradientPanel.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel brandLabel = new JLabel("Brand:", 22);
		brandLabel.setForeground(Color.BLACK);
		panel.add(brandLabel);

		JTextField brand = new JTextField(22);
		brand.setForeground(Color.BLACK);
		brand.setBackground(Color.LIGHT_GRAY);
		panel.add(brand);

		JLabel modelLabel = new JLabel("Model:", 22);
		modelLabel.setForeground(Color.BLACK);
		panel.add(modelLabel);

		JTextField model = new JTextField(22);
		model.setForeground(Color.BLACK);
		model.setBackground(Color.LIGHT_GRAY);
		panel.add(model);

		JLabel colorLabel = new JLabel("Color:", 22);
		colorLabel.setForeground(Color.BLACK);
		panel.add(colorLabel);

		JTextField color = new JTextField(22);
		color.setForeground(Color.BLACK);
		color.setBackground(Color.LIGHT_GRAY);
		panel.add(color);

		JLabel yearLabel = new JLabel("Year:", 22);
		yearLabel.setForeground(Color.BLACK);
		panel.add(yearLabel);

		JTextField year = new JTextField(22);
		year.setForeground(Color.BLACK);
		year.setBackground(Color.LIGHT_GRAY);
		panel.add(year);

		JLabel priceLabel = new JLabel("Price per Hour:", 22);
		priceLabel.setForeground(Color.BLACK);
		panel.add(priceLabel);

		JTextField price = new JTextField(22);
		price.setForeground(Color.BLACK);
		price.setBackground(Color.LIGHT_GRAY);
		panel.add(price);

		JButton cancel = new JButton("Cancel", 22);
		cancel.setBackground(Color.RED);
		cancel.setForeground(Color.WHITE);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(cancel);

		JButton confirm = new JButton("Confirm", 22);
		confirm.setBackground(Color.GREEN);
		confirm.setForeground(Color.WHITE);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (brand.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Brand cannot be empty");
					return;
				}
				if (model.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Model cannot be empty");
					return;
				}
				if (color.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Color cannot be empty");
					return;
				}
				if (year.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Year cannot be empty");
					return;
				}
				if (price.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Price cannot be empty");
					return;
				}
				int yearInt;
				double priceDoub;
				try {
					yearInt = Integer.parseInt(year.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Year must be int");
					return;
				}
				try {
					priceDoub = Double.parseDouble(price.getText());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "Price must be double");
					return;
				}

				int available = 0;

				try {
					ResultSet rs = database.getStatement().executeQuery("SELECT COUNT(*) FROM `pss`;");
					rs.next();
					int ID = rs.getInt("COUNT(*)");

					String insert = "INSERT INTO `pss`(`ID`, `Brand`, `Model`, `Color`,"
							+ " `Year`, `Price`, `Available`) VALUES ('" + ID + "','" + brand.getText() + "',"
							+ "'" + model.getText() + "','" + color.getText() + "','" + yearInt + "','" + priceDoub
							+ "',"
							+ "'" + available + "');";
					database.getStatement().execute(insert);
					JOptionPane.showMessageDialog(frame, "PS unit added successfully");
					frame.dispose();

				} catch (SQLException er) {
					JOptionPane.showMessageDialog(frame, er.getMessage());
				}
			}
		});
		panel.add(confirm);

		gradientPanel.add(panel, BorderLayout.CENTER);
		frame.add(gradientPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}
}
