package gameStart;



import java.util.Scanner;

import baseClasses.Sudoku;


public class Practica2 {
	static final int[][] sudoku9x9 = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
			{ 0, 7, 0, 0, 9, 0, 2, 0, 0 }, { 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 3, 0 }, { 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
			{ 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
	static final int[][] sudoku4x4 = { { 3, 4, 1, 0 }, { 0, 2, 0, 0 }, { 0, 0, 2, 0 }, { 0, 1, 4, 3 } };

	public static void main(String[] args) {
		//Pre:non
		//Post:console interacts with the player
		//The game is fully played in the console, and every interaction will be through it

		System.out.println("Jorge Carrera:  Welcome player!");
		System.out.println("Now you have to choose !");
		boolean exit=false;
		do {
		System.out.println();
		System.out.println("1-Generate random 9x9 sudoku and play");
		System.out.println("2.Play a 4x4 Sudoku");
		System.out.println("3.Play a 9x9 Sudoku");
		System.out.println("4.Sudoku Rules");
		System.out.println("5.Exit");
		Scanner entrada = new Scanner(System.in);
		int arg = entrada.nextInt();
		Sudoku nuevo;
		switch (arg) {

		case 1:

			nuevo = new Sudoku(Practica2.generateRandomSudoku());
			nuevo.Play();
			
			break;

		case 2:
			nuevo = new Sudoku(Practica2.sudoku4x4);
			nuevo.Play();
			break;

		case 3:
			nuevo = new Sudoku(Practica2.sudoku9x9);
			nuevo.Play();
			
			break;
		case 4:
			System.out.println("When you start a game of Sudoku, some blocks will be pre-filled for you. You cannot change these numbers in the course of the game");
			System.out.println("·Each column must contain all of the numbers 1 through 9 and no two numbers in the same column of a Sudoku puzzle can be the same"); 
			System.out.println("· Each row must contain all of the numbers 1 through 9 and no two numbers in the same row of a Sudoku puzzle can be the same.");	 
			System.out.println("· Each block must contain all of the numbers 1 through 9 and no two numbers in the same block of a Sudoku puzzle can be the same.");		
				break;
				
		case 5:
			
			exit=true;
		}
		}while(!exit);

	}

	public static int[][] generateRandomSudoku( ) {
		//Pre: non
		//Post: generate a random 9x9 sudoku with solution.
		int [][] random= new int[9][9];
		Sudoku nuevo= new Sudoku(random);
		int i=0;
		
		while(i<17) {
			int value=(int) Math.floor(Math.random()*(9-1+1)+1);
			int row=(int) Math.floor(Math.random()*(8-0+0)+0);
			int column=(int) Math.floor(Math.random()*(8-0+0)+0);
			int v=row * nuevo.getNumberOfRows() + column + 1;
			if(nuevo.addNumber(row, column, value)&&nuevo.gr.validateColor(v, value)) {
				random[row][column]=value;
				i++;
			}
		
		}
		
		if(nuevo.solveSudoku()==false) {
			Practica2.generateRandomSudoku();
		}
		
		
		
		return random;
		
	}
			
			
	
	
}

