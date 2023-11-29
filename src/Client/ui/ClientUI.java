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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaUI;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.w3c.dom.Text;

import Client.socket.client;
import Object.Code;
import Object.CodeResult;

import java.awt.TextArea;
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
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;

public class ClientUI {

	private JFrame frame;
	private JPanel contentPane;
	private RSyntaxTextArea textArea_Src;
	private RSyntaxTextArea textArea_Input;
	private RSyntaxTextArea textArea_Result;
	private ComboSuggestion cb;
	private JLayeredPane layeredPane;
	private LoadingScreen loadingScreen;
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
	// public static void main(String[] args) {
	// try {
	// ClientUI frame = new ClientUI();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

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
		frame.setLayout(null);
		frame.setBounds(100, 100, 1270, 781);
		frame.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 1276, 781);

		layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		layeredPane.setOpaque(true);
		layeredPane.setBackground(new Color(255, 255, 255, 60));
		frame.setContentPane(layeredPane);
		loadingScreen = new LoadingScreen();

		textArea_Src = new RSyntaxTextArea(20, 60);
		textArea_Src.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		textArea_Src.setCodeFoldingEnabled(true);
		textArea_Src.setFont(new Font("", Font.PLAIN, 15));
		RTextScrollPane sp = new RTextScrollPane(textArea_Src);
		sp.setBounds(10, 59, 900, 487);
		contentPane.add(sp);

		textArea_Input = new RSyntaxTextArea();
		textArea_Input.setHighlightCurrentLine(false);
		textArea_Input.setFont(new Font("Dialog", Font.ITALIC, 15));
		RTextScrollPane sp1 = new RTextScrollPane(textArea_Input);
		sp1.setBounds(920, 59, 320, 487);
		contentPane.add(sp1);

		textArea_Result = new RSyntaxTextArea();
		textArea_Result.setHighlightCurrentLine(false);
		textArea_Result.setFont(new Font("Dialog", Font.ITALIC, 14));
		textArea_Result.setEditable(false);
		RTextScrollPane sp2 = new RTextScrollPane(textArea_Result);
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
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				run();	
			}
		});
		btnRun.setBounds(630, 30, 80, 25);
		contentPane.add(btnRun);

		btnSave = new Button("Save");
		btnSave.setColor(new Color(66, 135, 245));
		btnSave.setColorOver(new Color(66, 135, 200));
		btnSave.setColorClick(new Color(66, 135, 200));
		btnSave.setBorderColor(new Color(66, 135, 200));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setForeground(Color.white);
		btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		btnSave.setBounds(720, 30, 80, 25);
		contentPane.add(btnSave);

		btnUpload = new Button("Upload");
		btnUpload.setColor(new Color(66, 135, 245));
		btnUpload.setColorOver(new Color(66, 135, 200));
		btnUpload.setColorClick(new Color(66, 135, 200));
		btnUpload.setBorderColor(new Color(66, 135, 200));
		btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpload.setForeground(Color.white);
		btnUpload.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
		btnUpload.setBounds(810, 30, 100, 25);
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
		cb.setLocation(10, 25);
		cb.setModel(new DefaultComboBoxModel(new String[] { "C", "Python", "Java", "Javascript"}));
		cb.setEditable(false);
		cb.setSize(125, 30);
		contentPane.add(cb);

		connectionStatus = new JLabel();
		connectionStatus.setBounds(1050, 18, 150, 30);
		connectionStatus.setFont(new Font("", Font.PLAIN, 13));
		connectionStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(connectionStatus);
		connectionStatus.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!clientSocket.connect()) {
					connectServer();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		Timer timer = new Timer(10000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// connectionChecking();
			}
			
		});
		timer.start();

		layeredPane.add(contentPane, 1, 0);
		layeredPane.add(loadingScreen, 2, 0);

		frame.setVisible(true);
		connectServer();

	}

	private void run() {
		// if (clientSocket.) {
		loadingScreen.setVisible(true);
		btnRun.setEnabled(false);
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
				// String rec = (String) clientSocket.receive();
				// System.out.println(rec);
				// textArea_Result.setText(rec);
				return true;
			}

			protected void done() {
				try {
					loadingScreen.setVisible(false);
					btnRun.setEnabled(true);
					// textArea_Result.setEnabled(true
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		swingWorker.execute();
		// } else {
		// 	Notification panel = new Notification(
		// 		frame, Notification.Type.WARNING, Notification.Location.TOP_RIGHT,
		// 			"Unable to connect to server");
		// 	panel.showNotification();
		// }

	}
	
	private void connectServer() {
		JOptionPane optionPane = new JOptionPane();
		clientSocket.setHostName(optionPane.showInputDialog(frame, "Server IP", clientSocket.getHostName()));
		loadingScreen.setVisible(true);

		SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				clientSocket = new client(clientSocket.getHostName(), 1234);
				return clientSocket.connect();
			}

			@Override
			protected void done() {
				try {
					loadingScreen.setVisible(false);
					if (get()) {
						Notification panel = new Notification(frame, Notification.Type.SUCCESS,
								Notification.Location.TOP_RIGHT, "Connected to server");
						panel.showNotification();
						connectionStatus.setText("<HTML><U>Connected</U></HTML>");
						connectionStatus.setForeground(Color.green);
					} else {
						Notification panel = new Notification(frame, Notification.Type.WARNING,
								Notification.Location.TOP_RIGHT, "Unable to connect to server");
						panel.showNotification();
						connectionStatus.setText("<HTML><U>Disconnected.Click here to reconnect</U></HTML>");
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

	private void connectionChecking() {
		SwingWorker swingWorker = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				clientSocket = new client(clientSocket.getHostName(), 1234);
				return clientSocket.connect();
			}

			@Override
			protected void done() {
				try {
					if (get()) {
						// Notification panel = new Notification(frame, Notification.Type.SUCCESS,
						// 		Notification.Location.TOP_RIGHT, "Connected to server");
						// panel.showNotification();
						connectionStatus.setText("<HTML><U>Connected</U></HTML>");
						connectionStatus.setForeground(Color.green);
					} else {
						// Notification panel = new Notification(frame, Notification.Type.WARNING,
						// 		Notification.Location.TOP_RIGHT, "Unable to connect to server");
						// panel.showNotification();
						connectionStatus.setText("<HTML><U>Disconnected.Click here to reconnect</U></HTML>");
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
}
