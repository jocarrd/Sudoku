package baseClasses;

import java.util.List;
import java.util.Scanner;

public class Sudoku {
	private int[][] start, matrix;
	public colorGraph<Integer> gr;

	public Sudoku(int[][] MatrixIni) {
		//Pre:non
		//Post: initialize Sudoku and build the start Graph
		this.start = MatrixIni;
		this.matrix = this.copyMatrix(MatrixIni);
		this.buildStartGraph();

	}

	public int getNumberOfRows() {
		//Pre: Sudoku must have been initialized
		//Post: return number of rows of start(Matrix)
		return this.start.length;
	}

	public boolean addNumber(int row, int column, int value) {
		//Pre: Sudoku must have been initialized
		//Post: add a value to sudoku [row][column] field
		
		if (this.start[row][column] == 0 && this.matrix[row][column] == 0 && value<=this.getNumberOfRows()) {

			int v = row * this.getNumberOfRows() + column + 1;
			this.gr.addColorToVertex(v, value);

			// Reset the value of the field
			this.matrix[row][column] = value;
			return true;
		}

		return false;
	}

	public boolean deleteNumber(int row, int column) {
		//Pre: Sudoku must have been initialized
		//Post: delete a number from sudoku [row][column] field
		if (this.start[row][column] == 0) {

			int v = row * this.getNumberOfRows() + column + 1;
			this.gr.deleteColorOfVertex(v);
			// Reset the value of the field
			this.matrix[row][column] = this.start[row][column];
			return true;
		}
		return false;
	}

	public int initialValue(int row, int column) {
		//Pre: Sudoku must have been initialized
		//Post: return the initialValue of Sudoku in the [row][column] field
		return this.start[row][column];
	}

