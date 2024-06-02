package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

		JLabel title = new JLabel("All Playstations", 35);
		title.setForeground(Color.BLUE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		gradientPanel.add(title, BorderLayout.NORTH);

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

		JScrollPane panel = new JScrollPane(new JTable(pssData, header, Color.BLACK, color2));
		panel.setBackground(null);
		panel.getViewport().setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		gradientPanel.add(panel, BorderLayout.CENTER);
		frame.add(gradientPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
