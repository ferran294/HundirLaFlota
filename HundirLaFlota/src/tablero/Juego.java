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
       
		// POR IMPLEMENTAR
		
		frame = new JFrame();
		frame.setVisible(true);
		anyadeMenu();
		anyadeGrid(NUMFILAS, NUMCOLUMNAS);
		partida=new Partida(NUMFILAS, NUMCOLUMNAS, NUMBARCOS);
		anyadePanelEstado("Intentos: 0   Barcos restantes: 6");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	} // end ejecuta
	
	/**
	 * Dibuja el tablero de juego y crea la partida inicial
	 */
	private void dibujaTablero() {
        // PUTA
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
		Container casillas= frame.getContentPane();
		casillas.setLayout(new GridLayout(NUMFILAS+1, NUMCOLUMNAS+2));
		String[] vectorLetras={"A","B","C","D","E","F","G","H"};
		ButtonListener e=new ButtonListener();
		buttons=new JButton[NUMFILAS][NUMCOLUMNAS];
		
		casillas.add(new JLabel(""));
		casillas.add(new JLabel("1"));
		casillas.add(new JLabel("2"));
		casillas.add(new JLabel("3"));
		casillas.add(new JLabel("4"));
		casillas.add(new JLabel("5"));
		casillas.add(new JLabel("6"));
		casillas.add(new JLabel("7"));
		casillas.add(new JLabel("8"));
		casillas.add(new JLabel(""));
		
		
		for(int i=0;i<NUMFILAS;i++){
			for(int j=0;j<NUMCOLUMNAS+2;j++){
				if(j==0){
					casillas.add(new JLabel(vectorLetras[i]));
					continue;
				}
				if(j==NUMCOLUMNAS+1){
					casillas.add(new JLabel(vectorLetras[i]));
					continue;
				}
				JButton boton=new JButton();
				int [] posicion={i,j-1};
				boton.addActionListener(e);
				boton.putClientProperty(posicion,posicion);
				buttons [i][j-1]=boton;
				casillas.add(boton);
			}
		}
		
		
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
		frame.add(panelEstado);
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
        // POR IMPLEMENTAR
	} // end muestraSolucion
	
	/**
	 * Limpia las casillas del tablero
	 */
	private void limpiaTablero() {
        // POR IMPLEMENTAR
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
			
			// POR IMPLEMENTAR	
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
	        // POR IMPLEMENTAR
		} // end actionPerformed

		
	} // end class ButtonListener
	
	

} // end class Juego
