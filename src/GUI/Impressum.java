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
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class Impressum extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public Impressum(JFrame parentComp) {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setTitle("Impressum");
		setLocationRelativeTo(parentComp);
		setSize(573, 329);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel lblNewLabel = new JLabel("DaMa-Crypt"){
				public void paint(java.awt.Graphics g) {
					Graphics2D graphics2d = (Graphics2D) g;
					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					super.paintComponent(g);
					super.paint(g);
				};
			};
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
			contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		}
		{
			JLabel lblJavaBasedEncrypted = new JLabel("Java based encrypted chat system"){
				public void paint(java.awt.Graphics g) {
					Graphics2D graphics2d = (Graphics2D) g;
					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					super.paintComponent(g);
					super.paint(g);
				};
			};
			lblJavaBasedEncrypted.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblJavaBasedEncrypted, BorderLayout.SOUTH);
		}
		{
			JTextPane txtpnThisIsA = new JTextPane(){
				public void paint(java.awt.Graphics g) {
					Graphics2D graphics2d = (Graphics2D) g;
					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					super.paintComponent(g);
					super.paint(g);
				};
			};
			txtpnThisIsA.setEditable(false);
			txtpnThisIsA.setText("\n\n This is a OpenSource-Project developed and released by the DaMa-Team. \n\n Developers: Marcel Hollerbach(GER), Daniel Haß(GER) \n \n You can find us on GitHub <https://github.com/organizations/DaMa-Team> \n\n Please remember, we are still in a very developing state of this Project. \n\n Version: BETA V0.1 ");
			contentPanel.add(txtpnThisIsA, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Impressum.this.dispose();
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
						Impressum.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
