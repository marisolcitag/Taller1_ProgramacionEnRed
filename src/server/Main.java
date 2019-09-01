package server;
import java.util.Scanner;
import server.Receiver.OnMessageListener;

public class Main implements OnMessageListener{

	public Main() {
		TCPConnection connection = TCPConnection.getInstance().setPuerto(5555);
		connection.setMain(this);
		connection.waitForConnection();
			
		Scanner scanner = new Scanner(System.in);
		while(true) {
			String line = scanner.nextLine();
			connection.sendMessage(line);
		}
	}
	
	public static void main(String[] args) {
		Main server = new Main();
	}

	@Override
	public void OnMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(">>"+msg);
	}
}
