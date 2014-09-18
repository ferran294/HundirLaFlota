package barco;

public class Barco {
	int filaInicial, columnaInicial; // coordenadas iniciales del barco
	char orientacion; // 'H': horizontal; 'V': vertical;
	int tamanyo; // numero de casillas que ocupa
	int tocadas; // numero de casillas tocadas
	
	Barco (int filaInicial, int columnaInicial, char orientacion, int tamanyo) {
		this.filaInicial = filaInicial;
		this.columnaInicial = columnaInicial;
		this.orientacion = orientacion;
		this.tamanyo = tamanyo;
	}

	public int getFilaInicial() {
		return filaInicial;
	}

	public void setFilaInicial(int filaInicial) {
		this.filaInicial = filaInicial;
	}

	public int getColumnaInicial() {
		return columnaInicial;
	}

	public void setColumnaInicial(int columnaInicial) {
		this.columnaInicial = columnaInicial;
	}

	public char getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(char orientacion) {
		this.orientacion = orientacion;
	}

	public int getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}

	public int getTocadas() {
		return tocadas;
	}

	public void setTocadas(int tocadas) {
		this.tocadas = tocadas;
	}
	
	public void tocaBarco() {
		//TODO incrementa el numero de casillas tocadas
	}
}
