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
import Model.JPasswordField;
import Model.Operation;
import Model.User;

public class ChangePassword implements Operation {

	@Override
	public void operation(Database database, JFrame f, User user) {

		JFrame frame = new JFrame("Change Password");
		frame.setSize(600, 380);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(236, 240, 241));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("Change Password", JLabel.CENTER);
		title.setBackground(new Color(44, 62, 80));
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15));
		panel.setBackground(new Color(236, 240, 241));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panel.add(createLabel("Old Password:"));

		JPasswordField oldPassword = new JPasswordField(22);
		panel.add(oldPassword);

		panel.add(createLabel("New Password:"));

		JPasswordField newPassword = new JPasswordField(22);
		panel.add(newPassword);

		panel.add(createLabel("Confirm Password:"));

		JPasswordField confirmPassword = new JPasswordField(22);
		panel.add(confirmPassword);

		JButton cancel = new JButton("Cancel");
		cancel.setBackground(new Color(192, 57, 43));
		cancel.setForeground(Color.WHITE);
		cancel.setFont(new Font("Arial", Font.BOLD, 16));
		cancel.setFocusPainted(false);
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
		confirm.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent ev) {

				if (oldPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Old Password cannot be empty");
					return;
				}
				if (newPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "New Password cannot be empty");
					return;
				}
				if (confirmPassword.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Confirm Password cannot be empty");
					return;
				}
				if (!oldPassword.getText().equals(user.getPassword())) {
					JOptionPane.showMessageDialog(frame, "Incorrect Password");
					return;
				}
				if (!newPassword.getText().equals(confirmPassword.getText())) {
					JOptionPane.showMessageDialog(frame, "Password doesn't match");
					return;
				}

				try {
					String update = "UPDATE `users` SET "
							+ "`Password`='" + newPassword.getText() + "' WHERE `ID` = '" + user.getID() + "';";
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, "Password changed successfully");
					user.setPassword(newPassword.getText());
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
