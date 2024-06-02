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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Model.ps;
import Model.Database;
import Model.JButton;
import Model.JLabel;
import Model.JTextField;
import Model.JComboBox;
import Model.Operation;
import Model.User;

public class Deleteps implements Operation {

	private JTextField brand, model, color, year, price;
	private Database database;
	private JFrame frame;

	@Override
	public void operation(Database database, JFrame f, User user) {

		this.database = database;

		frame = new JFrame("Delete PS Unit");
		frame.setSize(600, 600);
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

		JLabel title = new JLabel("Delete PS Unit", 35);
		title.setForeground(Color.BLUE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		gradientPanel.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(7, 2, 15, 15));
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

		JComboBox id = new JComboBox(ids, 22);
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

		JButton cancel = new JButton("Cancel", 22);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(cancel);

		JButton confirm = new JButton("Confirm", 22);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id.getSelectedItem().toString().equals(" ")) {
					JOptionPane.showMessageDialog(frame, "ID cannot be empty");
					return;
				}

				try {
					String update = "UPDATE `pss` SET `Available`='2' WHERE `ID` = '" + id.getSelectedItem().toString()
							+ "';";
					database.getStatement().execute(update);
					JOptionPane.showMessageDialog(frame, "PS deleted successfully");
					frame.dispose();
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(frame, e3.getMessage());
				}
			}
		});
		panel.add(confirm);

		gradientPanel.add(panel, BorderLayout.CENTER);
		frame.add(gradientPanel, BorderLayout.CENTER);
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
				price.setText(String.valueOf(rs1.getDouble("Price")));
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(frame, e1.getMessage());
				frame.dispose();
			}
		}
	}
}
