package aima.core.environment.RadikalChess;

public interface ChessPieces { 
    public String getColor();
    public String getType();
    public int getWeight();
    public int getPositionX();
    public int getPositionY();
    public boolean isMoveValid(int x, int y, int[] kingpos);
    public boolean possibleCapture(int x, int y);
	public void setNewPosition(int row, int col);
	public ChessPieces clone();
    }
