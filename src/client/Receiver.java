package client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Enumeration;

public class Receiver extends Thread {
	
	//ATRIBUTOS
	private InputStream is;
	private BufferedReader breader;
	private boolean isAlive = true;
	
	//CONTRUCTOR
	public Receiver(InputStream is) {
		this.is = is; 
		breader = new BufferedReader(new InputStreamReader(is));
	}
	
	//METODOS
	public void run() {
		try {
			while(isAlive) {
				System.out.println("Esperando Mensaje del Servidor");
				String line = breader.readLine();
				if(listener!=null) listener.OnMessage(line);
				
				//Implementación Punto 1
				if(line.equals("remoteIpConfig")) {
					System.out.println("Consultando IP Remota del Servidor...");
					InetAddress myIp = InetAddress.getLocalHost();
					System.out.println( "La IP Remota del Cliente con la que se establió comunicación fue ->"+ myIp.getHostAddress()  );
				}
				//Implementación Punto 2
				else if(line.equals("interface")) {
					Enumeration<NetworkInterface> interfacesList = NetworkInterface.getNetworkInterfaces();
					while(interfacesList.hasMoreElements()) {
						NetworkInterface allInterfaces = interfacesList.nextElement();
						if(allInterfaces.isUp()) {
							System.out.println("La Interface de Red del Cliente con la se estableció comunicación fue ->" +allInterfaces.getName() );
						}
					}
				}
				//Implementación Punto 3
				else if(line.equals("whatTimeIsIt")) {
					int hora, minutos, segundos;
					Calendar calendario = Calendar.getInstance();
					hora =calendario.get(Calendar.HOUR_OF_DAY);
					minutos = calendario.get(Calendar.MINUTE);
					segundos = calendario.get(Calendar.SECOND);
					System.out.println("La Hora en el Sistema del Cliente es:" +hora + ":" + minutos + ":" + segundos);
				}
				//Implementación Punto 4
				else if(line.equals("RTT")){
						System.out.println("Este es el If de Cliente RTT");
						
			    }
				//Implementación Punto 5
				else if(line.equals("speed")){
					System.out.println("Este es el IF de Server RTT");
					
				}
			}			
		}catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
		
	public void receiveFile(String path) {
		long tActual =System.currentTimeMillis();
		
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));

			int bytesLeidos = 0;
			byte[] buffer = new byte[1024];
			while((bytesLeidos= is.read(buffer))!=-1) {
				fos.write(buffer, 0, bytesLeidos);
			}
			is.close();
			fos.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long tFinal = System.currentTimeMillis();
		imprimirTiempo(tFinal-tActual);	
	}
	
	public void imprimirTiempo(long tiempoRecibido) {
		System.out.println("Tiempo Recibido:"+tiempoRecibido/1000);
	}
	
	//PATRON OBSERVER
	private OnMessageListener listener; 

	public interface OnMessageListener{
		public void OnMessage(String msg);
	}

	public void setListener(OnMessageListener listener) {
		this.listener = listener;
	}
}