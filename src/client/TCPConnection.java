package client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import client.Receiver.OnMessageListener;

public class TCPConnection {
	
	//ATRIBUTOS
	private static TCPConnection tcpInstance;
	private int puerto;
	private String ip;	
	private OnMessageListener main;
	private ServerSocket server;
	private Socket socket;
	private Sender sender;
	private Receiver receiver;
	
	//CONSTRUCTOR
	private TCPConnection() {
	
	}
	
	//METODOS
	public synchronized static TCPConnection getInstance() {
		if(tcpInstance==null)
			tcpInstance = new TCPConnection();
		return tcpInstance;
	}
	
	public void setMain(OnMessageListener main) {
		this.main=main;
	}

	public static TCPConnection setPuerto(int puerto) {
		tcpInstance.puerto = puerto;
		return tcpInstance;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	private void initReaderAndWriter() {
		try {
			sender = new Sender(socket.getOutputStream());
			receiver = new Receiver(socket.getInputStream());
			receiver.setListener(main);
			receiver.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static TCPConnection waitForConnection() {
		try {
			tcpInstance.server=new ServerSocket(tcpInstance.puerto);
			System.out.println("Esperando Conexión Con el Servidor");
			tcpInstance.socket=tcpInstance.server.accept();
			System.out.println("Conexion Con el Servidor Aceptada");
			tcpInstance.initReaderAndWriter();	
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return tcpInstance;
	}
	
	public void requestConnection() {
		try {
			System.out.println("Solicitando Conexión al Servidor...");
			socket = new Socket(ip,puerto);
			System.out.println("Conectado con el Servidor");
			initReaderAndWriter();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void sendMessage(String msg) {
		sender.sendMessage(msg);
	}
}
