package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.ChangePassword;
import Controller.EditUserData;
import Controller.Rentps;
import Controller.Returnps;
import Controller.ShowUserRents;
import Controller.Viewpss;

public class Client extends User {

	private Operation[] operations = new Operation[] {
			new Viewpss(),
			new Rentps(),
			new Returnps(),
			new ShowUserRents(-9999),
			new EditUserData(),
			new ChangePassword() };

	private JButton[] btns = new JButton[] {
			new JButton("View Schedule"),
			new JButton("Start Play"),
			new JButton("Return"),
			new JButton("Show My Schedule"),
			new JButton("Edit My Data"),
			new JButton("Change Password")
	};

	public Client() {
		super();
	}

	@Override
	public void showList(Database database, JFrame f) {

		JFrame frame = new JFrame("Client Panel");
		frame.setSize(400, btns.length * 90);
		frame.setLocationRelativeTo(f);
		frame.getContentPane().setBackground(new Color(236, 240, 241));
		frame.setLayout(new BorderLayout());

		JLabel title = new JLabel("Welcome " + getFirstName(), JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(30f));
		title.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		frame.add(title, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(btns.length, 1, 15, 15));
		panel.setBackground(null);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		for (int i = 0; i < btns.length; i++) {
			final int j = i;
			JButton button = btns[i];
			panel.add(button);
			button.setBackground(new Color(41, 128, 185));
			button.setForeground(Color.WHITE);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					operations[j].operation(database, frame, Client.this);
				}
			});
		}

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
