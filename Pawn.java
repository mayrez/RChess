package aima.core.environment.RadikalChess;

public class Pawn implements ChessPieces{ 
    private final String COLOR;
    private int x;
    private int y;
    private int weight;
    public Pawn(String  color, int x, int y) {
       COLOR = color;
       this.x = x;
       this.y = y;
       weight = 5;
    }  
   
    @Override
    public String getType(){
        return "p";
    }

    @Override
    public String getColor() {
        return COLOR;
    }
    public void setPositionX(int x, int y) {
        this.x=x;
        this.y=y;
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
    public boolean isMoveValid(int x, int y, int[] kingPos) {
        if(COLOR == "white"){
            if(x == this.x-1 && this.y == y){
                return true;
            }
        }else{
            if(x == this.x+1 && this.y == y){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean possibleCapture(int x, int y) {
        if(COLOR == "white"){
            if((x == this.x-1 && this.y == y + 1) ||  (x == this.x-1 && this.y == y - 1 )){
                return true;
            }
        }else{
            if((x == this.x+1 && this.y == y-1) || (x == this.x+1 && this.y == y+1)){
                return true;
            }
        }
        return false;
    }

    @Override
	public void setNewPosition(int row, int col) {
		x = row;
		y = col;
	}

	@Override
	public int getWeight() {
		return weight;
	}
	
    @Override
	public Pawn clone(){
     	return new Pawn(COLOR,x,y);
    }
}

