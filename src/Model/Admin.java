package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import Controller.AddNewAccount;
import Controller.AddNewps;
import Controller.ChangePassword;
import Controller.Deleteps;
import Controller.EditUserData;
import Controller.ShowAllRents;
import Controller.ShowSpecUserRents;
import Controller.Updateps;
import Controller.Viewpss;

public class Admin extends User {

	private Operation[] operations = new Operation[] {
			new AddNewps(),
			new Viewpss(),
			new Updateps(),
			new Deleteps(),
			new AddNewAccount(1),
			new ShowAllRents(),
			new ShowSpecUserRents(),
			new EditUserData(),
			new ChangePassword()
	};

	private JButton[] btns = new JButton[] {
			new JButton("Add New PS Unit", new ImageIcon("icons/add.png")),
			new JButton("View PS", new ImageIcon("icons/view.png")),
			new JButton("Update PS", new ImageIcon("icons/update.png")),
			new JButton("Delete PS", new ImageIcon("icons/delete.png")),
			new JButton("Add New Admin", new ImageIcon("icons/add_admin.png")),
			new JButton("Show Rents", new ImageIcon("icons/show_rents.png")),
			new JButton("Show User's Rents", new ImageIcon("icons/show_user_rents.png")),
			new JButton("Edit my Data", new ImageIcon("icons/edit.png")),
			new JButton("Change Password", new ImageIcon("icons/change_password.png"))
	};

	public Admin() {
		super();
	}

	@Override
	public void showList(Database database, JFrame f) {
		JFrame frame = new JFrame("Admin Dashboard");
		frame.setSize(1000, 700);
		frame.setLocationRelativeTo(f);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Header
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(new Color(44, 62, 80));
		JLabel title = new JLabel("Welcome " + getFirstName(), JLabel.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Arial", Font.BOLD, 30));
		header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		header.add(title, BorderLayout.CENTER);

		// Side Menu
		JPanel sideMenu = new JPanel(new GridLayout(btns.length, 1, 10, 10));
		sideMenu.setBackground(new Color(52, 73, 94));
		sideMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		for (int i = 0; i < btns.length; i++) {
			final int j = i;
			JButton button = btns[i];
			button.setBackground(new Color(41, 128, 185));
			button.setForeground(Color.WHITE);
			button.setFocusPainted(false);
			button.setFont(new Font("Arial", Font.BOLD, 16));
			button.setPreferredSize(new Dimension(200, 40));
			sideMenu.add(button);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					operations[j].operation(database, frame, Admin.this);
				}
			});
		}

		// Main Panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(236, 240, 241));

		// Adding summary data with cards
		JPanel summaryPanel = new JPanel(new GridBagLayout());
		summaryPanel.setBackground(new Color(236, 240, 241));
		summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;

		JPanel totalPSUnitsCard = createSummaryCard("Total PS Units", String.valueOf(database.getTotalPSUnits()));
		gbc.gridx = 0;
		gbc.gridy = 0;
		summaryPanel.add(totalPSUnitsCard, gbc);

		JPanel totalUsersCard = createSummaryCard("Total Users", String.valueOf(database.getTotalUsers()));
		gbc.gridx = 1;
		gbc.gridy = 0;
		summaryPanel.add(totalUsersCard, gbc);

		JPanel totalRentsCard = createSummaryCard("Total Rents", String.valueOf(database.getTotalRents()));
		gbc.gridx = 0;
		gbc.gridy = 1;
		summaryPanel.add(totalRentsCard, gbc);

		JPanel totalEarningsCard = createSummaryCard("Total Earnings", formatCurrency(database.getTotalEarnings()));
		gbc.gridx = 1;
		gbc.gridy = 1;
		summaryPanel.add(totalEarningsCard, gbc);

		mainPanel.add(summaryPanel, BorderLayout.CENTER);

		// Footer
		JPanel footer = new JPanel();
		footer.setBackground(new Color(44, 62, 80));
		JLabel footerText = new JLabel("Kelompok 3 - Pemrograman Berorientasi Objek © 2024", JLabel.CENTER);
		footerText.setForeground(Color.WHITE);
		footerText.setFont(new Font("Arial", Font.PLAIN, 12));
		footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		footer.add(footerText);

		frame.add(header, BorderLayout.NORTH);
		frame.add(sideMenu, BorderLayout.WEST);
		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(footer, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private String formatCurrency(double amount) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
		return currencyFormatter.format(amount);
	}

	private JPanel createSummaryCard(String title, String value) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setForeground(new Color(44, 62, 80));

		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
		valueLabel.setForeground(new Color(41, 128, 185));
		valueLabel.setHorizontalAlignment(JLabel.CENTER);

		card.add(titleLabel, BorderLayout.NORTH);
		card.add(valueLabel, BorderLayout.CENTER);

		return card;
	}
}
