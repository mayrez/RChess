package aima.core.environment.RadikalChess;

public class Rook implements ChessPieces {

    private final String COLOR;
    private int x;
    private int y;
    private int weight;
    public Rook(String  color, int x, int y) { 
       COLOR = color;
       this.x = x;
       this.y = y;
       weight = 30;
    }

    @Override
	public boolean isMoveValid(int x, int y, int[] kingpos) {
	    if(Math.abs(kingpos[0]-this.x) == Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && isInLineRight(x, y)
		    || Math.abs(kingpos[0]-this.x) == Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) > Math.abs(kingpos[1]-y) && isInLineLeft(x, y)
		    || Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) == Math.abs(kingpos[1]-y) && isInLineUp(x, y)
		    ||  Math.abs(kingpos[0]-this.x) > Math.abs(kingpos[0] -x) && Math.abs(kingpos[1]-this.y) == Math.abs(kingpos[1]-y) && isInLineDown(x, y))

		    return true;
	     
	    return false;
	}

	@Override
	public boolean possibleCapture(int x, int y) {
	    if(this.x >x && this.y == y){
	        if(isInLineUp(x,y))return true;
	    }
	    if(this.x < x && this.y == y){
	        if(isInLineDown(x,y))return true;
	    }
	    if(this.x == x && this.y < y){
	        if(isInLineRight(x,y))return true;
	    }
	    if(this.x == x && this.y > y){
	        if(isInLineLeft(x,y))return true;
	    }
	
	    return false;
	}

	@Override
	public String getType(){
	    return "r";
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
	    
	private boolean isInLineUp(int x, int y) {
	    int i=this.x;
	    while(i>=x){
	        if(i==x && this.y==y)return true;
	        i--;
	    }
	    return false;
	}

	private boolean isInLineDown(int x1, int y1) {
	    int i=this.x;
	    while(i<=x1){
	        if(i==x1 && this.y==y1)return true;
	        i++;
	    }
	    return false;
	}

	private boolean isInLineRight(int x, int y) {
	    int j=this.y;
	    while(j<=y){
	       if(j==y && this.x==x)return true;
	       j++;
	    }
	    return  false;
    }

    private boolean isInLineLeft(int x1, int y1) {
	    int j=this.y;
	    while(j>=y1){
	       if(j==y1 && this.x==x1)return true;
	       j--;
	    }
	    return false;
    }

	@Override
	public int getWeight() {
		return weight;
	}

	public Rook clone (){
		return new Rook(COLOR,x,y);
	}

}
