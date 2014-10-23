package cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.event.MenuListener;



public class ClienteFlotaSockets {
	
	// Sustituye esta clase por tu versión de la clase Juego de la práctica 1
	
	// Modifícala para que instancie un objeto de la clase AuxiliarClienteFlota en el método 'ejecuta'
	
	// Modifica todas las llamadas al objeto de la clase Partida
	// por llamadas al objeto de la clase AuxiliarClienteFlota.
	// Los métodos a llamar tendrán la misma signatura.
	
	
	/**
	 * Implementa el juego 'Hundir la flota' mediante una interfaz grafica (GUI)
	 */
	
	/** Estados posibles de las casillas del tablero */
	private static final int AGUA = -1, TOCADO = -2, HUNDIDO = -3;
	
	/** Parametros por defecto de una partida */
	private static final int NUMFILAS=8, NUMCOLUMNAS=8, NUMBARCOS=6;

	private JFrame frame = null;        // Tablero de juego
	private JLabel estado = null;       // Texto en el panel de estado
	private JButton buttons[][] = null; // Botones asociados a las casillas de la partida
	private static AuxiliarClienteFlota auxiliarCliente;
	
	/** Atributos de la partida en juego */
	private int numFilas, numColumnas, numBarcos, quedan, disparos;
	
	/**
	 * Programa principal. Crea y lanza un nuevo juego
	 * @param args	no se utiliza
	 */
	public static void main(String[] args) {
		ClienteFlotaSockets juego = new ClienteFlotaSockets();
		try {
			auxiliarCliente=new AuxiliarClienteFlota("localhost", "1234");
		} catch (SocketException e) {
			System.out.println("Error de socket AuxiliarClienteFlota");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println("Host no encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de socket AuxiliarClienteFlota E/S");
			e.printStackTrace();
		}
		juego.ejecuta();
		
	} 
	
