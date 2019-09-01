package client;
import java.util.Scanner;
import client.Receiver.OnMessageListener;

public class Main implements OnMessageListener{
	
	public Main() {
		TCPConnection connection = TCPConnection.getInstance();
		connection.setPuerto(5555);
		connection.setIp("127.0.0.1");
		connection.setMain(this);
		connection.requestConnection();
		
		 Scanner scanner = new Scanner(System.in); 
		 while(true) { 
			 String line =scanner.nextLine(); 
			 connection.sendMessage(line); 
			 }
		 
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();

	}

	@Override
	public void OnMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(">"+msg);
	}
}
