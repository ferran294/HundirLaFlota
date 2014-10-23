package servidor;

import java.net.ServerSocket;

import comun.MyStreamSocket;

/**
 * Este modulo contiene la logica de aplicacion del servidor del juego Hundir la flota
 * Utiliza sockets en modo stream para llevar a cabo la comunicacion entre procesos.
 * Puede servir a varios clientes de modo concurrente lanzando una hebra para atender a cada uno de ellos.
 * Se le puede indicar el puerto del servidor en linea de ordenes.
 */


public class ServidorFlotaSockets {
   
   public static void main(String[] args) {
	   
	  // Acepta conexiones vía socket de distintos clientes.
	  // Por cada conexión establecida lanza una hebra de la clase HiloServidorFlota.
	   

	  // Revisad el apartado 5.5 del libro de Liu
 
	   int puertoServidor = 1234; //Puerto por defecto.
	   
	   if(args.length == 1) {
		   puertoServidor = Integer.parseInt(args[0]);
	   }
	   
	   try {
		   //Instaciar un socket stream que acepte las conexiones.
		   ServerSocket miSocketConexion = new ServerSocket(puertoServidor);
		   System.out.println("Servidor Hundir la Flota listo.");
		   
		   while(true) { //Bucle infinito: espera a que se solicite una conexión y la acepta.
			   System.out.println("Esperando conexión.");
			   
			   MyStreamSocket miSocketDatos = new MyStreamSocket(miSocketConexion.accept());
			   System.out.println("Conexión aceptada.");
			   
			   //Crea y arranca un hilo para la sesión con este cliente.
			   Thread hilo = new Thread(new HiloServidorFlota(miSocketDatos));
			   hilo.start();
		   }
		   
	   } catch (Exception ex) {
		   ex.printStackTrace();
	   }
   } //fin main
} // fin class
