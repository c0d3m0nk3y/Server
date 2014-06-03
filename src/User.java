import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class User {
	private Socket socket;
	private boolean connected;
	private String ID;
	private Inport inport;
	
	public User(Socket socket) {
		this.socket = socket;
		ID = socket.toString();
		connected = true;
		inport = new Inport();
		inport.start();
	}
	
	public void disconnect() {
		try {
			System.out.println("Disconnecting " + ID + "...");
			connected = false;
			
			if(socket != null) {
				socket.close();
				socket = null;
			}
			
			if(inport != null) {
				inport.close();
			}
			System.out.println("..." + ID + " Disconnected");
		} catch (Exception e) {
			System.out.println(e + "Failed to close socket " + socket);
		}
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public String getID() {
		return ID;
	}
	
	public String toString() {
		return socket.toString();
	}

	private class Inport extends Thread {
		private ObjectInputStream in;
		
		public void run() {
			if(!getInputStream()) return;
			
			while(true) {
				try {
					Thread.sleep(1000);
					System.out.println("Tick " + toString());
					
					System.out.println(in.readUTF());
				} catch (Exception e) {
					System.out.println(toString() + " has been interuppted " + e);
					disconnect();
					return;
				}
			}
		}
		
		private boolean getInputStream() {
			try {
				in = new ObjectInputStream(socket.getInputStream());
				return true;
			} catch (Exception e) {
				System.out.println("User.getInputStrem " + e);
				close();
				return false;
			}
		}
		
		private void close() {
			try {
				if(in != null) {
					in.close();
					in = null;
				}
			} catch (Exception e) {
				System.out.println("Inport " + toString() + " failed to disconnect " + e);
			}
		}
	}
}
