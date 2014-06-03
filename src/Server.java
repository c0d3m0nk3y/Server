import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {
	private InetAddress hostAddress;
	private ServerSocket serverSocket;
	private Socket socket;
	private ArrayList<User> users = new ArrayList<User>();
	private static final int PORT = 63455;
	
	public void run() {
		System.out.println("Server Running");
		
		while(true) {
			removeDisconnectedUsers();
			acceptNewUser();
		}
	}
	
	private void acceptNewUser() {
		try {
			socket = serverSocket.accept();
			users.add(new User(socket));
			System.out.println(socket + " has connected.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void removeDisconnectedUsers() {
		for(int userIndex = 0; userIndex < users.size(); userIndex++) {
			User user = users.get(userIndex);
			if(!user.isConnected()) {
				user.disconnect();
				users.remove(userIndex);
				userIndex--;
				System.out.println("Removed dead client " + user.getID());
			}
		}
	}
	
	public Server() {
		if(!startServer()) return;
		
		start();
	}
	
	private boolean startServer() {
		if(!getHostAddress() || !getServerSocket()) {
			closeSockets();
			return false;
		}
		
		return true;
	}
	
	private boolean getHostAddress() {
		try {
			hostAddress = InetAddress.getLocalHost();
			System.out.println("Host Address " + hostAddress);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	private boolean getServerSocket() {
		try {
			serverSocket = new ServerSocket(PORT,0,hostAddress);
			System.out.println("Socket " + serverSocket + " created");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	private void closeSockets() {
		try {
			System.out.println("Closing Sockets...");
			
			if(serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
				System.out.println("	serverSocket closed");
			}
			
			if(socket != null) {
				socket.close();
				socket = null;
				System.out.println("	socket closed");
			}
			
			System.out.println("...Sockets Closed");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
