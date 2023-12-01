package Client.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent.KeyBinding;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaUI;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.w3c.dom.Text;

import Client.socket.client;
import Object.Code;
import Object.CodeResult;

import java.awt.RenderingHints.Key;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.net.ConnectException;
import java.nio.file.Files;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class ClientUI {

	private JFrame frame;
	private JPanel contentPane;
	private GridBagLayout layout;
	GridBagConstraints gbc;
	private JLayeredPane layeredPane;
	private LoadingScreen loadingScreen;
	private RSyntaxTextArea textArea_Src;
	private RSyntaxTextArea textArea_Input;
	private RSyntaxTextArea textArea_Result;
	private ComboSuggestion cb;
	private Button btnRun;
	private Button btnSave;
	private Button btnUpload;
	private JLabel connectionStatus;
	
	private client clientSocket = new client();

	// private String textResult = "";
	// private int lengthOfTextResult = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	try {
		ClientUI frame = new ClientUI();
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public ClientUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setTitle("Code Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setLayout(null);
		frame.setSize( 1270, 781);
		frame.setMinimumSize(new Dimension(500,500));
		frame.setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		layout = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		contentPane.setLayout(layout);
		frame.setContentPane(contentPane);

		// layeredPane = new JLayeredPane();
		// // layeredPane.setLayout(new BorderLayout());
		// layeredPane.setOpaque(true);
		// layeredPane.setBackground(new Color(255, 255, 255, 60));
		// layeredPane.setPreferredSize(new Dimension(1270, 781));
		// layeredPane.setMaximumSize(contentPane.getPreferredSize());
		// frame.setContentPane(layeredPane);
		loadingScreen = new LoadingScreen();

		textArea_Src = new RSyntaxTextArea(20, 60);
		textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		textArea_Src.setCodeFoldingEnabled(true);
		textArea_Src.setLineWrap(true);
		textArea_Src.setFont(new Font("", Font.PLAIN, 15));
		RTextScrollPane sp = new RTextScrollPane(textArea_Src);
		sp.setMinimumSize(sp.getPreferredSize());
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 1;
		gbc.gridx = 0;
		layout.setConstraints(sp, gbc);
		contentPane.add(sp);

		textArea_Input = new RSyntaxTextArea(20, 20);
		textArea_Input.setHighlightCurrentLine(false);
		textArea_Input.setFont(new Font("Dialog", Font.ITALIC, 15));
		RTextScrollPane sp1 = new RTextScrollPane(textArea_Input);
		sp1.setMinimumSize(sp1.getPreferredSize());
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridy = 1;
		gbc.gridx = 1;
		layout.setConstraints(sp1, gbc);
		contentPane.add(sp1);
		textArea_Result = new RSyntaxTextArea(10,100);
		textArea_Result.setHighlightCurrentLine(false);
		textArea_Result.setFont(new Font("Dialog", Font.ITALIC, 14));
		textArea_Result.setEditable(false);
		RTextScrollPane sp2 = new RTextScrollPane(textArea_Result);
		sp2.setMinimumSize(sp2.getPreferredSize());
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		layout.setConstraints(sp2, gbc);
		sp2.setBounds(10, 550, 1230, 180);
		contentPane.add(sp2);
		// textArea_Result.setEnabled(false);
		// SwingWorker swingWorker = new SwingWorker<Void, Void>() {
		// 	@Override
		// 	protected Void doInBackground() throws Exception {
		// 		String line = "";
		// 		while ((line = (String) clientSocket.receive()) != null) {
		// 			lengthOfTextResult += line.length();
		// 			textArea_Result.append(line);
		// 		}
		// 		return null;
		// 	}

		// 	@Override
		// 	protected void done() {
				
		// 	}
		// };

		// swingWorker.execute();
		// textArea_Result.addKeyListener(new KeyListener() {
			
		// 	@Override
		// 	public void keyPressed(KeyEvent e) {
		// 		// TODO Auto-generated method stub
		// 		try {
		// 			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		// 				textArea_Result.insert("\n", textArea_Result.getText().length());
		// 				textResult += e.getKeyChar();
		// 				clientSocket.send(textResult);
		// 				System.out.println(textResult);
		// 			} else if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
		// 				textArea_Result.insert(e.getKeyChar() + "", textArea_Result.getText().length());
		// 				textResult += e.getKeyChar();
		// 			} else if (textArea_Result.getCaretPosition() > lengthOfTextResult) { 
		// 				textArea_Result.replaceRange("",textArea_Result.getCaretPosition()-1, textArea_Result.getCaretPosition());
		// 			}
					
		// 		} catch (IllegalArgumentException | StringIndexOutOfBoundsException ex){
		// 		} catch(Exception ex) {
		// 			ex.printStackTrace();
		// 		}
		// 	}

		// 	@Override
		// 	public void keyTyped(KeyEvent e) {}
		// 	@Override
		// 	public void keyReleased(KeyEvent e) {}

		// });

		btnRun = new Button("");
		btnRun.setColor(new Color(66, 135, 245));
		btnRun.setColorOver(new Color(66, 135, 200));
		btnRun.setColorClick(new Color(66, 135, 200));
		btnRun.setBorderColor(new Color(66, 135, 200));
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRun.setIcon(new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\logo\\icons8-video-48.png")
				.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		btnRun.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRun.setPreferredSize(new Dimension(80,30));
		btnRun.setMinimumSize(btnRun.getPreferredSize());
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				run();	
			}
		});
		// btnRun.setBounds(630, 30, 80, 25);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(35, 530, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(btnRun, gbc);
		contentPane.add(btnRun);

		btnSave = new Button("Save");
		btnSave.setColor(new Color(66, 135, 245));
		btnSave.setColorOver(new Color(66, 135, 200));
		btnSave.setColorClick(new Color(66, 135, 200));
		btnSave.setBorderColor(new Color(66, 135, 200));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setForeground(Color.white);
		btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSave.setPreferredSize(new Dimension(100,30));
		btnSave.setMinimumSize(btnSave.getPreferredSize());
		// btnSave.setIcon(new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\logo\\stop-button.png")
		// 		.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				String type = (String) cb.getSelectedItem();
				String extension = "";
				switch (type) {
					case "C":
						extension = ".c";
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("C Program", "c"));
						break;
					case "Python":
						extension = ".py";
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Python Program", "py"));
						break;
					case "Java":
						extension = ".java";
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java Program", "java"));
						break;
					case "Javascript":
						extension = ".js";
						fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Javascript Program", "js"));
						break;
					default:
						break;
				}
				if (fileChooser.showSaveDialog(btnSave) == JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().toString() + extension);
					try {
						FileWriter fw = new FileWriter(file);
						fw.write(textArea_Src.getText());
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		// btnSave.setBounds(720, 30, 80, 25);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(35, 620, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(btnSave, gbc);
		contentPane.add(btnSave);

		btnUpload = new Button("Upload");
		btnUpload.setColor(new Color(66, 135, 245));
		btnUpload.setColorOver(new Color(66, 135, 200));
		btnUpload.setColorClick(new Color(66, 135, 200));
		btnUpload.setBorderColor(new Color(66, 135, 200));
		btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpload.setForeground(Color.white);
		btnUpload.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpload.setPreferredSize(new Dimension(100,30));
		btnUpload.setMinimumSize(btnUpload.getPreferredSize());
		// btnUpload.setIcon(new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\logo\\upload.png")
		// 		.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH)));
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(btnUpload);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						String content = Files.readString(selectedFile.toPath());
						textArea_Src.setText(content);
						String[] types = selectedFile.getName().split("\\.");
						String type = types[types.length - 1];
						switch (type) {
							case "c":
								cb.setSelectedIndex(0);
								break;
							case "py":
								cb.setSelectedIndex(1);
								break;
							case "java":
								cb.setSelectedIndex(2);
								break;
							case "js":
								cb.setSelectedIndex(3);
								break;
							default:
								break;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		// btnUpload.setBounds(810, 30, 100, 25);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(35, 730, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(btnUpload, gbc);
		contentPane.add(btnUpload);

		cb = new ComboSuggestion();
		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ComboSuggestion comboBox = (ComboSuggestion) event.getSource();

				Object selected = comboBox.getSelectedItem();
				if (selected.toString().equals("C"))
					textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
				else if (selected.toString().equals("Python"))
					textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
				// else if (selected.toString().equals("PHP"))
					// textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP);
				else if (selected.toString().equals("Javascript"))
					textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
				else if (selected.toString().equals("Java"))
					textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

			}
		});
		// cb.setLocation(10, 25);
		cb.setModel(new DefaultComboBoxModel(new String[] { "C", "Python", "Java", "Javascript"}));
		Map<Object, Icon> icons = new HashMap<Object, Icon>();	
		icons.put("C", new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\img\\letter-c.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		icons.put("Python", new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\img\\python.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		icons.put("Java", new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\img\\java.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		icons.put("Javascript", new ImageIcon(new ImageIcon(".\\src\\Client\\ui\\img\\js.png").getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		cb.setRenderer(new IconListRenderer(icons));
		cb.setEditable(false);
		cb.setPreferredSize(new Dimension(120,30));
		cb.setMinimumSize(cb.getPreferredSize());
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(40, 0, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(cb, gbc);
		contentPane.add(cb);

		connectionStatus = new JLabel();
		connectionStatus.setBounds(980, 18, 250, 30);
		connectionStatus.setFont(new Font("", Font.PLAIN, 13));
		connectionStatus.setText("<HTML><nobr><U>Disconnected with server " + clientSocket.getHostName() +"</U></nobr></HTML>");
		connectionStatus.setForeground(Color.red);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.insets = new Insets(20, 900, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		layout.setConstraints(connectionStatus, gbc);
		contentPane.add(connectionStatus);
		// connectionStatus.addMouseListener(new MouseListener() {
		// 	@Override
		// 	public void mouseClicked(MouseEvent e) {
		// 		connectServer();
		// 	}

		// 	@Override
		// 	public void mousePressed(MouseEvent e) {
		// 	}

		// 	@Override
		// 	public void mouseReleased(MouseEvent e) {
		// 	}

		// 	@Override
		// 	public void mouseEntered(MouseEvent e) {
		// 	}

		// 	@Override
		// 	public void mouseExited(MouseEvent e) {
		// 	}

		// });
		connectServer();
		checkConnection();
		// layeredPane.add(contentPane, 1, 0);
		// layeredPane.add(loadingScreen, 2, 0);
		frame.setVisible(true);
		frame.pack();

	}

	private void run() {
		// if (clientSocket.) {
		loadingScreen.setVisible(true);
		btnRun.setEnabled(false);
		textArea_Result.setEditable(false);
		textArea_Input.setEditable(false);
		SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
			Code code;
			@Override
			protected Boolean doInBackground() throws Exception {
				code = new Code();
				code.setLanguage((String) cb.getSelectedItem());
				code.setSource(textArea_Src.getText());
				code.setInput(textArea_Input.getText());
				clientSocket.send(code);
				CodeResult result = (CodeResult) clientSocket.receive();
				textArea_Src.setText(result.getFormattedSrc());
				textArea_Result.setText(result.getExecResult());
				return true;
			}

			protected void done() {
				try {
					loadingScreen.setVisible(false);
					btnRun.setEnabled(true);
					textArea_Result.setEditable(true);
					textArea_Input.setEditable(true);
				} catch (Exception e) {
				}
			}
		};
		swingWorker.execute();
	}
	
	private void connectServer() {
		SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				JOptionPane optionPane = new JOptionPane();
				String hostName = optionPane.showInputDialog(frame, "Server IP", clientSocket.getHostName());
				clientSocket.setHostName(hostName);
				loadingScreen.setVisible(true);
				clientSocket = new client(clientSocket.getHostName(), 1234);
				return clientSocket.isConnected();
			}

			@Override
			protected void done() {
				try {
					loadingScreen.setVisible(false);
					if (get()) {
						Notification panel = new Notification(frame, Notification.Type.SUCCESS,
								Notification.Location.CENTER, "Connected to server");
						panel.showNotification();
						connectionStatus.setText("<HTML><nobr><U>Connected with server " + clientSocket.getHostName() + "</U></nobr></HTML>");
						connectionStatus.setForeground(Color.green);
					} else {
						Notification panel = new Notification(frame, Notification.Type.WARNING,
								Notification.Location.CENTER, "Unable to connect to server");
						panel.showNotification();
						connectionStatus.setText("<HTML><nobr><U>Disconnected with server " + clientSocket.getHostName() +"</U></nobr></HTML>");
						connectionStatus.setForeground(Color.red);
					}
				} catch (HeadlessException | InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		swingWorker.execute();
	}

	private void checkConnection() {
		Timer timer = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (clientSocket.isConnected()) {
					connectionStatus.setText("<HTML><nobr><U>Connected with server " + clientSocket.getHostName() + "</U></nobr></HTML>");
					connectionStatus.setForeground(Color.green);
				} else {
					connectionStatus.setText("<HTML><nobr><U>Disconnected with server " + clientSocket.getHostName() +"</U></nobr></HTML>");
					connectionStatus.setForeground(Color.red);
					clientSocket = new client(clientSocket.getHostName(), 1234);
				}
			}	
		});
		timer.start();
	}

	// private void connectionChecking() {
	// 	SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
	// 		@Override
	// 		protected Boolean doInBackground() throws Exception {
	// 			clientSocket = new client(clientSocket.getHostName(), 1234);
	// 			return clientSocket.connect();
	// 		}

	// 		@Override
	// 		protected void done() {
	// 			try {
	// 				if (get()) {
	// 					// Notification panel = new Notification(frame, Notification.Type.SUCCESS,
	// 					// 		Notification.Location.TOP_RIGHT, "Connected to server");
	// 					// panel.showNotification();
	// 					connectionStatus.setText("<HTML><U>Connected</U></HTML>");
	// 					connectionStatus.setForeground(Color.green);
	// 				} else {
	// 					// Notification panel = new Notification(frame, Notification.Type.WARNING,
	// 					// 		Notification.Location.TOP_RIGHT, "Unable to connect to server");
	// 					// panel.showNotification();
	// 					connectionStatus.setText("<HTML><U>Disconnected.Click here to reconnect</U></HTML>");
	// 					connectionStatus.setForeground(Color.red);
	// 				}
	// 			} catch (HeadlessException | InterruptedException | ExecutionException e) {
	// 				// TODO Auto-generated catch block
	// 				e.printStackTrace();
	// 			}
	// 		}
	// 	};

	// 	swingWorker.execute();
	// }
}
