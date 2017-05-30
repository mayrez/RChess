package aima.core.environment.RadikalChess;


public class King implements ChessPieces{
	    private final String COLOR;
	    private int x;
	    private int y;
	    private int weight;
	    public King(String color,int x, int y) { 
	        COLOR = color;
	        this.x = x;
	        this.y = y;
	        weight = 1000;
	    }
	 
	   

	    @Override
	    public String getType(){
	        return "k";
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
	    public boolean isMoveValid(int x, int y, int[] kingpos) {
	    	if(Math.abs(this.x-kingpos[0]) > Math.abs(x-kingpos[0]) && x==this.x+1 && this.y==y
	    	|| Math.abs(this.y-kingpos[1]) > Math.abs(y-kingpos[1]) && Math.abs(this.x - kingpos[0])> Math.abs(x - kingpos[0]) && x == this.x+1 && y == this.y - 1
	    	|| Math.abs(this.y - kingpos[1]) > Math.abs(y - kingpos[1]) && Math.abs(this.x - kingpos[0])>Math.abs(x - kingpos[0]) && x == this.x+1 && y == this.y + 1
	    	|| Math.abs(this.x - kingpos[0]) > Math.abs(x - kingpos[0]) && x == this.x-1 && this.y==y
	    	|| Math.abs(this.y - kingpos[1]) > Math.abs(y - kingpos[1]) && Math.abs(this.x - kingpos[0]) > Math.abs(x - kingpos[0]) && x == this.x-1 && y == this.y - 1
	    	|| Math.abs(this.y - kingpos[1]) > Math.abs(y - kingpos[1])	&&	Math.abs(this.x - kingpos[0]) > Math.abs(x - kingpos[0]) && x == this.x-1 && y == this.y + 1
	    	|| Math.abs(this.y - kingpos[1]) > Math.abs(y - kingpos[1])	&& x==this.x && y == this.y + 1
	    	|| Math.abs(this.y - kingpos[1]) > Math.abs(y - kingpos[1])	&& x==this.x &&  y == this.y - 1 
	    	){
	    		return true;
	    	}
                
	  
	        return false;
	    }

	    @Override
	    public boolean possibleCapture(int x, int y) {
	        if(this.x==x+1 && this.y==y) return true;
	        if(this.x==x-1 && this.y==y) return true;
	        if(this.y==y+1 && this.x==x) return true;
	        if(this.y==y-1 && this.x==x) return true;
	        if(this.x==x+1 && this.y==y-1) return true;
	        if(this.x==x+1 && this.y==y+1) return true; 
            if(this.x==x-1 && this.y==y-1) return true;
            if(this.x==x-1 && this.y==y+1) return true;
	        return false;
	    }



		@Override
		public void setNewPosition(int row, int col) {
			x = row;
			y = col;
		}
        
		@Override
		public King clone (){
			return new King(COLOR,x,y);
		}

		@Override
		public int getWeight() {
			return weight;
		}

	   
	}


