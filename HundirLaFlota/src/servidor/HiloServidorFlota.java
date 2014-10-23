package servidor;


import java.io.IOException;
import java.net.SocketException;

import partida.*;
import comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del juego Hundir la flota.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

 // Revisar el apartado 5.5. del libro de Liu

class HiloServidorFlota implements Runnable {
   MyStreamSocket myDataSocket;
   private Partida partida = null;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 */
   HiloServidorFlota(MyStreamSocket myDataSocket) {
	   this.myDataSocket = myDataSocket;
   }
 
   /**
	* Gestiona una sesion con un cliente	
   */
   public void run( ) {
      boolean done = false;
      int operacion = 0;
      String mensaje;
      // ...
      try {
         while (!done) {
        	 // Recibe una peticion del cliente
        	 mensaje = myDataSocket.receiveMessage();
        	 System.out.println("Orden recibida: " + mensaje);
        	 
        	 // Extrae la operación y los argumentos
             String [] op = mensaje.split("#");
             operacion = Integer.parseInt(op[0]);
        	 
             switch (operacion) {
             case 0:  // fin de conexión con el cliente
            	 System.out.println("Fin de la sesión.");
            	 myDataSocket.close();
            	 done = true;
            	 break;

             case 1: { // Crea nueva partida
            	 partida = new Partida(Integer.parseInt(op[1]),Integer.parseInt(op[2]), Integer.parseInt(op[3]));
            	 break;
             }             
             case 2: { // Prueba una casilla y devuelve el resultado al cliente
            	 int respuesta = partida.pruebaCasilla(Integer.parseInt(op[1]), Integer.parseInt(op[1]));
                 myDataSocket.sendMessage("" + respuesta);
            	 break;
             }
             case 3: { // Obtiene los datos de un barco y se los devuelve al cliente
            	 // ... 
                 break;
             }
             case 4: { // Devuelve al cliente la solucion en forma de vector de cadenas
        	   // Primero envia el numero de barcos 
               // Despues envia una cadena por cada barco
               break;
             }
         } // fin switch
       } // fin while   
     } // fin try
     catch (Exception ex) {
        System.out.println("Exception caught in thread: " + ex);
     } // fin catch
   } //fin run
   
} //fin class 