	public int[][] copyMatrix(int[][] toCopy) {
		//Pre: toCopy must have been initialized
		//Post: copy the matrix
		int[][] Acopy = new int[this.getNumberOfRows()][this.getNumberOfRows()];
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfRows(); j++) {
				Acopy[i][j] = toCopy[i][j];

			}

		}

		return Acopy;
	}

	public void resetSudoku() {
		//Pre: Sudoku must have been initialized
		//Post: reset sudoku to initial state
		for (int row = 0; row < this.getNumberOfRows(); row++) {
			for (int column = 0; column < this.getNumberOfRows(); column++) {
				if (this.start[row][column] != this.matrix[row][column]) {
					int v = row * this.getNumberOfRows() + column + 1;
					this.gr.deleteColorOfVertex(v);
				}
			}
		}
		this.matrix = this.copyMatrix(this.start);
	}

	public boolean isCorrectToAdd(int row, int column, int value) {
		//Pre: Sudoku must have been initialized
		//Post: return true if can add a number in [row][column] field , false in other case.
		List<Integer> adyacentes = this.gr.listAdjacentOfVertex(row * this.getNumberOfRows() + column + 1);
		for (Integer i : adyacentes) {
			if (this.gr.informationAboutColorOfVertex(i)==value) {
				return false;
			}
		}
		return true;

	}

	public boolean solveSudoku() {
		//Pre: Sudoku must have been initialized
		//Post: Solve sudoku
		try {
		this.gr.colorear(this.getNumberOfRows());
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfRows(); j++) {
				this.matrix[i][j] = this.gr.informationAboutColorOfVertex(i * this.getNumberOfRows() + j + 1);
			}
		}
		return true;
		
		
		}
		catch(NullPointerException a) {
			return false;
		}
	}

	private void buildStartGraph() {
		this.gr = new colorGraph<>();
		// gr es el nombre del atributo de Sudoku que contiene el grafo con colores
		int numFilas = this.getNumberOfRows();
		int numVertices = numFilas * numFilas;
		for (int v = 1; v <= numVertices; v++)
			this.gr.addVertex(v);

		// Añado aristas para todas las parejas de vértices que están en la misma fila
		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numFilas; j++) {
				for (int k = j + 1; k < numFilas; k++) {
					int v1 = numFilas * i + j + 1;
					int v2 = numFilas * i + k + 1;
					this.gr.addEdge(v1, v2);
				}
			}
		}

		// Añado aristas para todas las parejas de vértices que están en la misma
		// columna
		for (int j = 0; j < numFilas; j++) {
			for (int i = 0; i < numFilas; i++) {
				for (int k = i + 1; k < numFilas; k++) {
					int v1 = numFilas * i + j + 1;
					int v2 = numFilas * k + j + 1;
					this.gr.addEdge(v1, v2);
				}
			}
		}

		// Añado aristas para todas las parejas de vértices que están en la misma región
		int n = (int) Math.sqrt(numFilas);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int i0 = i * n;
				int j0 = j * n;
				// (i0,j0) es la esquina superior izquierda de la región
				for (int i1 = i0; i1 < i0 + n; i1++) {
					for (int j1 = j0; j1 < j0 + n; j1++) {
						for (int i2 = i0; i2 < i0 + n; i2++) {
							for (int j2 = j0; j2 < j0 + n; j2++) {
								int v1 = numFilas * i1 + j1 + 1;
								int v2 = numFilas * i2 + j2 + 1;
								if ((v2 != v1) && !this.gr.areAdjacent(v1, v2))
									this.gr.addEdge(v1, v2);
							}
						}
					}
				}
			}
		}

		// Por último añado los colores a los vértices correspondientes a los
		// valores iniciales del sudoku
		for (int i = 0; i < numFilas; i++) {
			for (int j = 0; j < numFilas; j++) {
				if (this.initialValue(i, j) != 0)
					this.gr.addColorToVertex(i * numFilas + j + 1, this.initialValue(i, j));
			}
		}
	}

	public void showSudoku() {
		//Pre: Sudoku must have been initialized
		//Post: show sudoku state
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			System.out.println();
			System.out.print("|");
			for (int j = 0; j < this.getNumberOfRows(); j++) {
				System.out.print(" "+this.matrix[i][j] +" "+ "|");
			}
		}

	}

	public boolean finish() {
		//Pre: Sudoku must have been initialized
		//Post:  Return true if sudoku has been solved false in other case
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			for (int j = 0; j < this.getNumberOfRows(); j++) {
				if (this.matrix[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isOkSudoku() {
		//Pre: Sudoku must have been initialized
		//Post: return true if sudoku solution is correct , false in other case
		for(int i=0;i<this.getNumberOfRows();i++) {
			for(int j=0;j<this.getNumberOfRows();j++) {
				List<Integer> adyacentes= this.gr.listAdjacentOfVertex(i * this.getNumberOfRows() + j+ 1);
				for(Integer t : adyacentes) {
					if(this.gr.informationAboutColorOfVertex(t)==null) {
						return false;
					}
					
					if(this.gr.informationAboutColorOfVertex(t)==this.matrix[i][j]) {
						return false;
					}
				}
				
			}
		}
		return true;
	}
	
	

	public void Play() {
		//Pre: Sudoku must have been initialized.
		//Post: Allows a user to solve a sudoku.
		boolean finish = this.finish();
		while (!finish) {
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
			this.showSudoku();
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
			System.out.println();
		
			Scanner entrada = new Scanner(System.in);
			System.out.println("1- AddNumber");
			System.out.println("2-DeleteNumber");
			System.out.println("3- Solve Sudoku");
			int c= entrada.nextInt();
			if(c==1) {
			System.out.println("Row: ");
			int row = entrada.nextInt() - 1;
			System.out.println("Column: ");
			int column = entrada.nextInt() - 1;
			System.out.println("Value: ");
			int value = entrada.nextInt();
			
			if(this.addNumber(row, column, value)==true) {
				System.out.println("----------------------------------------------------------------------------------------------------------------------");
				System.out.println("The number was added");
			}else {
				System.out.println("----------------------------------------------------------------------------------------------------------------------");
				System.out.println("You cant add the number");
			}
			}
			if(c==2) {
				System.out.println("Row: ");
				int row = entrada.nextInt() - 1;
				System.out.println("Column: ");
				int column = entrada.nextInt() - 1;
				
				if(this.deleteNumber(row, column)) {
					System.out.println("----------------------------------------------------------------------------------------------------------------------");
					System.out.println("The number was deleted");
				}else {
					System.out.println("----------------------------------------------------------------------------------------------------------------------");
					System.out.println("You cant delete the number");
				}
				
				
			}
			if(c==3) {
				System.out.println("Wait.........");
				this.solveSudoku();
				this.showSudoku();
				System.out.println();
				System.out.println();
				System.out.println("----------------------------------------------------------------------------------------------------------------------");
				System.out.println("You just cheated :(");
				System.out.println("----------------------------------------------------------------------------------------------------------------------");
				return;
			}
			finish = this.finish();
		}
		if(this.isOkSudoku() ) {
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
			System.out.println("Good job");
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
		}else {
			System.out.println("Try again");
		}
		
	}
	

}
