/*
Copyright (C) 2013  Marcel Hollerbach, Daniel Haß

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerSettings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfIP;
	private JTextField tfPort;
	private ChatFrame ctFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 */
	public ServerSettings(ChatFrame ctframe) {
		setTitle("Server Settings");
		setLocationRelativeTo(ctframe);
		ctFrame = ctframe;
		
		try {
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSize(269, 191);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server Settings:");
		lblNewLabel.setBounds(12, 12, 124, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("IP-Adress:");
		lblNewLabel_1.setBounds(12, 39, 93, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Port:");
		lblNewLabel_2.setBounds(12, 83, 70, 15);
		contentPanel.add(lblNewLabel_2);
		
		tfIP = new JTextField();
		tfIP.setBounds(105, 37, 114, 26);
		contentPanel.add(tfIP);
		tfIP.setColumns(10);
		
		tfPort = new JTextField();
		tfPort.setBounds(105, 83, 114, 26);
		contentPanel.add(tfPort);
		tfPort.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ctFrame.notifyServerSettings(tfIP.getText(), Integer.valueOf(tfPort.getText()));
						ServerSettings.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ServerSettings.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	public String[] getServerSettings(){
		
		String[] values = new String[2];
		
		values[0] = tfIP.getText();
		values[1] = tfPort.getText();
		
		return  values;
	}
}
