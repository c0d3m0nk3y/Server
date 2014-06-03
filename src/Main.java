import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;



public class Main {
	private static String inputLine;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(63400);
			Socket clientSocket = serverSocket.accept();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while((inputLine = bufferedReader.readLine()) != null) {
				System.out.println(inputLine);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		}

}
