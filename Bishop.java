package aima.core.environment.RadikalChess;

public class Bishop implements ChessPieces {

	private final String COLOR;
	private int x;
	private int y;
	private int weight;
	
	public Bishop(String  color, int x, int y) {
	    COLOR = color;
	    this.x = x;
	    this.y = y;
	    weight = 20;
	}
	    
	   
	@Override
	public boolean isMoveValid(int x, int y, int[] kingpos) {
	    
	if(Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && Math.abs(y-this.y ) == Math.abs(x-this.x))return true;
	    return false;
	}

	@Override
	public boolean possibleCapture(int x, int y) {  
	  	if(Math.abs(y-this.y ) == Math.abs(x-this.x))return true;
	    return false;
	}
	    

    @Override
    public String getType(){
        return "b";
    }

    @Override
    public String getColor() {
        return COLOR;
    }

    @Override
    public int getPositionX() {
        return x;
    }

    @Override
    public int getPositionY() {
        return y;
    }

    @Override
	public void setNewPosition(int row, int col) {
		x = row;
		y = col;
	}
	    
	private boolean isDiagonalUpLeft(int x, int y) {
	    int i = this.x;
	    int j = this.y;             
	    while(i>=x && j>=y){
	        if(i == x && j == y) return true;
	        i--;
	        j--;
	    }
	    return false;
	}	   

	private boolean isDiagonalDownRight(int x, int y) {
	    int i = this.x;
	    int j = this.y;
	    while(i <= x && j <= y){
	        if(i == x && j == y) return true;
	        i++;
	        j++;
	    }
	    return false;
	}
	    
	private boolean isDiagonalDownLeft(int x, int y) {
	    int i = this.x;
	    int j = this.y;             
	    while(i<=x && j>=y){
	        if(i == x && j == y) return true;
	        i++;
	        j--;
	    }
	    return false;
	}

	private boolean isDiagonalUpRigth(int x, int y) {
	    int i = this.x;
	    int j = this.y;             
	    while(i>=x && j<=y){
	       if(i == x && j == y) return true;
	       i--;
	       j++;
	    }
	    return false;
	}
	
	@Override
	public int getWeight() {
		return weight;
	}
	   
    public Bishop clone(){
       	return new Bishop(COLOR,x,y);
    }
}
