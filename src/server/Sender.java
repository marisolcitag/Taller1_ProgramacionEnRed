package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Sender {
	
	private OutputStream os;
	private BufferedWriter bwriter;
	
	//CONSTRUCTOR
	public Sender(OutputStream os) {
		this.os = os;
		bwriter = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	//METODOS
	
	/*
	 * Metodo: sendMessage
	 * Descripción: 
	 */
	public void sendMessage(String msg) {
		new Thread(
				() -> {
					try {
						System.out.println("Servidor Enviando Mensaje...");
						bwriter.write(msg+"\n");
						bwriter.flush();
						System.out.println("Mensaje Enviado");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				).start();
	}
		
	/*
	 * Metodo: sendFile
	 * Descripción: 
	 */
	
	  public void sendFile(String path) { 
		  try { 
			  File f = new File(path);
			  FileInputStream fis = new FileInputStream(f);
	  
			  int bytesLeidos = 0; byte[] buffer = new byte[512]; 
			  while((bytesLeidos = fis.read(buffer) ) != -1 ) {
				  os.write(buffer, 0, bytesLeidos); } fis.close();
				  os.close(); 
		  } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
