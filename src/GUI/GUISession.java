package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Client.UserHandling.ChatSession;
import ClientSide.DaCryClient;

public class GUISession extends JPanel {

	private ChatSession chSession;
	private JTextArea taInput;
	private JScrollPane scrollInput;
	private JTextArea taOutput;
	private JScrollPane scrollOutput;
	private JButton btnOK;
	private JTabbedPane tabbedPane;

	private BorderLayout borderLayout;
	private JPanel panInput;

	private DaCryClient daclient;
	private String nick;
	private boolean notifyed = false;

	public GUISession(ChatSession session, DaCryClient client, String ownNick,
			JTabbedPane tabPane) {

		borderLayout = new BorderLayout();
		setLayout(borderLayout);

		nick = ownNick;
		tabbedPane = tabPane;
		daclient = client;
		panInput = new JPanel();
		panInput.setLayout(new BorderLayout());

		MouseListener mouseListener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (notifyed) {
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),
							ChatFrame.getPartnerName(chSession, nick));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};

		// INPUT ======================================================
		taInput = new JTextArea() {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		taInput.setLineWrap(true);
		taInput.setWrapStyleWord(true);
		taInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					taInput.setText("");
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOK.doClick();
					taInput.setText("");
				}
			}
		});

		scrollInput = new JScrollPane(taInput);
		scrollInput
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollInput.setBounds(10, 380, 380, 80);
		scrollInput.setPreferredSize(new Dimension(380, 80));

		// OUTPUT ======================================================

		taOutput = new JTextArea() {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		taOutput.setLineWrap(true);
		taOutput.setWrapStyleWord(true);
		taOutput.setEditable(false);
		scrollOutput = new JScrollPane(taOutput);
		scrollOutput
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollOutput.setBounds(10, 10, 480, 350);

		// SESSION ======================================================

		chSession = session;

		btnOK = new JButton("Send") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
//		btnOK.setBounds(407, 380, 80, 80);
//		btnOK.setPreferredSize(new Dimension(80, 80));
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Debugging this will invite Teeest0 to a chat
				// daclient.invToChat("Teeest0");
				clientOutput(taInput.getText());
				taInput.setText("");
			}
		});

		taInput.addMouseListener(mouseListener);
		taOutput.addMouseListener(mouseListener);
		btnOK.addMouseListener(mouseListener);

		panInput.add(scrollInput, BorderLayout.CENTER);
		panInput.add(btnOK, BorderLayout.LINE_END);

		add(scrollOutput, BorderLayout.CENTER);
		add(panInput, BorderLayout.PAGE_END);
		// add(scrollInput, BorderLayout.PAGE_END);

		// add(scrollInput);
		// add(scrollOutput);
		// add(btnOK);

	}

	public void notifyNewMessage(int tabIndex, String msg) {
		notifyed = true;
		tabbedPane.setTitleAt(tabIndex,
				ChatFrame.getPartnerName(chSession, nick) + "(N)");
		partnerOutput(msg);
	}

	/**
	 * Ist fuer die Ausgaben des Users zustaendig
	 * 
	 * @param pOut
	 *            - Eingabe des Users, die im Chatverlauf auftauchen soll
	 */
	public void clientOutput(String pOut) {
		if (pOut != null && !pOut.equals("")) {
			if (chSession != null) {
				daclient.sentMessage(chSession, pOut);
			}
			taOutput.append("You: " + pOut + "\n");
			taOutput.setCaretPosition(taOutput.getDocument().getLength());
		}
	}

	/**
	 * Ist fuer die Ausgaben des Partners zustaendig
	 * 
	 * @param pOut
	 *            - Ausgabe des Systems
	 */
	public void partnerOutput(String pOut) {
		if (pOut != null && !pOut.equals("")) {
			taOutput.append(ChatFrame.getPartnerName(chSession, nick) + ": "
					+ pOut + "\n");
			taOutput.setCaretPosition(taOutput.getDocument().getLength());
		}
	}

	/**
	 * Ist fuer die Ausgaben des Systems zustaendig
	 * 
	 * @param pOut
	 *            - Ausgabe des Systems
	 */
	public void serverOutput(String pOut) {
		taOutput.append("<SERVER>: " + pOut + "\n");
		taOutput.setCaretPosition(taOutput.getDocument().getLength());
	}

	public ChatSession getChatSession() {
		return chSession;
	}

	public JTextArea getTaInput() {
		return taInput;
	}

	public JScrollPane getScrollInput() {
		return scrollInput;
	}

	public JTextArea getTaOutput() {
		return taOutput;
	}

	public JScrollPane getScrollOutput() {
		return scrollOutput;
	}

}
