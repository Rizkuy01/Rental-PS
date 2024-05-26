package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.ps;
import Model.Database;
import Model.JButton;
import Model.JLabel;
import Model.JTextField;
import Model.Operation;
import Model.Rent;
import Model.User;

public class Rentps implements Operation {

	private JTextField brand, model, color, year, price;
	private Database database;
	private JFrame frame;

	@Override
	public void operation(Database database, JFrame f, User user) {

		this.database = database;

		frame = new JFrame("Booking PS");
		frame.setSize(600, 650);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(250, 206, 27));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("Booking PS", 35);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(8, 2, 15, 15));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		panel.add(new JLabel("ID:", 22));

		String[] ids = new String[] { " " };
		ArrayList<Integer> idsArray = new ArrayList<>();
		try {
			ResultSet rs0 = database.getStatement()
					.executeQuery("SELECT `ID`, `Available` FROM `pss`;");
			while (rs0.next()) {
				if (rs0.getInt("Available") < 2)
					idsArray.add(rs0.getInt("ID"));
			}
		} catch (Exception e0) {
			JOptionPane.showMessageDialog(frame, e0.getMessage());
			frame.dispose();
		}

		ids = new String[idsArray.size() + 1];
		ids[0] = " ";
		for (int i = 1; i <= idsArray.size(); i++) {
			ids[i] = String.valueOf(idsArray.get(i - 1));
		}

		Model.JComboBox id = new Model.JComboBox(ids, 22);
		id.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateData(id.getSelectedItem().toString());
			}
		});
		panel.add(id);

		panel.add(new JLabel("Brand:", 22));

		brand = new JTextField(22);
		brand.setEditable(false);
		panel.add(brand);

		panel.add(new JLabel("Model:", 22));

		model = new JTextField(22);
		model.setEditable(false);
		panel.add(model);

		panel.add(new JLabel("Color:", 22));

		color = new JTextField(22);
		color.setEditable(false);
		panel.add(color);

		panel.add(new JLabel("Year:", 22));

		year = new JTextField(22);
		year.setEditable(false);
		panel.add(year);

		panel.add(new JLabel("Price per Hour:", 22));

		price = new JTextField(22);
		price.setEditable(false);
		panel.add(price);

		panel.add(new JLabel("Hours:", 22));

		JTextField hours = new JTextField(22);
		panel.add(hours);

		JButton showpss = new JButton("Cek PS", 22);
		showpss.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Viewpss().operation(database, frame, user);
			}
		});
		panel.add(showpss);

		JButton confirm = new JButton("Confirm", 22);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id.getSelectedItem().toString().equals(" ")) {
					JOptionPane.showMessageDialog(frame, "ID cannot be empty");
					return;
				}
				if (hours.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Hours cannot be empty");
					return;
				}
				int hoursInt;
				try {
					hoursInt = Integer.parseInt(hours.getText());
				} catch (Exception e5) {
					JOptionPane.showMessageDialog(frame, "Hours must be int");
					return;
				}
				try {
					ResultSet rs0 = database.getStatement()
							.executeQuery(
									"SELECT * FROM `pss` WHERE `ID` = '" + id.getSelectedItem().toString() + "';");
					rs0.next();
					ps ps = new ps();
					ps.setID(rs0.getInt("ID"));
					ps.setBrand(rs0.getString("Brand"));
					ps.setModel(rs0.getString("Model"));
					ps.setColor(rs0.getString("Color"));
					ps.setYear(rs0.getInt("Year"));
					ps.setPrice(rs0.getDouble("Price"));
					ps.setAvailable(rs0.getInt("Available"));

					if (ps.isAvailable() != 0) {
						JOptionPane.showMessageDialog(frame, "PS isn't available");
						return;
					}

					ResultSet rs1 = database.getStatement()
							.executeQuery("SELECT COUNT(*) FROM `rents`;");
					rs1.next();
					int ID = rs1.getInt("COUNT(*)");

					double total = ps.getPrice() * hoursInt;

					Rent rent = new Rent();

					String insert = "INSERT INTO `rents`(`ID`, `User`, `ps`, `DateTime`, `Hours`,"
							+ " `Total`, `Status`) VALUES ('" + ID + "','" + user.getID() + "',"
							+ "'" + ps.getID() + "','" + rent.getDateTime() + "','" + hoursInt + "',"
							+ "'" + total + "','0');";

					database.getStatement().execute(insert);
					JOptionPane.showMessageDialog(frame, "Selamat bermain"
							+ "\nTotal bayar = " + "Rp" + total);
					frame.dispose();
				} catch (SQLException exception) {
					JOptionPane.showMessageDialog(frame, exception.getMessage());
				}
			}
		});
		panel.add(confirm);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.requestFocus();
	}

	private void updateData(String ID) {
		if (ID.equals(" ")) {
			brand.setText("");
			model.setText("");
			color.setText("");
			year.setText("");
			price.setText("");
		} else {
			try {
				ResultSet rs1 = database.getStatement()
						.executeQuery("SELECT * FROM `pss` WHERE `ID` = '" + ID + "';");
				rs1.next();
				ps ps = new ps();
				ps.setID(rs1.getInt("ID"));
				brand.setText(rs1.getString("Brand"));
				model.setText(rs1.getString("Model"));
				color.setText(rs1.getString("Color"));
				year.setText(String.valueOf(rs1.getInt("Year")));
				price.setText("Rp" + String.valueOf(rs1.getDouble("Price")));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frame, e1.getMessage());
				frame.dispose();
			}
		}
	}

}
