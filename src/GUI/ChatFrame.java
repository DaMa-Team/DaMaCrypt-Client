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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

import Client.UserHandling.ChatSession;
import Client.UserHandling.User;
import ClientSide.DaCryClient;

/**
 * Simple representation of the Chat Window
 * 
 * @author daniel
 * 
 */
public class ChatFrame extends JFrame implements ClientNotify {

	// Connection Stuff
	private String serverIP = null;
	private int serverPORT = -1;

	private String settingsPath;

	private JTabbedPane tabbedpane;

	private DaCryClient daclient;

	private String nick = "<Nick>";

	private BorderLayout borderLayout;

	// Menu Stuff
	private JMenuBar menubar;
	private JMenu mt_settings;
	private JMenuItem mt_server;
	private JMenuItem mt_nick;
	private JMenu mt_help;
	private JMenuItem mt_info;
	private JMenu mt_menu;
	private JMenuItem mt_close;
	private JMenuItem mt_openChat;

	// Table Stuff
	private JScrollPane tableScroll;
	private JTable tblOnlineUsers;
	private DefaultTableModel tblModel;
	private final String[] tblColumnNames = { "Online Users" };
	private String[][] tblData = { {} };

	// GUI Session
	GUISession curGuiSession = null;
	ArrayList<GUISession> guiSessions = new ArrayList<GUISession>();

