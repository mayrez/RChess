package aima.core.environment.RadikalChess;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import aima.core.util.datastructure.XYLocation;

public class RadikalChessState implements Cloneable {
	public static final String white = "white";
	public static final String black = "black";
	public static final String EMPTY_b = "+";
	public static final String EMPTY_w = "-";
	private ChessBoard board;
	private int numRows;
    private int numCols;

    public RadikalChessState(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new ChessBoard(numRows, numCols);
    }

    public RadikalChessState(int currSize, TextArea texto) {
	}

	private String playerToMove = white; 
	private double utility = -1; 
	
    public String getPlayerToMove() {
		return playerToMove;
	}
    
    public int getNumRows(){
    	return numRows;
    }
    public int getNumCols(){
    	return numCols;
    }
    public ChessBoard getBoard(){
    	return board;
    }
    public ChessPieces[][] getboard(){
    	return board.getArray();
    }
    public boolean isEmpty(int row, int col){
    	if(board.getElement(row, col)==null) return true;
    	return false;
    }
    public double getUtility() {
		return utility;
	}
    public void setUtility(int utility){
		this.utility=utility;
	}
    public boolean isKingalive(String color){
    	int[] pos = board.getKingPosition(color);
    	if(pos[0] == -1 && pos[1] == -1)return false;
    	return true;
    }
    private boolean pieceExistsAt(int x, int y) {
       if(board.getElement(x, y) != null)return true;
		return false;
	}

	public boolean pieceExistsAt(XYLocation l) {
		return (pieceExistsAt(l.getXCoOrdinate(), l.getYCoOrdinate()));
	}
	
	public void removePieceFrom(XYLocation l, ChessPieces p) {
		ChessPieces piece = board.getElement(l.getXCoOrdinate(), l.getYCoOrdinate());
		if (piece != null && p == piece) {
			
			board.removeElement(l.getXCoOrdinate(),l.getYCoOrdinate());
		}
	}

	public void addPieceAt(XYLocation l, ChessPieces p) {
		if (isEmpty(l.getXCoOrdinate(),l.getYCoOrdinate()) || isCapture(l))
			if(p!=null)
			board.setElement(l.getXCoOrdinate(), l.getYCoOrdinate(), p) ;
	}
	public void movePiece(XYLocation from, XYLocation to) { 
		if (pieceExistsAt(from)) {
			ChessPieces p = board.getElement(from.getXCoOrdinate(),from.getYCoOrdinate());
			if (p.getColor().equals(playerToMove)) {

				if(!pieceExistsAt(to)){
					addPieceAt(to, p);
					removePieceFrom(from, p);
							
				}else if (pieceExistsAt(to) && board.getElement(to.getXCoOrdinate(),to.getYCoOrdinate()).getColor() != playerToMove){
				    if(board.getElement(to.getXCoOrdinate(),to.getYCoOrdinate()).getType()=="k"){
					    board.setKingPosition(board.getElement(to.getXCoOrdinate(),to.getYCoOrdinate()).getColor(), -1,-1);
					}
					removePieceFrom(to,board.getElement(to.getXCoOrdinate(),to.getYCoOrdinate()));	// elimina la ficha enemiga
					addPieceAt(to, p);	
					removePieceFrom(from, p);
								
				}
					   
			} 
		}
		analyzeUtility();
		playerToMove = (playerToMove == white ? black : white);
                
	}

    public boolean isAdversary (XYLocation l){
    	return isAdversary(l.getXCoOrdinate(), l.getYCoOrdinate());
    }

    public boolean isAdversary (int x, int y){
    	if(!playerToMove.equals(board.getElement(x, y).getColor()))return true;
    	return false;
    }
    
    public boolean isCapture (XYLocation l){
    	if(pieceExistsAt(l) && isAdversary(l)) return true;
    	return false;
    }
    
    public String getAdversary(String player){
    	return (player.equals(white) ? black : white);
    }
    

