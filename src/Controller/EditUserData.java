package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import Model.Database;
import Model.JTextField;
import Model.Operation;
import Model.User;

public class EditUserData implements Operation {

	@Override
	public void operation(Database database, JFrame f, User user) {

		JFrame frame = new JFrame("Edit Data");
		frame.setSize(600, 450);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(236, 240, 241));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("Edit Data", JLabel.CENTER);
		title.setForeground(new Color(44, 62, 80));
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(5, 2, 15, 15));
		panel.setBackground(new Color(236, 240, 241));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panel.add(createLabel("First Name:"));

		JTextField firstName = new JTextField(22);
		firstName.setText(user.getFirstName());
		panel.add(firstName);

		panel.add(createLabel("Last Name:"));

		JTextField lastName = new JTextField(22);
		lastName.setText(user.getLastName());
		panel.add(lastName);

		panel.add(createLabel("Email:"));

		JTextField email = new JTextField(22);
		email.setText(user.getEmail());
		panel.add(email);

		panel.add(createLabel("Phone Number:"));

		JTextField phoneNumber = new JTextField(22);
		phoneNumber.setText(user.getPhoneNumber());
		panel.add(phoneNumber);

		JButton cancel = new JButton("Cancel");
		cancel.setBackground(new Color(192, 57, 43));
		cancel.setForeground(Color.WHITE);
		cancel.setFont(new Font("Arial", Font.BOLD, 16));
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(cancel);

		JButton confirm = new JButton("Confirm");
		confirm.setBackground(new Color(39, 174, 96));
		confirm.setForeground(Color.WHITE);
		confirm.setFont(new Font("Arial", Font.BOLD, 16));
		confirm.setFocusPainted(false);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				if (firstName.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "First Name cannot be empty");
					return;
				}
				if (lastName.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Last Name cannot be empty");
					return;
				}
				if (email.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Email cannot be empty");
					return;
				}
				if (phoneNumber.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Phone Number cannot be empty");
					return;
				}

				String update = "UPDATE `users` SET `FirstName`='" + firstName.getText() + "',"
						+ "`LastName`='" + lastName.getText() + "',`Email`='" + email.getText() + "',"
						+ "`PhoneNumber`='" + phoneNumber.getText() + "' "
						+ "WHERE `ID` = '" + user.getID() + "';";

				try {
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, "Data updated successfully");
					user.setFirstName(firstName.getText());
					user.setLastName(lastName.getText());
					user.setEmail(email.getText());
					user.setPhoneNumber(phoneNumber.getText());
					frame.dispose();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(frame, e.getMessage());
				}
			}
		});
		panel.add(confirm);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);

	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(new Color(44, 62, 80));
		label.setFont(new Font("Arial", Font.BOLD, 18));
		return label;
	}
}