	public ChatFrame() {

		// Windows
		settingsPath = System.getenv("APPDATA");
		// Unix
		if (settingsPath == null) {
			settingsPath = System.getProperty("user.home");
		}

		setLayout(null);
		setSize(700, 575);
		setTitle("DaMaCrypt - Swing Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		borderLayout.setHgap(10);
		borderLayout.setVgap(10);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

		}

		tabbedpane = new JTabbedPane() {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
//		tabbedpane.setPreferredSize(new Dimension(500, 500));
		tabbedpane
				.add("Welcome",
						new JLabel(
								"<html><body align=\"center\"><p style=\"padding-left:100px;\">Welcome to the DaMa-Crypt Client. <br> Click on the right side to start a new chat.</body></html>"));

		final WindowListener winListener = new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					daclient.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		};

		addWindowListener(winListener);

		// MenuStuff

		menubar = new JMenuBar() {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);

			};
		};
		mt_settings = new JMenu("Settings") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		mt_menu = new JMenu("Menu") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		mt_help = new JMenu("Help") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		mt_info = new JMenuItem("Info") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		mt_server = new JMenuItem("Server") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		// mt_nick = new JMenuItem("Nick");
		mt_close = new JMenuItem("Close") {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		// mt_openChat = new JMenuItem("[Debug] OpenChat");

		menubar.add(mt_menu);
		menubar.add(mt_settings);
		menubar.add(mt_help);
		mt_help.add(mt_info);
		mt_info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Impressum impressum = new Impressum(ChatFrame.this);
			}
		});
		;
		mt_menu.add(mt_close);
		mt_close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				winListener.windowClosing(null);
			}
		});
		// mt_menu.add(mt_openChat);
		// mt_openChat.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// notifyChatConnectionEstablished(new ChatSession(12, new
		// BigInteger("15"), new BigInteger("13"), new BigInteger("14"), new
		// BigInteger("16"), new User("Test", 54), new User("Test", 78)));
		// }
		// });
		// mt_settings.add(mt_nick);
		// mt_nick.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// requestNick();
		// }
		// });
		mt_server.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ServerSettings ss = new ServerSettings(ChatFrame.this);

			}
		});
		mt_settings.add(mt_server);
		this.setJMenuBar(menubar);

		// Table Stuff
		tblModel = new DefaultTableModel(tblData, tblColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblOnlineUsers = new JTable(tblModel) {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};
		tblOnlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableScroll = new JScrollPane(tblOnlineUsers) {
			public void paint(java.awt.Graphics g) {
				Graphics2D graphics2d = (Graphics2D) g;
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paintComponent(g);
				super.paint(g);
			};
		};

		tableScroll.setPreferredSize(new Dimension(200, 450));

		tblOnlineUsers.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					openPartner(tblOnlineUsers.rowAtPoint(e.getPoint()));
				}
			}
		});

		add(tableScroll, BorderLayout.EAST);
		add(tabbedpane, BorderLayout.CENTER);

		setVisible(true);

		if (readSettings()) {

			requestNick();

			startClient();
		} else {

			ServerSettings ss = new ServerSettings(ChatFrame.this);

		}

	}

	private void openPartner(int clickedRow) {
		String tmpPartnerNick = (String) tblModel.getValueAt(clickedRow, 0);
		tmpPartnerNick = tmpPartnerNick.substring(0,
				tmpPartnerNick.indexOf('<') - 1);
		System.out.println(tmpPartnerNick);
		// String tmpPartner = (String) tblModel.getValueAt(clickedRow, 0);
		// tmpPartner = tmpPartner.substring(tmpPartner.indexOf('<')+1,
		// tmpPartner.indexOf('>'));
		// System.out.println(tmpPartner);
		// long tmpPartnerID = Long.parseLong(tmpPartner);

		for (GUISession sessions : guiSessions) {
			if (sessions.getChatSession().getPartner().getUser().getName()
					.equals(tmpPartnerNick)) {
				// A chat with this partner is already available
				tabbedpane.setSelectedIndex(tabbedpane
						.indexOfComponent(sessions));
				return;
			}
			if (sessions.getChatSession().getInitiator().getUser().getName()
					.equals(tmpPartnerNick)) {
				// A chat with this partner is already available
				tabbedpane.setSelectedIndex(tabbedpane
						.indexOfComponent(sessions));
				return;
			}

			// if(sessions.getChatSession().getPartner().getUser().getClientId()
			// == tmpPartnerID){
			//
			// }
			// if(sessions.getChatSession().getInitiator().getUser().getClientId()
			// == tmpPartnerID){
			//
			// }

		}

		try {
			daclient.invToChat(tmpPartnerNick);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean readSettings() {

		File file = new File(settingsPath + "/damacrypt/server_settings.txt");
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String servertmpIP = reader.readLine();
				String servertmpPort = reader.readLine();
				reader.close();

				serverIP = servertmpIP.substring(servertmpIP.indexOf("<") + 1,
						servertmpIP.indexOf(">"));
				serverPORT = Integer.valueOf(servertmpPort.substring(
						servertmpPort.indexOf("<") + 1,
						servertmpPort.indexOf(">")));

				return true;

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(this,
					"Server settings are not set or incorrect!");
			return false;
		}

		return false;

	}

	public void writeSettings() {

		try {

			File path = new File(settingsPath + "/damacrypt/");
			File file = new File(settingsPath
					+ "/damacrypt/server_settings.txt");
			if (!path.exists()) {
				try {
					path.mkdirs();
					if (!file.exists())
						file.createNewFile();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			writer.println("serverIP=<" + serverIP + ">");
			writer.println("serverPort=<" + serverPORT + ">");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void applyGuiSession(GUISession s) {
		curGuiSession = s;
		tabbedpane.add(getPartnerName(s.getChatSession(), nick), s);
	}

	public static String getPartnerName(ChatSession chsession, String ownNick) {

		if (chsession.getNameInitiator().equals(ownNick)) {
			return chsession.getNamePartner();
		} else {
			return chsession.getNameInitiator();
		}

	}

	private void requestNick() {
		nick = JOptionPane.showInputDialog(this,
				"Please enter your desired Nickname: ", "Enter Nick",
				JOptionPane.QUESTION_MESSAGE);
	}

	private void startClient() {
		if (nick == null || nick.isEmpty()) {
			JOptionPane
					.showMessageDialog(
							this,
							"Can't start the Client without Nick, please enter a Nick!",
							"Error", JOptionPane.ERROR_MESSAGE);
			requestNick();
			startClient();

		} else if (serverIP == null || serverPORT == -1) {

			JOptionPane.showMessageDialog(this,
					"Server settings are not set or incorrect!");
			ServerSettings ss = new ServerSettings(ChatFrame.this);
			startClient();

		} else {
			DaCryClient client = new DaCryClient(serverIP, serverPORT, nick,
					this);
			this.setClient(client);
			client.start();
		}
	}

	@Override
	public void notifyOnlineUsers(User[] users) {

		System.out.println("Notify New Users!");

		int tmprowcnt = tblModel.getRowCount();

		for (int i = 0; i < tmprowcnt; i++) {
			tblModel.removeRow(0);
		}

		for (int i = 0; i < users.length; i++) {

			Object[] tmp = { "" };

			if (users[i].getName().equals(nick)) {
				tmp[0] = "<You> " + users[i].getName() + " <"
						+ users[i].getClientId() + ">";
			} else {
				tmp[0] = users[i].getName() + " <" + users[i].getClientId()
						+ ">";
			}

			tblModel.addRow(tmp);
		}

		tblModel.fireTableDataChanged();
		tblModel.fireTableStructureChanged();

	}

	@Override
	public void notifyClose() {
		this.setVisible(false);
	}

	public void setClient(DaCryClient client) {
		daclient = client;
	}

	@Override
	public void notifyNewChat(String chatpartnernick) {

	}

	@Override
	public void notifyChatConnectionEstablished(ChatSession s) {
		GUISession tmpGuiSession = new GUISession(s, daclient, nick, tabbedpane);
		guiSessions.add(tmpGuiSession);
		applyGuiSession(tmpGuiSession);
	}

	@Override
	public void notifyNewMessage(ChatSession session, String msg) {

		for (GUISession sessions : guiSessions) {

			if (sessions.getChatSession().getId() == session.getId()) {
				int index = tabbedpane.indexOfComponent(sessions);
				sessions.notifyNewMessage(index, msg);
			}

		}

	}

	@Override
	public String notifyInvalidNickName(String invalidName) {

		JOptionPane
				.showMessageDialog(
						this,
						"Sorry but this Nickname is already taken, please choose another one!",
						"Error", JOptionPane.ERROR_MESSAGE);
		requestNick();
		startClient();

		return null;
	}

	public void notifyServerSettings(String ip, int port) {
		serverIP = ip;
		serverPORT = port;

		writeSettings();

		try {
			if (daclient != null)
				daclient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startClient();
	}

	@Override
	public void notifyChatSessionEnded(ChatSession s) {
		for (GUISession sessions : guiSessions) {
			if (sessions.getChatSession().equals(s)) {
				// TODO Check for bugs!
				tabbedpane.remove(tabbedpane.indexOfComponent(sessions));
				guiSessions.remove(sessions);
			}
		}

	}

}
