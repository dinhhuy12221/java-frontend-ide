package Client.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.security.PublicKey;

import Client.ui.ClientUI;
import Object.Code;

public class client {
	private Socket socket;
	private int port;
	private String hostName = "localhost";
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private encryption encryption;

	public client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}
	
	public client() {
		this.hostName = "localhost";
		this.port = 0;
	}

	public boolean connect() {
		try {
			this.socket = new Socket(this.hostName, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
			generateKey();
			return true;
		} catch (ConnectException e) {
			System.out.println("[Notification] Diconnect to server");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	private void generateKey() {
		try {
			// encryption = new encryption();
			// this.out.writeObject(encryption.getKeyPair().getPublic());
			// this.out.flush();

			// byte[] encryptedKey = (byte[]) this.in.readObject();
			// encryption.setEncryptedKey(encryptedKey);
			// encryption.decryptKey();

			encryption = new encryption();
			encryption.setPublicKey((PublicKey) this.in.readObject());

			encryption.encryptKey();

			this.out.writeObject(encryption.getEncryptedKey());
			this.out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (socket != null) {
				this.in.close();
				this.out.close();
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(Object object) {
		try {
			byte[] bytes = encryption.encryptData((Code) object);
			this.out.writeObject(bytes);
			this.out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String str) {
		try {
			byte[] bytes = encryption.encryptData(str);
			this.out.writeObject(bytes);
			this.out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object receive() {
		try {
			byte[] bytes = (byte[]) this.in.readObject();
			Object object = (Object) encryption.decryptData(bytes);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
