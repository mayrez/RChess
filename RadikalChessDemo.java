package aima.core.environment.RadikalChess;


import java.util.ArrayList;
import java.util.Scanner;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;

public class RadikalChessDemo {

	public static void main(String[] args) {
		int scoreWhite=0;
		int scoreBlack=0;
		int tablas =0;
		int timejugada =0;
		int timejuego=0;
		int jugadas=0;
		int nodos=0;
		//for(int i=0; i<10; i++){
			RadikalChessGame game= new RadikalChessGame(6, 4);
			RadikalChessState currState = game.getInitialState();
			RadikalChessAction action;
			System.out.print(game.toString());
			H1MiniMaxPlayer<RadikalChessState, RadikalChessAction, String> player1 = new H1MiniMaxPlayer<>(game);
			H2MiniMaxPlayer<RadikalChessState, RadikalChessAction, String> player2 = new H2MiniMaxPlayer<>(game);
			long timeini=0;
			long totalTime=0;
			long timeini2=0;
			long totalTime2=0;
			//timejuego=0;
			//timejugada=0;
			timeini2 = System.currentTimeMillis();
			//Alternancia de juagadores
			while (!game.isTerminal(currState)) {			
				System.out.println(game.getPlayer(currState) + "  playing ... ");
				//action = player1.makeDecision(currState);
				if(game.getPlayer(currState).equals("white")){
					timeini = System.currentTimeMillis();
					//Jugador máquina 1
					action = player1.makeDecision(currState);
					nodos+=player1.getNodos();
					//action = player2.makeDecision(currState);
					//turno persona1
					//action = getAction(currState);
					totalTime = System.currentTimeMillis() - timeini;
					timejugada+=totalTime;
				}else{
					timeini = System.currentTimeMillis();
					//Jugador máquina2
					action = player1.makeDecision(currState);
					nodos+=player1.getNodos();
					//action = player1.makeDecision(currState);
					//turno persona2
					//action = getAction(currState);
					totalTime = System.currentTimeMillis() - timeini;
					timejugada+=totalTime;
				}
				//action = player1.makeDecision(currState);
				//action = player2.makeDecision(currState);
				jugadas++;
				currState = game.getResult(currState, action);
				System.out.println(currState);
				//currState.nextPlayerToMove();
			}
			totalTime2 = System.currentTimeMillis() - timeini2;
			timejuego+=totalTime2;
			if(currState.getUtility()==1.0){
				scoreWhite++;
				System.out.println("Gana  Blancas");
			}else if(currState.getUtility()==0){
				scoreBlack++;
				System.out.println("Gana  Negras");
			}else{
				tablas++;
				System.out.println("Empate");
			}
	//}
		System.out.println("En 10 partidas jugadas:");
		System.out.println("Se han expandido de media "+nodos/10 +" nodos");
		System.out.println("Blancas han ganado "+ scoreWhite +" partidas");
		System.out.println("Negras han ganado "+ scoreBlack +" partidas");
		System.out.println("Y se han empatado "+ tablas +" partidas");
		System.out.println("Número medio de jugadas "+ jugadas/10);
		System.out.println("Tiempo medio por cada jugada "+ timejugada/jugadas +" ms");
		System.out.println("Tiempo medio por partida "+ timejuego/10 +" ms");
	}

	private static RadikalChessAction getAction(RadikalChessState currState) {
		try {
			RadikalChessAction action;
			
			while(true){
				System.out.println("Juega "+currState.getPlayerToMove());
				System.out.print("your move (format srcRow srcCol targetRow targetCol): ");
				Scanner sc = new Scanner(System.in);
				System.out.print(" Source Row: ");
				int srcRow = sc.nextInt();
				System.out.print(" Source Column: ");
				int srcCol = sc.nextInt();
				System.out.print(" Target Row: ");
				int targetRow = sc.nextInt();
				System.out.print(" Target Column: ");
				int targetCol = sc.nextInt();
			 if(srcRow < currState.getNumRows() && srcCol< currState.getNumCols() && targetRow<currState.getNumRows()
					 && targetCol<currState.getNumCols()){
				 
				 action= handleMove(srcRow,srcCol,targetRow,targetCol,currState);
				 if(action!=null)return action;
			 }
			 
			 System.out.println(currState.toString());
			 //System.out.println(game.toString());
			}
		} catch (Exception e) {
			System.out.println(e.getClass() + ": " + e.getMessage());
		}
		return null;
	}

	private static RadikalChessAction handleMove(int srcRow, int srcCol, int targetRow,
		int targetCol, RadikalChessState currState) {
		ChessBoard board = currState.getBoard();
		XYLocation oldPosition = new XYLocation(srcRow, srcCol);
		XYLocation newPosition = new XYLocation(targetRow, targetCol);
		boolean valid= false;
		ChessPieces piece = board.getElement(srcRow, srcCol);
		XYLocation[] jaque= currState.moveForJaque(srcRow, srcCol);
		for(int i=0; i<jaque.length;i++){
			if(jaque[i].getXCoOrdinate()==targetRow && jaque[i].getYCoOrdinate()==targetCol){
			valid=true;
			break;
			}
		}
		XYLocation[] c = currState.getPossibleCaptureMove(oldPosition);
		if(!valid){
		for(int i=0; i<c.length;i++){
			if(c[i].getXCoOrdinate()==targetRow && c[i].getYCoOrdinate()==targetCol){
			valid=true;
			break;
			}
		}
		}
		if(!valid){
			XYLocation[] l= currState.getValidMovesFor(oldPosition);
			for(int i=0; i<l.length;i++){
				if(l[i].getXCoOrdinate()==targetRow && l[i].getYCoOrdinate()==targetCol){
				valid=true;
				break;
				}
			}
		}
		if(valid){
			System.out.println("Movimiento válido");
			RadikalChessAction action=  new RadikalChessAction(oldPosition, newPosition);
			return action;
		}else{
			System.out.println("Movimiento no válido");
			//Se vuelve a preguntar el movimiento
			 
			return null;
		}

	}

}
