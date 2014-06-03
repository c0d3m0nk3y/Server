import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {
	private static String inputLine;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader bufferedReader;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(63455);
			clientSocket = serverSocket.accept();
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while((inputLine = bufferedReader.readLine()) != null) {
				System.out.println(inputLine);
			}
		} catch (Exception e) {
			System.out.println(e);
			closeSocket();
		}
		
	}
	
	private static void closeSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
			
			if(clientSocket != null) {
				clientSocket.close();
				clientSocket = null;
			}
			
			if(bufferedReader != null) {
				bufferedReader.close();
				bufferedReader = null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
