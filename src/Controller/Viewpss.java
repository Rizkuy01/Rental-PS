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
import Model.Database;
import Model.JLabel;
import Model.JTable;
import Model.Operation;
import Model.User;

public class Viewpss implements Operation {

	@Override
	public void operation(Database database, JFrame f, User user) {

		JFrame frame = new JFrame("All Playstations");
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(250, 206, 27));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("All Playstations", 35);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		String[] header = new String[] {
				"ID", "Brand", "Model", "Color", "Year", "Price", "Available"
		};

		String select = "SELECT * FROM `pss`;";
		ArrayList<ps> pss = new ArrayList<>();
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				ps ps = new ps();
				ps.setID(rs.getInt("ID"));
				ps.setBrand(rs.getString("Brand"));
				ps.setModel(rs.getString("Model"));
				ps.setColor(rs.getString("Color"));
				ps.setYear(rs.getInt("Year"));
				ps.setPrice(rs.getDouble("Price"));
				int available = rs.getInt("Available");
				if (available < 2) {
					ps.setAvailable(available);
					pss.add(ps);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}

		String[][] pssData = new String[pss.size()][7];
		for (int j = 0; j < pss.size(); j++) {
			ps c = pss.get(j);
			if (c.isAvailable() < 2) {
				pssData[j][0] = String.valueOf(c.getID());
				pssData[j][1] = c.getBrand();
				pssData[j][2] = c.getModel();
				pssData[j][3] = c.getColor();
				pssData[j][4] = String.valueOf(c.getYear());
				pssData[j][5] = "Rp" + String.valueOf(c.getPrice());
				if (c.isAvailable() == 0) {
					pssData[j][6] = "Available";
				} else {
					pssData[j][6] = "Not Available";
				}
			}
		}

		Color color2 = new Color(252, 242, 202);

		JScrollPane panel = new JScrollPane(new JTable(pssData, header, Color.black, color2));
		panel.setBackground(null);
		panel.getViewport().setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);

	}

}
