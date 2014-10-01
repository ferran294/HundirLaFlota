package tablero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Juego {
	
	/**
	 * Implementa el juego 'Hundir la flota' mediante una interfaz grafica (GUI)
	 */
	
	/** Estados posibles de las casillas del tablero */
	private static final int AGUA = -1, TOCADO = -2, HUNDIDO = -3;
	
	/** Parametros por defecto de una partida */
	private static final int NUMFILAS=8, NUMCOLUMNAS=8, NUMBARCOS=6;

	private Partida partida = null;     // Objeto con los datos de la partida en juego
	private JFrame frame = null;        // Tablero de juego
	private JLabel estado = null;       // Texto en el panel de estado
	private JButton buttons[][] = null; // Botones asociados a las casillas de la partida
	
	/** Atributos de la partida en juego */
	private int numFilas, numColumnas, numBarcos, quedan, disparos;
	
	/**
	 * Programa principal. Crea y lanza un nuevo juego
	 * @param args	no se utiliza
	 */
	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.ejecuta();
	} // end main
	
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
	} // end ejecuta
	
	/**
	 * Dibuja el tablero de juego y crea la partida inicial
	 */
	private void dibujaTablero() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		anyadeMenu();
		
		anyadeGrid(NUMFILAS, NUMCOLUMNAS);
		
		partida=new Partida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		
		disparos = partida.getDisparos();
		quedan = partida.getQuedan();
		
		anyadePanelEstado("Intentos: " + disparos + " Barcos restantes: " + quedan);
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} // end dibujaTablero
	
	/**
	 * Anyade el menu de opciones del juego
	 */
	private void anyadeMenu() {
		MenuListener e = new MenuListener();
		
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);
		
		JMenu menu = new JMenu("Opciones");
		mb.add(menu);
		
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
		
	} // end anyadeMenu

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
					casillas.add(new JLabel(vectorLetras[i],JLabel.CENTER));
					continue;
				}
				if(j==NUMCOLUMNAS+1){
					casillas.add(new JLabel(vectorLetras[i],JLabel.CENTER));
					continue;
				}
				JButton boton=new JButton();
				int [] posicion={i,j-1};
				boton.addActionListener(e);
				boton.putClientProperty("posicion",posicion);
				buttons [i][j-1]=boton;
				casillas.add(boton);
			}
		}
		frame.getContentPane().add(casillas, BorderLayout.CENTER);
		
        // POR IMPLEMENTAR		
		
		//JButton metodo Propierty para asignar propiedad al boton y saber asi en que lugar de la matriz se encuentra
	} // end anyadeGrid
	

	/**
	 * Anyade el panel de estado al tablero
	 * @param cadena	cadena inicial del panel de estado
	 */
	private void anyadePanelEstado(String cadena) {	
		JPanel panelEstado = new JPanel();
		estado = new JLabel(cadena);
		panelEstado.add(estado);
		frame.getContentPane().add(panelEstado, BorderLayout.SOUTH);
	} // end anyadePanel Estado
	
	/**
	 * Cambia la cadena mostrada en el panel de estado
	 * @param cadenaEstado	nuevo estado
	 */
	private void cambiaEstado(String cadenaEstado) {
		estado.setText(cadenaEstado);
	} // end cambiaEstado
	
	/**
	 * Muestra la solucion de la partida y marca la partida como finalizada
	 */
	private void muestraSolucion() {
		
		for(int i=0;i<NUMFILAS;i++){
			for (int j = 0; j < NUMCOLUMNAS; j++) {
				//System.out.println("hecho");
				int valor=partida.pruebaCasilla(i, j);
				if(valor==AGUA){
					buttons[i][j].setBackground(Color.blue);
				}else{
					buttons[i][j].setBackground(Color.red);
				}
				buttons[i][j].setEnabled(false);
			}
		}
		
		
	} // end muestraSolucion
	
	/**
	 * Limpia las casillas del tablero
	 */
	private void limpiaTablero() {
       for (int i = 0; i < NUMFILAS; i++) {
    	   for (int j = 0; j < NUMCOLUMNAS; j++) {
			buttons[i][j].setBackground(null);
			buttons[i][j].setEnabled(true);
    	   }	
       }
       partida=new Partida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
       
       //Actualizamos los atributos que se habrán reiniciado y actualizamos el estado.
       disparos = partida.getDisparos();
       quedan = partida.getQuedan();
       
       cambiaEstado("Intentos: " + disparos  + " Barcos restantes: " + quedan);

	} // end limpiaTablero

	
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
				System.exit(0);
				break;
			
			case "nueva":
				limpiaTablero();
				break;
			
			case "solucion":
				muestraSolucion();
				break;
				
			}
			
		} // end actionPerformed
		
	} // end class MenuListener
	

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
			
			int valor = partida.pruebaCasilla(pos[0], pos[1]);
			
			if(valor == AGUA)
				boton.setBackground(Color.blue);
			
			else if(valor == TOCADO)
				boton.setBackground(Color.yellow);
			
			else {
				//El barco se hunde.
				//Obtenemos la cadena con su información y la utilizamos para averiguar que casillas tenemos que cambiar de color.
				//Actualizamos el valor de quedan que habrá sido reducido en 1.
				
				String[] datosBarco = partida.getBarco(valor).split("#");	//Obtenemos un vector con las propiedades del Barco
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
				quedan = partida.getQuedan();
					
			}
			
			disparos = partida.getDisparos();
			
			//Si ya no quedan barcos la partida termina.
			if(quedan == 0)
				muestraSolucion();
			
			cambiaEstado("Intentos: " + disparos  + " Barcos restantes: " + quedan);
			
			//Deshabilitamos el botón para que si vuelve a ser pulsado no suceda nada.
			boton.setEnabled(false);
			
		} // end actionPerformed

		
	} // end class ButtonListener
	
	

} // end class Juego
