package aima.core.environment.RadikalChess;

public class Queen implements ChessPieces {
    private final String COLOR;
    private int x;
    private int y;
    private int weight;
    public Queen(String color,int x, int y) {
        COLOR = color;
        this.x = x;
        this.y = y;
        weight = 100;
    }

    @Override
    public String getType(){
        return "q";
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

    @Override
    public boolean isMoveValid(int x, int y, int[] kingpos) {
    	if(Math.abs(kingpos[0]-this.x) == Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && isInLineRight(x, y)
    			|| Math.abs(kingpos[0]-this.x) == Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && isInLineLeft(x, y)
    		    || Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) == Math.abs(kingpos[1]-y) && isInLineUp(x, y)
    		    || Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) == Math.abs(kingpos[1]-y) && isInLineDown(x, y)
    		    || Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && Math.abs(y-this.y ) == Math.abs(x-this.x))
            
            return true;

   
        return false;
    }

    @Override
    public boolean possibleCapture(int x2, int y2) {
         
    	if(Math.abs(y2-this.y ) == Math.abs(x2-this.x)) return true; 
        if(this.x >x2 && this.y == y2){
            if(isInLineVerticalUp(y2))return true;
        }
        if(this.x < x2 && this.y == y2){
            if(isInLineVerticalDown(y2))return true;
        }
        if(this.x == x2 && this.y < y2){
            if(isInLineHorizontal(x2))return true;
        }
        if(this.x == x2 && this.y > y2){
            if(isInLineHorizontal(x2))return true;
        }
  
        return false;
    } 

    private boolean isInLineVerticalUp(int y1) {
        return this.y == y1;
    }

    private boolean isInLineVerticalDown(int y1) {
        return this.y == y1;
    }

    private boolean isInLineHorizontal(int x) {    
    	return this.x == x;
    }

	@Override
	public int getWeight() {
		return weight;
	}
	
	@Override
	public Queen clone (){
		return new Queen(COLOR,x,y);
	}
}

