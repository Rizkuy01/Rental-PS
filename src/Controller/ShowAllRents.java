package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import Model.ps;
import Model.Client;
import Model.Database;
import Model.JLabel;
import Model.JTable;
import Model.Operation;
import Model.Rent;
import Model.User;

public class ShowAllRents implements Operation {

	@Override
	public void operation(Database database, JFrame f, User user) {

		JFrame frame = new JFrame("Rents");
		frame.setSize(1200, 600);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(250, 206, 27));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("Rents", 35);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		String[] header = new String[] {
				"ID", "Name", "Email", "Tel", "ps ID", "ps", "Date Time",
				"Hours", "Total", "Status"
		};

		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<Integer> psIDs = new ArrayList<>();
		ArrayList<Integer> userIDs = new ArrayList<>();
		try {
			String select = "SELECT * FROM `rents`;";
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				Rent rent = new Rent();
				rent.setID(rs.getInt("ID"));
				userIDs.add(rs.getInt("User"));
				psIDs.add(rs.getInt("PS"));
				rent.setDateTime(rs.getString("DateTime"));
				rent.setHours(rs.getInt("Hours"));
				rent.setTotal(rs.getDouble("Total"));
				rent.setStatus(rs.getInt("Status"));
				rents.add(rent);
			}

			for (int j = 0; j < rents.size(); j++) {
				Rent r = rents.get(j);

				String selectUser = "SELECT * FROM `users` WHERE `ID` = '" + userIDs.get(j) + "';";
				ResultSet rs2 = database.getStatement().executeQuery(selectUser);
				rs2.next();
				User u = new Client();
				u.setID(rs2.getInt("ID"));
				u.setFirstName(rs2.getString("FirstName"));
				u.setLastName(rs2.getString("LastName"));
				u.setEmail(rs2.getString("Email"));
				u.setPhoneNumber(rs2.getString("PhoneNumber"));
				u.setPassword(rs2.getString("Password"));
				r.setUser(u);

				ResultSet rs3 = database.getStatement()
						.executeQuery("SELECT * FROM `pss` WHERE `ID` = '" + psIDs.get(j) + "';");
				rs3.next();
				ps ps = new ps();
				ps.setID(rs3.getInt("ID"));
				ps.setBrand(rs3.getString("Brand"));
				ps.setModel(rs3.getString("Model"));
				ps.setColor(rs3.getString("Color"));
				ps.setYear(rs3.getInt("Year"));
				ps.setPrice(rs3.getDouble("Price"));
				ps.setAvailable(rs3.getInt("Available"));
				r.setps(ps);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
			frame.dispose();
		}

		String[][] rentsData = new String[rents.size()][10];
		for (int j = 0; j < rents.size(); j++) {
			Rent r = rents.get(j);
			rentsData[j][0] = String.valueOf(r.getID());
			rentsData[j][1] = r.getUser().getFirstName() + " " + r.getUser().getLastName();
			rentsData[j][2] = r.getUser().getEmail();
			rentsData[j][3] = r.getUser().getPhoneNumber();
			rentsData[j][4] = String.valueOf(r.getps().getID());
			rentsData[j][5] = r.getps().getBrand() + " " + r.getps().getModel() + " " + r.getps().getColor();
			rentsData[j][6] = r.getDateTime();
			rentsData[j][7] = String.valueOf(r.getHours());
			rentsData[j][8] = "Rp" + String.valueOf(r.getTotal());
			rentsData[j][9] = r.getStatusToString();
		}

		Color color2 = new Color(252, 242, 202);

		JScrollPane panel = new JScrollPane(new JTable(rentsData, header, Color.black, color2));
		panel.setBackground(null);
		panel.getViewport().setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);

	}

}
