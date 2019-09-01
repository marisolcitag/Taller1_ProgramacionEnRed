package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import server.Receiver.OnMessageListener;

public class TCPConnection {
	
	private static TCPConnection tcpInstance;
	private int puerto;
	private String ip;	
	private OnMessageListener main;
	private ServerSocket server;
	private Socket socket;
	private Sender sender;
	private Receiver receiver;
	
	private TCPConnection() {
	}
	
	public void setMain(OnMessageListener main) {
		this.main=main;
	}
	
	public static synchronized TCPConnection setPuerto(int puerto) {
		tcpInstance.puerto = puerto;
		return tcpInstance;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	 
	public synchronized static TCPConnection getInstance() {
		if(tcpInstance==null)
			tcpInstance= new TCPConnection();
		return tcpInstance;
	}
	
	public static TCPConnection waitForConnection() {
		try {
			tcpInstance.server= new ServerSocket(tcpInstance.puerto);
			System.out.println("Esperando Conexión Con el Cliente");
			tcpInstance.socket=tcpInstance.server.accept();
			System.out.println("Conexion Con el Cliente Aceptada");
			tcpInstance.initReaderAndWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tcpInstance;
	}
	
	/*
	 * Metodo: initReader
	 * Descripción: 
	 */
	public void initReaderAndWriter() {
		try {
			sender= new Sender(socket.getOutputStream());
			receiver = new Receiver(socket.getInputStream());
			receiver.setListener(main);
			receiver.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Metodo: requestForConnection()
	 * Descripción: 
	 */
	public void requestForConnection() {
		try {
			System.out.println("Solicitando Conexión al Cliente...");
			socket= new Socket(ip,puerto);
			System.out.println("Conectado con el Cliente");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/*
	 * Metodo: sendMessage()
	 * Descripción: 
	 */
	public void sendMessage(String msg) {
		sender.sendMessage(msg);
	}
}
