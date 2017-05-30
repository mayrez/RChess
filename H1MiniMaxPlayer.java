package aima.core.environment.RadikalChess;

//import aima.core.search.adversarial.AdversarialSearch;
import java.util.ArrayList;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;
import aima.core.util.datastructure.XYLocation;


public class H1MiniMaxPlayer <RadikalChessState, RadikalChessAction, String> implements AdversarialSearch<aima.core.environment.RadikalChess.RadikalChessState, aima.core.environment.RadikalChess.RadikalChessAction>{

	private RadikalChessGame game;
	private int expandedNodes;
	private int profundidad;
	private String currPlayer;
	public H1MiniMaxPlayer(RadikalChessGame game) {
		this.game = game;
	}

	public int getProfundidad(){
		return profundidad;
	}
	public void modProfundidad(){
		profundidad--;
	}

	public aima.core.environment.RadikalChess.RadikalChessAction makeDecision(aima.core.environment.RadikalChess.RadikalChessState state) {
		int maxDepth  = (int) (Math.random()*4+1);
		expandedNodes = 0;	
		aima.core.environment.RadikalChess.RadikalChessAction result = null;
		double resultValue      = Double.NEGATIVE_INFINITY;
		java.lang.String player =  game.getPlayer(state);
		currPlayer=(String) player;
		for (aima.core.environment.RadikalChess.RadikalChessAction action : game.getActions(state)) {
			double value = minValue(game.getResultTestMove(state, action), player, maxDepth);
			if (value > resultValue) {
				result = (aima.core.environment.RadikalChess.RadikalChessAction) action;
				resultValue = value;
			} if(result==null) result= (aima.core.environment.RadikalChess.RadikalChessAction) action;
		}
		return result;
	}

	public double minValue(aima.core.environment.RadikalChess.RadikalChessState state, java.lang.String player, int maxDepth) { 
		expandedNodes++;
		if (game.isTerminal(state) || maxDepth<=0)
			return evaluate(state,player);
		double value = Double.POSITIVE_INFINITY;
		for (aima.core.environment.RadikalChess.RadikalChessAction action : game.getActions(state)){
			value = Math.min(value,
			maxValue(game.getResultTestMove(state, action), player, maxDepth));
		}
		return value;
	}
	public double maxValue(aima.core.environment.RadikalChess.RadikalChessState state, java.lang.String player, int maxDepth) { 									
		expandedNodes++;
		if (game.isTerminal(state) || maxDepth<=0)
			return evaluate(state,player);
			double value = Double.NEGATIVE_INFINITY;
		for (aima.core.environment.RadikalChess.RadikalChessAction action : game.getActions(state)){
			value = Math.max(value,
			minValue(game.getResultTestMove(state, action), player, maxDepth-1));
		}
		return value;
	}



	private double evaluate(aima.core.environment.RadikalChess.RadikalChessState state,java.lang.String player) {
	    Double value=0.0;
	    if(!state.isKingalive((java.lang.String) currPlayer)) {
			return Double.NEGATIVE_INFINITY;
	    }
	    if(!state.isKingalive(state.getAdversary((java.lang.String) currPlayer))) {
		    return Double.POSITIVE_INFINITY;
	    }
	    if(state.isBlockedBoardfor((java.lang.String) currPlayer)){
		    return Double.NEGATIVE_INFINITY;
	    }
	    if(state.isBlockedBoardfor(state.getAdversary((java.lang.String) currPlayer))){
		    return Double.POSITIVE_INFINITY;
	    }
	    if(state.isKingInthreat((java.lang.String) currPlayer)) {
		    return -100000000;
	    }
	  
	    if(state.isKingInthreat(state.getAdversary((java.lang.String) currPlayer))){
		    return 100000000;
	    }


	    //Devuelve valor aleatorio
	    // if(expandedNodes%3 ==0)return Math.random()*10000+-10000;
	   
	    if(state.getPiecesWeightOnBoardofColor((java.lang.String) currPlayer)>=state.getPiecesWeightOnBoardofColor(state.getAdversary((java.lang.String) currPlayer))){
		    value+= 4500;	   
	    }else{
		    value+= -4500;
	    }

	    double maxCounter=0;
		double minCounter=0;
		XYLocation idealPosition= new XYLocation ((state.getNumRows()/2)-1,(state.getNumCols()/2)-1);
		for(int i=0;i<state.getNumRows();i++){
			for(int j=0;j<state.getNumCols();j++){
			    if (state.pieceExistsAt(new XYLocation(i, j))){
				    if (state.getBoard().getElement(i,j).getColor().equals((java.lang.String) currPlayer)){// hay una pieza que se ha acercado al centro
					    maxCounter+= state.euclideanDistance(new XYLocation(i, j), idealPosition);
				    }else {
				   	    minCounter+= state.euclideanDistance(new XYLocation(i, j), idealPosition);
				    }			   	
			    } 
	        }
		}
		maxCounter=(maxCounter/(state.getNumberPiecesOnBoardofColor((java.lang.String) currPlayer)+1));  
		minCounter=(minCounter/(state.getNumberPiecesOnBoardofColor(state.getAdversary((java.lang.String) currPlayer))+1)); 
		if (player.equals((java.lang.String) currPlayer)) 
			return value+=state.getNumCols()*state.getNumCols()-maxCounter;
		else 
			return value+=-(state.getNumCols()*state.getNumCols()-minCounter); 
	}	

	public Metrics getMetrics() {
		Metrics result = new Metrics();
			result.set("expandedNodes", expandedNodes);
		return result;
	}
	public int getNodos (){
	    return expandedNodes;
    }
}