package org.martin.scene;

import java.util.*;

public class GameAction {

	private enum ActionType {
		MOVEBY,
		MOVETO, 
		ROTATEBY,
		ROTATETO,
		SCALETO,
		SCALEBY,
		SCALEXTO,
		SCALEXBY,
		SCALEYTO,
		SCALEYBY
	}
	
	GameObject object;
	
	static ArrayList<GameAction> actionPool = new ArrayList<GameAction>();
	
	public GameAction() {
		
	}

	private void updateAction() {
		
	}
	
	public static void update() {
		for(GameAction action : actionPool) {
			action.updateAction();
		}
	}
	
	
	
}