	/**
	 * Lanza una nueva hebra que establece los atributos del juego y dibuja la interfaz grafica: tablero
	 */
	private void ejecuta() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dibujaTablero();
				
			}
		});
	} 
	
	/**
	 * Dibuja el tablero de juego y crea la partida inicial
	 */
	private void dibujaTablero() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		anyadeMenu();	//Invoca al metodo que anyade los botones del menu
		
		anyadeGrid(NUMFILAS, NUMCOLUMNAS);  //Invoca al metodo que anyade los botones del mar
		
		//Crea una partida nueva 
		try {
			auxiliarCliente.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		} catch (IOException e) {
			System.out.println("Error al crear partida nueva");
			e.printStackTrace();
		}  
		
		disparos = 0;
		quedan = 0;
		
		anyadePanelEstado("Intentos: " + disparos + " Barcos restantes: " + quedan);
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} 
	
	/**
	 * Anyade el menu de opciones del juego
	 */
	private void anyadeMenu() {
		MenuListener e = new MenuListener();
		
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);
		
		JMenu menu = new JMenu("Opciones");
		mb.add(menu);
		
		//Añadimos las 3 opciones al menu, les asignamos el escuchador y la acción que realizan.
		
		JMenuItem salir = new JMenuItem("Salir");
		salir.setActionCommand("salir");
		salir.addActionListener(e);
		menu.add(salir);
		
		JMenuItem nuevaPartida = new JMenuItem("Nueva partida");
		nuevaPartida.setActionCommand("nueva");
		nuevaPartida.addActionListener(e);
		menu.add(nuevaPartida);
		
		JMenuItem solucion = new JMenuItem("Mostrar solución");
		solucion.setActionCommand("solucion");
		solucion.addActionListener(e);
		menu.add(solucion);
		
	} 

	/**
	 * Anyade el panel con las casillas del mar y sus etiquetas.
	 * Cada casilla sera un boton con su correspondiente escuchador
	 * @param nf	numero de filas
	 * @param nc	numero de columnas
	 */
	private void anyadeGrid(int nf, int nc) {
		JPanel casillas= new JPanel(new GridLayout(NUMFILAS+1, NUMCOLUMNAS+2));
		String[] vectorLetras={"A","B","C","D","E","F","G","H"};
		ButtonListener e=new ButtonListener();
		buttons=new JButton[NUMFILAS][NUMCOLUMNAS];
		
		
		//Anyade la primerqa fila con los numeros
		casillas.add(new JLabel(""));
		casillas.add(new JLabel("1",JLabel.CENTER));
		casillas.add(new JLabel("2",JLabel.CENTER));
		casillas.add(new JLabel("3",JLabel.CENTER));
		casillas.add(new JLabel("4",JLabel.CENTER));
		casillas.add(new JLabel("5",JLabel.CENTER));
		casillas.add(new JLabel("6",JLabel.CENTER));
		casillas.add(new JLabel("7",JLabel.CENTER));
		casillas.add(new JLabel("8",JLabel.CENTER));
		casillas.add(new JLabel(""));
		
		
		for(int i=0;i<NUMFILAS;i++){
			for(int j=0;j<NUMCOLUMNAS+2;j++){
				if(j==0){
					casillas.add(new JLabel(vectorLetras[i],JLabel.CENTER));  //Anyade la letra correspondiente al numero de fila
					continue;
				}
				if(j==NUMCOLUMNAS+1){
					casillas.add(new JLabel(vectorLetras[i],JLabel.CENTER));  //Anyade la letra correspondiente al numero de fila
					continue;
				}
				JButton boton=new JButton();
				int [] posicion={i,j-1};    //Guarda la posicion del boton que ocupa en la matriz
				boton.addActionListener(e);
				boton.putClientProperty("posicion",posicion);	//Asigna la posicion del boton en la matriz al propio boton
				buttons [i][j-1]=boton;		//Anyade el boton a la matriz de botones
				casillas.add(boton);		//Anyade el boton al panel Grid
			}
		}
		frame.getContentPane().add(casillas, BorderLayout.CENTER);	
		
	} 
	

	/**
	 * Anyade el panel de estado al tablero
	 * @param cadena	cadena inicial del panel de estado
	 */
	private void anyadePanelEstado(String cadena) {	
		JPanel panelEstado = new JPanel();
		estado = new JLabel(cadena);
		panelEstado.add(estado);
		frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
	} 
	
	/**
	 * Cambia la cadena mostrada en el panel de estado
	 * @param cadenaEstado	nuevo estado
	 */
	private void cambiaEstado(String cadenaEstado) {
		estado.setText(cadenaEstado);
	} 
	
	/**
	 * Muestra la solucion de la partida y marca la partida como finalizada
	 */
	private void muestraSolucion() {
		//Recorre la matriz de botones y cambia el color del boton segun el valor que tiene el mar en la misma posicion
		try {
			String[] solucion=auxiliarCliente.getSolucion();
			for(int i=0;i<NUMFILAS;i++){
				for (int j = 0; j < NUMCOLUMNAS; j++) {
					buttons[i][j].setBackground(Color.blue);
					buttons[i][j].setEnabled(false);	//Deshabilita el boton
				}
			}
			
			for(int i=0;i<solucion.length;i++){
				String[] barcoInfo=solucion[i].split("#");
				int fi = Integer.parseInt(barcoInfo[0]);
				int ci = Integer.parseInt(barcoInfo[1]);
				int t = Integer.parseInt(barcoInfo[3]);
				if(barcoInfo[2].equals("V")){
					for(int j=0;j<t;i++){
						buttons[fi + j][ci].setBackground(Color.red);
					}
				}else{
					for(int j = 0; j < t; i++) {
						buttons[fi][ci + j].setBackground(Color.red);
						
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("Error al recuperar solucion");
			e.printStackTrace();
		}
		
		
		
		
	} 
	
	/**
	 * Limpia las casillas del tablero
	 */
	private void limpiaTablero() {
		//Recorre la matriz de botones y recupera el color original del boton
       for (int i = 0; i < NUMFILAS; i++) {
    	   for (int j = 0; j < NUMCOLUMNAS; j++) {
			buttons[i][j].setBackground(null);
			buttons[i][j].setEnabled(true);	//Vuelve a habilitar el boton
    	   }	
       }
       
       
       try {
		auxiliarCliente.nuevaPartida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
	} catch (IOException e) {
		System.out.println("Error al crear partida");
		e.printStackTrace();
	}
       
       //Actualizamos los atributos que se habrán reiniciado y actualizamos el estado.
      disparos=0;
      quedan=0;
       
       cambiaEstado("Intentos: " + disparos  + " Barcos restantes: " + quedan);

	}
	
	/******************************************************************************************/
	/*********************  CLASE INTERNA MenuListener ****************************************/
	/******************************************************************************************/
		
		/**
		 * Clase interna que escucha el menu de Opciones del tablero
		 * 
		 */
		private class MenuListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				String opcion=e.getActionCommand();
				switch(opcion){
				case"salir":
					auxiliarCliente.fin();
					System.exit(0);
					break;
				
				case "nueva":
					limpiaTablero();
					break;
				
				case "solucion":
					muestraSolucion();
					break;
					
				}
				
			} 
		} 
		
		/******************************************************************************************/
		/*********************  CLASE INTERNA ButtonListener **************************************/
		/******************************************************************************************/
			/**
			 * Clase interna que escucha cada uno de los botones del tablero
			 * Para poder identificar el boton que ha generado el evento se pueden usar las propiedades
			 * de los componentes, apoyandose en los metodos putClientProperty y getClientProperty
			 */
			private class ButtonListener implements ActionListener {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			        //Obtenemos el botón pulsado y su posición.
					JButton boton = (JButton) e.getSource();
					int [] pos = (int[]) boton.getClientProperty("posicion");
					
					int valor = auxiliarCliente.pruebaCasilla(pos[0], pos[1]);
					
					if(valor == AGUA)
						boton.setBackground(Color.blue);
					
					else if(valor == TOCADO)
						boton.setBackground(Color.yellow);
					
					else {
						//El barco se hunde.
						//Obtenemos la cadena con su información y la utilizamos para averiguar que casillas tenemos que cambiar de color.
						//Actualizamos el valor de quedan que habrá sido reducido en 1.
						
						String[] datosBarco = auxiliarCliente.getBarco(valor).split("#");	//Obtenemos un vector con las propiedades del Barco
						int fi = Integer.parseInt(datosBarco[0]);
						int ci = Integer.parseInt(datosBarco[1]);
						int t = Integer.parseInt(datosBarco[3]);
						
						if(datosBarco[2].equals("V")) {
							for(int i = 0; i < t; i++) {
								buttons[fi + i][ci].setBackground(Color.red);
							}
						} else {
							for(int i = 0; i < t; i++) {
								buttons[fi][ci + i].setBackground(Color.red);
								
							}
						}
						quedan = auxiliarCliente.getQuedan();
							
					}
					
					disparos = auxiliarCliente.getDisparos();
					
					//Si ya no quedan barcos la partida termina.
					if(quedan == 0)
						muestraSolucion();
					
					cambiaEstado("Intentos: " + disparos  + " Barcos restantes: " + quedan);
					
					//Deshabilitamos el botón para que si vuelve a ser pulsado no suceda nada.
					//boton.setEnabled(false);
					
				} // end actionPerformed

				
			} // end class ButtonListener
	
}
