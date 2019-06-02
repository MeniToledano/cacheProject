package com.hit.view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;

public class CacheUnitView extends java.lang.Object {

	private final PropertyChangeSupport pcs;
	private JFrame frame;
	private CacheUnitPanel p;

	/**
	 * @wbp.parser.entryPoint
	 */
	public CacheUnitView() {
		this.frame = new JFrame("CacheUnitUI");
		frame.getContentPane().setBackground(new Color(245, 222, 179));
		frame.getContentPane().setForeground(new Color(128, 128, 0));
		this.p = new CacheUnitPanel(this.frame);
		pcs = new PropertyChangeSupport(this);
		
	}

	public void start() {
		this.frame.setVisible(true);

	}

	public <T> void updateUIData(T t) {

		String text[] = t.toString().split("\\@");
		String output = "";
		String secondOutput = "";
		boolean writeSecondTextArea = false;

		for (String line : text) {

			if (writeSecondTextArea == true)
				secondOutput += line + "\n";
			else
				output += line + "\n";

			if (line.equals("Succeeded !"))
				writeSecondTextArea = true;

		}
		p.textArea.setText(secondOutput);
		p.textArea_1.setText(output);
		
	}

	public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
		this.pcs.removePropertyChangeListener(pcl);
	}

	public class CacheUnitPanel extends JPanel implements ActionListener {

		/**
		 * 
		 */

		private static final long serialVersionUID = 1L;
		private JFrame frame;
		private JTextArea textArea;
		private JLabel lblOutput;
		private JButton btnNewButton;
		private JButton button;

		public JTextArea textArea_1;

		public CacheUnitPanel(JFrame frame) {
			this.frame = frame;
			this.lblOutput = new JLabel("Output:");
			this.textArea = new JTextArea();
			this.textArea_1 = new JTextArea();
			this.btnNewButton = new JButton("Load a Request");
			button = new JButton("Show Statistics");
			this.initialize();
		}

		private void initialize() {


			
			JLabel lblOutput = new JLabel("Request status/statistics:");
			lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 20));
			

			JButton btnNewButton = new JButton("Load a Request");
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
			btnNewButton.setSize(337, 110);
			JButton button = new JButton("Show Statistics");
			button.setFont(new Font("Tahoma", Font.PLAIN, 21));
			button.setSize(330, 113);

			frame.setVisible(true);
			frame.setVisible(true);
			frame.setBounds(100, 100, 826, 487);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			Image img = new ImageIcon(this.getClass().getResource("/Load.png")).getImage();
			btnNewButton.setIcon(new ImageIcon(img));

			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					JFileChooser fileChoose = new JFileChooser();
					String json = new String();

					if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

						try {
							File file = fileChoose.getSelectedFile();
							Scanner input = new Scanner(file);
							json = input.nextLine();

							while (input.hasNextLine()) {
								json = json + (input.nextLine());
							}

							input.close();

						} catch (FileNotFoundException e1) {
							System.out.println("failed to read file");
							e1.printStackTrace();
						}

					} else {
						textArea.setText("No File was selected..");

					}

					pcs.firePropertyChange(null, null, json);
				}

			});

			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String json = new String();
					File file = new File("src/main/resources/STATISTICS.json");
					Scanner input;
					try {
						input = new Scanner(file);
						json = input.nextLine();

						while (input.hasNextLine()) {
							json += input.nextLine();
						}

						input.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					pcs.firePropertyChange(null, null, json);

				}

			});

			Image img2 = new ImageIcon(this.getClass().getResource("/Statistics.png")).getImage();
			button.setIcon(new ImageIcon(img2));

			JLabel lblData = new JLabel("Get data:");
			lblData.setFont(new Font("Tahoma", Font.PLAIN, 20));

			JScrollPane scrollPane = new JScrollPane();

			JScrollPane scrollPane_1 = new JScrollPane();

			GroupLayout groupLayout = new GroupLayout(this.frame.getContentPane());
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
							.addComponent(lblOutput, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
							.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
							.addComponent(button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
							.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(button, 0, 0, Short.MAX_VALUE)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 133, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutput, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
							.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
						.addGap(27)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
						.addGap(37))
			);
		
			
			this. textArea = new JTextArea();
			scrollPane_1.setViewportView(this.textArea);
			
			this.textArea_1 = new JTextArea();
			textArea_1.setFont(new Font("SansSerif", Font.ITALIC, 16));
			scrollPane.setViewportView(this.textArea_1);
			frame.getContentPane().setLayout(groupLayout);
			

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