	public int getNumberBoardMovesfor(String player) {
        int moves=0;
		List<RadikalChessAction> actions = new ArrayList<RadikalChessAction>();
	
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (board.getElement(i,j) != null && board.getElement(i,j).getColor().equals(player)) {
					actions = getValidMoveforPiece(new XYLocation(i, j), player);
					moves= moves+ actions.size();
				}
			}
		}
	
		return moves;
	}
    
    //getValidMoveforPiece se utiliza para comprobar el número de movimientos todavía disponibles para un color
	private ArrayList<RadikalChessAction> getValidMoveforPiece(XYLocation xyLocation, String player) {
		ChessPieces piece = board.getElement(xyLocation.getXCoOrdinate(), xyLocation.getYCoOrdinate());
		ArrayList<RadikalChessAction> actions = new ArrayList<RadikalChessAction>();
		for(int i=0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(!isBlocked(xyLocation ,new XYLocation(i, j)) ){
					if(!isEmpty(i, j) && piece.possibleCapture(i, j) && board.getElement(i, j).getColor().equals(getAdversary(player))|| 
							isEmpty(i, j) && piece.isMoveValid(i, j, board.getKingPosition(getAdversary(piece.getColor())))){
						actions.add(new RadikalChessAction(xyLocation, new XYLocation(i, j)));}
				}
			}
		}
	
		return actions;
	}
	public void analyzeUtility() {
		
		//Se comprueba que el rey del jugador est� en el tablero
		if (!isKingalive(playerToMove)) {
			if (playerToMove.equals(black)) {
				utility = 1.0; //ganan blancas
			} else {
				utility = 0.0; //ganan negras
			}
		}else if (!isKingalive(getAdversary(playerToMove))) {
				if (playerToMove.equals(black)) {
					utility = 0.0; 
				} else {
					utility = 1.0; 
				}
        //se comprueba si quedan movimientos disponibles para el oponente
		}else if(isBlockedBoardfor(getAdversary(playerToMove))){
			if (playerToMove.equals(white)) {
				utility = 1.0; 
			} else {
				utility = 0.0;
			}
		}else if(isBlockedBoardfor(playerToMove)){
			if (playerToMove.equals(white)) {
				utility = 0.0; 
			} else {
				utility = 1.0;
			}
		}else if(isBlockedBoardfor(playerToMove) && isBlockedBoardfor(getAdversary(playerToMove))){
			
				utility = 0.5; //Si los dos jugadores est�n se quedan sin movimientos empatan
		
		}else{
			utility=-1;
		}
	}
	

	public double euclideanDistance(XYLocation pos, XYLocation enemyKingpos) {
		double distance = 0;
		int cateto1 = (pos.getXCoOrdinate()) - (enemyKingpos.getXCoOrdinate());
		int cateto2 = (pos.getYCoOrdinate()) - (enemyKingpos.getYCoOrdinate());
		distance = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
		return distance;
	}
	
	@Override
	public RadikalChessState clone() {

		RadikalChessState result = null;
		try {
			result = (RadikalChessState) super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		result = new RadikalChessState(numRows, numCols);
		result.board = new ChessBoard(numRows, numCols);
		for (int x = 0; x < numRows; x++) {
			for (int y = 0; y < numCols; y++) {
				if (board.getElement(x, y) != null)
					result.board.setElement(x, y, board.getElement(x, y).clone()); 
			}
		}
		return result;
	}
	

	public XYLocation[] getValidMovesFor(XYLocation pieceLocation){ 
		ArrayList<XYLocation> moves=new ArrayList<XYLocation>();
		int[] kingpos;
		int x = pieceLocation.getXCoOrdinate();
		int y= pieceLocation.getYCoOrdinate();
		ChessPieces piece = board.getElement(x, y);
		if(piece.getColor().equals(playerToMove)){
		    kingpos=board.getKingPosition(getAdversary(playerToMove));
			for(int i=0; i< numRows;i++){
				for(int j=0; j< numCols;j++){
					if(isEmpty(i, j) && !isBlocked(pieceLocation, new XYLocation(i,j) ) && piece.isMoveValid(i, j, kingpos)){
							moves.add(new XYLocation(i, j));
						
					}
				}
			}
		}
		ArrayList<XYLocation> inthreat = new ArrayList<XYLocation>();
		ArrayList<XYLocation> notthreat = new ArrayList<XYLocation>();
		for(int i=0; i<moves.size();i++){
		   if(isInthreat(moves.get(i), board.getElement(pieceLocation.getXCoOrdinate(), pieceLocation.getYCoOrdinate()))){
				inthreat.add(moves.get(i));
		   }else{
			    notthreat.add(moves.get(i));
		   }
		}
		   ArrayList<XYLocation> actions = new ArrayList<XYLocation>();
		   actions.addAll(notthreat);
		   actions.addAll(inthreat);
		
		if(actions != null)
			return actions.toArray(new XYLocation[moves.size()]);

		return null;
	}
	
	public XYLocation[] getValidMovesForKing(XYLocation pieceLocation){ 
		ArrayList<XYLocation> moves=new ArrayList<XYLocation>();
		int[] kingpos;
		int x = pieceLocation.getXCoOrdinate();
		int y= pieceLocation.getYCoOrdinate();
		ChessPieces piece = board.getElement(x, y);
		if(piece.getColor().equals(playerToMove)){
		    kingpos=board.getKingPosition(getAdversary(playerToMove));
		   
			for(int i=0; i< numRows;i++){
				for(int j=0; j< numCols;j++){
					if(!isEmpty(i, j) && !isInthreat(new XYLocation(i,j), board.getElement(x,y)) && board.getElement(i,j).getColor()!=playerToMove 
						    &&  !isBlocked(pieceLocation, new XYLocation(i,j) ) && piece.possibleCapture(i, j)	
						    || isEmpty(i, j) && !isInthreat(new XYLocation(i,j), board.getElement(x,y)) && !isBlocked(pieceLocation, new XYLocation(i,j) ) 
						    && piece.isMoveValid(i, j, kingpos) 
							
							){
							moves.add(new XYLocation(i, j));
						
					}
				}
			}
		}

		return moves.toArray(new XYLocation[moves.size()]);
	}

	private boolean isInthreat(XYLocation pieceLocation, ChessPieces p) {
		for(int i=0;i<numRows;i++){
			for(int j=0;j<numCols; j++){
				if(!isEmpty(i, j) && board.getElement(i,j).getColor().equals(getAdversary(p.getColor())) 
					&& board.getElement(i,j).possibleCapture(pieceLocation.getXCoOrdinate(),pieceLocation.getYCoOrdinate())
					&& !isBlocked(new XYLocation(i,j), pieceLocation))
					return true;
			}
		}
		return false;
	}

	public boolean isBlocked (XYLocation from, XYLocation to){
	    if(to.getXCoOrdinate() == from.getXCoOrdinate()+1 || to.getXCoOrdinate() == from.getXCoOrdinate()-1 || to.getYCoOrdinate() == from.getYCoOrdinate()+1 
			|| to.getYCoOrdinate() == from.getYCoOrdinate()-1)
	    	return false;

		if(isBlockedInColumn(from,to))
			return true;

	    if(isBlockedInRow(from,to))
	    	return true;
	
	    if(isBlockedInDiagonal(from,to))
	    	return true;
	   
			return false;
	}
		
	private boolean isBlockedInDiagonal(XYLocation from, XYLocation to) {
		if(Math.abs(from.getYCoOrdinate()-to.getYCoOrdinate()) == Math.abs(from.getXCoOrdinate()-to.getXCoOrdinate())){ 
	   	    int dif= Math.abs(from.getYCoOrdinate()-to.getYCoOrdinate());
	   	    int inix=from.getXCoOrdinate();
	       	int finx=to.getXCoOrdinate();
	       	if(inix> finx){
	       		inix=to.getXCoOrdinate();
	       		finx =from.getXCoOrdinate();
	       	}
	       	int iniy=from.getYCoOrdinate();
	       	int finy=to.getYCoOrdinate();
	       	if(iniy> finy){
	       		iniy=to.getYCoOrdinate();
	       		finy =from.getYCoOrdinate();
	       	}
	       	inix++;
	       	iniy++;
	       	while(inix<finx){
	       		if(!isEmpty(inix,iniy))return true;
	       		inix++;
	        	iniy++;
	       	}
	        	
	    }
		return false;
	}

	private boolean isBlockedInRow(XYLocation from, XYLocation to) {
		if(from.getXCoOrdinate()==to.getXCoOrdinate() && from.getYCoOrdinate()!=to.getYCoOrdinate()){
	    	int ini=from.getYCoOrdinate();
	    	int fin=to.getYCoOrdinate();
	    	if(ini> fin){
	    		ini=to.getYCoOrdinate();
	    		fin =from.getYCoOrdinate();
	    	}
	    	for(int i=ini+1; i<fin;i++){
	    		if(!isEmpty(from.getXCoOrdinate(),i))return true;
	    	}
		}
		return false;
		
	}
	
	private boolean isBlockedInColumn(XYLocation from, XYLocation to) {
		if(from.getYCoOrdinate()==to.getYCoOrdinate() && from.getXCoOrdinate()!=to.getXCoOrdinate()){
	    	int ini=from.getXCoOrdinate();
	    	int fin=to.getXCoOrdinate();
	    	if(ini> fin){
	    		ini=to.getXCoOrdinate();
	    		fin =from.getXCoOrdinate();
	    	}
	    	for(int i=ini+1; i<fin;i++){
	    		if(!isEmpty(i,from.getYCoOrdinate()))return true;
	    	}
		}
		return false;
			
	}
	
	public XYLocation[] getPossibleCaptureMove(XYLocation pieceLocation){
		ArrayList<XYLocation> moves=new ArrayList<XYLocation>();
		int x=pieceLocation.getXCoOrdinate();
		int y=pieceLocation.getYCoOrdinate();
		ChessPieces piece= board.getElement(pieceLocation.getXCoOrdinate(), pieceLocation.getYCoOrdinate());
		String adversary=getAdversary(playerToMove);
		for(int i=0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(piece.getColor().equals(playerToMove) && !isEmpty(i,j)  && isCapture(new XYLocation(i,j))   &&  piece.possibleCapture(i, j)
					&& !isBlocked(pieceLocation, new XYLocation(i,j))){
					 moves.add(new XYLocation(i, j));
				}
			}			
		}
    	XYLocation[] actions= new XYLocation[moves.size()];
		int weigth=0;
		XYLocation location=null;
		for(int i=0; i<actions.length;i++){
			for(int j=0; j<actions.length;j++){
				if(board.getElement(moves.get(j).getXCoOrdinate(), moves.get(j).getYCoOrdinate()).getWeight()>=weigth){
					weigth=board.getElement(moves.get(j).getXCoOrdinate(), moves.get(j).getYCoOrdinate()).getWeight();
					location=moves.get(j);
				}
			}
			actions[i]=location;
		}
					
		return actions;
	}	
	
	
	public boolean isJaque(XYLocation l, ChessPieces p){
		String player     = p.getColor();
		String adversary  = getAdversary(player);
		ChessPieces piece = null;

		switch(p.getType()){
			case "p": piece = new Pawn(player, l.getXCoOrdinate(), l.getYCoOrdinate());
			          break;
			case "q": piece = new Queen(player, l.getXCoOrdinate(), l.getYCoOrdinate());
	                  break;
			case "r": piece = new Rook (player, l.getXCoOrdinate(), l.getYCoOrdinate());
	                  break;
			case "b": piece = new Bishop(player, l.getXCoOrdinate(), l.getYCoOrdinate());
	                  break;
			case "k": piece = new King (player, l.getXCoOrdinate(), l.getYCoOrdinate());
		}
		
		if(piece.possibleCapture(board.getKingPosition(adversary)[0], board.getKingPosition(adversary)[1])
			&& !isBlocked(l, new XYLocation(board.getKingPosition(adversary)[0], board.getKingPosition(adversary)[1])))
			return true;
		
		return false;
	}
	
	public XYLocation[] moveForJaque(int x, int y){
		ArrayList<XYLocation> moves=new ArrayList<XYLocation>();
		ChessPieces piece = board.getElement(x, y);
		String player = piece.getColor();
		for(int i=0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(piece.getColor().equals(playerToMove)  && !isBlocked(new XYLocation(x,y), new XYLocation(i,j)) && isJaque(new XYLocation(i,j), piece) 
						&& !isInthreat(new XYLocation(i,j),piece)){
					if(!isEmpty(i,j) && piece.possibleCapture(i,j) && board.getElement(i,j).getColor().equals(getAdversary(playerToMove)) 
							|| isEmpty(i,j) && piece.isMoveValid(i,j, board.getKingPosition(getAdversary(playerToMove)))  )
					moves.add(new XYLocation(i, j));
				}
				
			}			
		}
		if(moves!=null)
			return moves.toArray(new XYLocation[moves.size()]);
		
		return null;
	}	
	
	public ArrayList<RadikalChessAction> getValidMovefor(){
		ArrayList<RadikalChessAction> actions = new ArrayList<RadikalChessAction>();

		int[] enemykingpos = board.getKingPosition(getAdversary(playerToMove));
		boolean captureKing=false;
		for(int i = 0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(!isEmpty(i,j) && board.getElement(i,j).getColor().equals(playerToMove)
						&& board.getElement(i,j).possibleCapture(enemykingpos[0],enemykingpos[1]) 
						&&	 !isBlocked(new XYLocation(i,j), new XYLocation (enemykingpos[0],enemykingpos[1]))){
					captureKing=true;
				    actions.add(new RadikalChessAction(new XYLocation(i,j), new XYLocation (enemykingpos[0],enemykingpos[1])));
				    return actions;
				}
			}
		}			
				
		if(!captureKing){
			if(isKingInthreat(playerToMove)){
				int[] kingpos = board.getKingPosition(playerToMove);
				XYLocation kingPos =new XYLocation(kingpos[0], kingpos[1]);
				XYLocation[] possibleMoves = getValidMovesForKing(kingPos);				
				for(int i=0; i< possibleMoves.length; i++){			
					actions.add(new RadikalChessAction(kingPos,possibleMoves[i]));
				}
				
			}  
			
	
			for(int i = 0; i<numRows;i++){
				for(int j=0; j<numCols;j++){
					if(!isEmpty(i,j) && board.getElement(i, j).getColor().equals(playerToMove)){
						actions.addAll(getMovesforJaque(new XYLocation(i,j)));
					}
				}
			}
				
			for(int i = 0; i<numRows;i++){
				for(int j=0; j<numCols;j++){
					if(!isEmpty(i,j) && board.getElement(i, j).getColor().equals(playerToMove)){							
			            actions.addAll(getMovesforCapture(new XYLocation(i,j)));						
					}
				}
			}				
			
			if(actions.size()==0){
	   		    for(int i = 0; i<numRows;i++){
					for(int j=0; j<numCols;j++){
					    if(!isEmpty(i,j) && board.getElement(i, j).getColor().equals(playerToMove)){
						   actions.addAll(getMovesfor(new XYLocation(i,j)));
					   }
					}
				}
			}
				
		}
		 
		return actions;
	}
	
	private ArrayList<RadikalChessAction> heaviestMoveFirst(ArrayList<RadikalChessAction> actions) {
		ArrayList<RadikalChessAction> moves = new ArrayList<RadikalChessAction>();
		int weight=0;
		RadikalChessAction a= null;
		for(int i=0; i<actions.size(); i++){
			for(int j=0; j<actions.size();j++){
				if(board.getElement(actions.get(i).getOldPosition().getXCoOrdinate(), actions.get(i).getOldPosition().getYCoOrdinate()).getWeight()>weight){
					weight = board.getElement(actions.get(i).getOldPosition().getXCoOrdinate(), actions.get(i).getOldPosition().getYCoOrdinate()).getWeight();
					a      = actions.get(i);
				} 
			}
			moves.add(a);
		}
		return moves;
	}

	public ArrayList<RadikalChessAction> getMovesfor(XYLocation currentPosition) {
		ArrayList<RadikalChessAction> moves = new ArrayList<RadikalChessAction>();
		int x = currentPosition.getXCoOrdinate();
		int y = currentPosition.getYCoOrdinate();
		ChessPieces currentPiece = board.getElement(x,y);
		XYLocation[] possibleMoves = getValidMovesFor(currentPosition);
			for(int i=0; i< possibleMoves.length; i++){			
				moves.add(new RadikalChessAction(currentPosition,possibleMoves[i]));
			}
		return moves;
	}

	public ArrayList<RadikalChessAction> getMovesforCapture(XYLocation currentPosition) {
		ArrayList<RadikalChessAction> moves = new ArrayList<RadikalChessAction>();
		int x = currentPosition.getXCoOrdinate();
		int y = currentPosition.getYCoOrdinate();
		ChessPieces currentPiece          = board.getElement(x,y);
		XYLocation[] possibleCaptureMoves = getPossibleCaptureMove(currentPosition);
		for(int i=0; i< possibleCaptureMoves.length; i++){			
			moves.add(new RadikalChessAction(currentPosition,possibleCaptureMoves[i]));
		}
		return moves;
	}

	public  ArrayList<RadikalChessAction> getMovesforJaque(XYLocation currentPosition) {		
 		ArrayList<RadikalChessAction> moves = new ArrayList<RadikalChessAction>();
		int x = currentPosition.getXCoOrdinate();
		int y = currentPosition.getYCoOrdinate();
		ChessPieces currentPiece        = board.getElement(x,y);
	    XYLocation[] possibleJaqueMoves = moveForJaque(currentPosition.getXCoOrdinate(), currentPosition.getYCoOrdinate());

		for(int i=0; i< possibleJaqueMoves.length; i++){			
			moves.add(new RadikalChessAction(currentPosition,possibleJaqueMoves[i]));
	    }

		return moves;
	}

	public String toString(){
		return board.toString();
	}
	
	public int getNumberPiecesOnBoardofColor (String player){
		int num = 0;
		for(int i=0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(!isEmpty(i,j) && board.getElement(i, j).getColor().equals(player))
				num+=1;				
			}
			
		}
		return num;
	}

	public int getPiecesWeightOnBoardofColor (String player){
		int weight = 0;
		for(int i=0; i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(!isEmpty(i,j) && board.getElement(i, j).getColor().equals(player))
				weight+=board.getElement(i, j).getWeight();				
			}
			
		}
		return weight;
	}

	public boolean isKingInthreat(String player) {
		int[] kingpos = board.getKingPosition(player);
		int kingx     = kingpos[0];
		int kingy     = kingpos[1];
		for(int i=0;i<numRows;i++){
			for(int j=0; j<numCols;j++){
				if(!isEmpty(i,j) && board.getElement(i, j).possibleCapture(kingx, kingy) && board.getElement(i, j).getColor().equals(getAdversary(player))						
					&& !isBlocked(new XYLocation(i, j), new XYLocation(kingx, kingy)))
				 
			        return true;
			}
		}
		return false;
	}	
	
	public void nextPlayerToMove() {
		playerToMove = getAdversary(playerToMove);				
	}

	public boolean isBlockedBoardfor(java.lang.String player) {
		if(getNumberBoardMovesfor(player)==0)return true;
		return false;
	}
}
