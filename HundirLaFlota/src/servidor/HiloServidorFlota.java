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

             case 1: // Crea nueva partida
            	 partida = new Partida(Integer.parseInt(op[1]),Integer.parseInt(op[2]), Integer.parseInt(op[3]));
            	 System.out.println("Partida nueva creada.");
            	 break;
                          
             case 2:  // Prueba una casilla y devuelve el resultado al cliente
            	 int respuesta = partida.pruebaCasilla(Integer.parseInt(op[1]), Integer.parseInt(op[2]));
            	 System.out.println(respuesta);
                 myDataSocket.sendMessage("" + respuesta);
            	 break;
             
             case 3:  // Obtiene los datos de un barco y se los devuelve al cliente
            	 String res = partida.getBarco(Integer.parseInt(op[1]));
            	 myDataSocket.sendMessage(res);
                 break;
             
             case 4:  // Devuelve al cliente la solucion en forma de vector de cadenas
            	 String[] solucion = partida.getSolucion();
               // Primero envia el numero de barcos 
            	 myDataSocket.sendMessage("" + solucion.length);
               // Despues envia una cadena por cada barco
            	 for (int i = 0; i < solucion.length; i++)
            		 myDataSocket.sendMessage(solucion[i]);
               break;
               
             
             
             //Se han añadido estas dos opciones para permitir la actualización del estado del juego.
             
             case 5: 
            	//Envia los barcos que quedan en la partida.
            	 myDataSocket.sendMessage("" + partida.getQuedan());
            	 break;
             
             case 6: 
            	 //Envia los disparos realizados.
            	 myDataSocket.sendMessage("" + partida.getDisparos());
            	 break;
             
         } // fin switch
       } // fin while   
     } // fin try
     catch (Exception ex) {
        System.out.println("Exception caught in thread: " + ex);
     } // fin catch
   } //fin run
   
} //fin class 
