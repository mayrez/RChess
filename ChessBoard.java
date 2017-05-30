package aima.core.environment.RadikalChess;

public class ChessBoard {
    private ChessPieces[][] board;
    private int [] bKingPos;
    private int [] wKingPos;
    private int numRows;
    private int numCols;
    public ChessBoard(int numRows, int numCols) {
        bKingPos     = new int[2];
        wKingPos     = new int[2];
    	  this.numRows = numRows;
        this.numCols = numCols;
    	  board        = new ChessPieces[numRows][numCols];
        inicializeBlack("black");
        inicializeWhite("white", numRows-1);    
    }


    private void inicializeBlack(String color) {
        inicializeFirstline(color,  0);
        inicializeSecondline(color, 1);
    }
    
    private void inicializeWhite(String color, int line) {
        inicializeFirstline(color, line);
        inicializeSecondline(color,  line-1);
    }

    private void inicializeFirstline(String color, int line) {
        if(color.equals("black")){
            board [line][0] = new King(color, line,0);
            bKingPos [0]    = 0;
            bKingPos [1]    = 0;
            board [line][1] = new Queen(color, line,1);
            board [line][2] = new Bishop(color,line,2);
            board [line][3] = new Rook (color, line,3);                                    
      
        }else{
            board [line][0] = new Rook (color, line,0);
            board [line][1] = new Bishop(color, line,1);
            board [line][2] = new Queen (color, line,2);
            board [line][3] = new King (color, line,3);   
            wKingPos [0]    = 5;
            wKingPos [1]    = 3;                                                 
                
        }
            
    }  
 
    private void inicializeSecondline(String color,  int line) {
        for (int j = 0; j < numCols; j++) {
            board[line][j] = new Pawn(color, line,j);
        }    
    }
    
    public int[] getKingPosition (String color){
        if(color.equals("black")) return bKingPos;
        return wKingPos;
    }
    
    public void setKingPosition (String color, int x, int y){
    	  if(color.equals("white")){
    		    wKingPos[0]=x; wKingPos[1]=y;
    	  }else {
    		    bKingPos[0]=x; bKingPos[1]=y;
		    }
    }

    public ChessPieces getElement (int row, int col){
    	  if(row < numRows && col < numCols && row >=0 && col>=0)
            return board [row][col];
    	  return null;
    }
    
    public void setElement (int row, int col, ChessPieces p){
    	  board[row][col] = p;
    	  board[row][col].setNewPosition(row,col);
    	  if(p.getType() == "k") setKingPosition(p.getColor(), row,col);
    }
    
    public void removeElement (int row, int col){
    	  board[row][col]=null;
    }   

    public ChessPieces[][] getArray() {
        return board;
    }
   
    @Override
    public String toString (){
        String table ="";
        table+="    0   1   2   3   \n";
        for (int i = 0; i < numRows; i++) {
        	  table+=" "+i+" ";
            for (int j = 0; j < numCols; j++) {
                if(board[i][j] != null){
                    if(board[i][j].getColor().equals("white")){table +=" w";}else{table+=" b";}
                    table += board[i][j].getType()+" ";
                }else{
                    if((i==0 && j==0) || (i==0 && j==2) || (i==1 && j==1) || (i==1 && j==3) || (i==2 && j==0) 
                           || (i==2 && j==2) || (i==3 && j==1) || (i==3 && j==3) || (i==4 && j==0) || (i==4 && j==2) || (i==5 && j==1) || (i==5 && j==3)){
                        table+=" +  ";
                    } else{
                        table+=" -  ";
                    }
                }
            }
            table+="\n";
        }
        return table;
    }
}