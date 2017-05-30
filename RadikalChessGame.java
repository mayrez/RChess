package aima.core.environment.RadikalChess;

import aima.core.search.adversarial.Game;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.List;


public class RadikalChessGame implements Game<RadikalChessState,RadikalChessAction,String>{
	RadikalChessState initialState;
	public RadikalChessGame (int numRows, int numCols){
		initialState = new RadikalChessState(numRows, numCols);
		
	}

	public RadikalChessGame(TextArea texto, int numRows, int numCols) {
		initialState = new RadikalChessState(numRows, numCols);
	}

	@Override
	public RadikalChessState getInitialState() {
		return initialState;
	}
	@Override
	public String[] getPlayers() {
		return new String[] {RadikalChessState.white, RadikalChessState.black};
	}

	@Override
	public String getPlayer(RadikalChessState state) {
		return state.getPlayerToMove();
	}
	
	@Override
	public List<RadikalChessAction> getActions(RadikalChessState state) {
		return state.getValidMovefor();
	}    
	
	public RadikalChessState getResultTestMove(RadikalChessState state,	RadikalChessAction action) {
		RadikalChessState result = state.clone();
		result.movePiece(action.getOldPosition(), action.getNewPosition());
		return result;
	}

	@Override
	public RadikalChessState getResult(RadikalChessState state,	RadikalChessAction action) {
		state.movePiece(action.getOldPosition(), action.getNewPosition());
		return state;
	}

	@Override
	public boolean isTerminal(RadikalChessState state) {
        return state.getUtility() != -1;
	}

	@Override
	public double getUtility(RadikalChessState state, String player) {
		// TODO Auto-generated method stub
		double result = state.getUtility();
		//if (result != -1) {
			return result;
		//} else {
		//	throw new IllegalArgumentException("State is not terminal.");
		//}
	}

    public String toString(){
	    return initialState.toString();
    }


}

