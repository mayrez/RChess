package aima.core.environment.RadikalChess;
import aima.core.util.datastructure.XYLocation;

public class RadikalChessAction {
	private XYLocation oldPosition;
	private XYLocation newPosition;
	
	public RadikalChessAction(XYLocation oldPosition, XYLocation newPosition) {
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	public XYLocation getOldPosition() {
		return oldPosition;
	}
	public void setOldPosition(XYLocation oldPosition) {
		this.oldPosition = oldPosition;
	}
	public XYLocation getNewPosition() {
		return newPosition;
	}
	public void setNewPosition(XYLocation newPosition) {
		this.newPosition = newPosition;
	}
}
