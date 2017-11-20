package majava.control.testcode;

import java.util.ArrayList;

import majava.Wall;
import majava.tiles.GameTile;
import majava.util.GameTileList;

public class WallDemoer {
	private static final int NUMBER_OF_DIFFERENT_TILES = 34;
	private static final int MAX_SIZE_WALL = NUMBER_OF_DIFFERENT_TILES * 4;	//136
	private static final int POS_LAST_NORMAL_WALL_TILE = 121;
	private static final int[] debugHandSizes = {14,13,13,13};
	private static final int OFFSET_DEAD_WALL = POS_LAST_NORMAL_WALL_TILE + 1;

	private static final boolean SCAM_DEAD_WALL = true;
//	private static final boolean SCAM_DEAD_WALL = false;
	
	
	public static void loadDebugWall(GameTile[] wallTiles, int offset){
		
		int tsumo2, tsumo3, tsumo4, tsumo5, tsumo6, tsumo7, tsumo8, tsumo9;
		tsumo2 = tsumo3 = tsumo4 = tsumo5 = tsumo6 = tsumo7 = tsumo8 = tsumo9 = 0;
//		int[] h1 = {2,2,4,4,6,6,8,8,10,10,12,12,15,15};
		
		
//		//multiple minkans
//		int[] h1 = {2,2,2,14,14,14,14,22,22,29,29,29,33,33};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		

//		int[] h1 = {3,6,8,9,9,10,12,13,15,23,23,32,32,32};
//		int[] h2 = {4,6,12,13,14,14,18,19,19,21,25,29,34};
//		int[] h3 = {2,5,11,13,16,17,21,23,26,27,28,29,32};
//		int[] h4 = {1,2,6,7,9,10,11,15,18,20,22,24,25};
//	tsumo2 = 9 + 18;
//	tsumo3 = 1 + 18;
//	tsumo4 = 0;
		
		
		//specific error case
//		Integer[] h1 = {1,2,5,8,9,10,11,14,15,20,21,29,30,34};
//		Integer[] h2 = {2,2,4,7,9,15,16,17,18,19,19,22,23};
//		Integer[] h3 = {1,3,7,16,16,17,25,25,28,30,30,31,33};
//		Integer[] h4 = {3,5,9,10,11,11,21,22,24,28,29,29,34};
//		tsumo2 = 24;
		
		//specific error case
//		Integer[] h1 = {3,4,11,11,17};
//		Integer[] h2 = {1,6,7,20};
//		Integer[] h3 = {2,5,5,6};
//		Integer[] h4 = {4,8,12,17};
//		tsumo2 = 2;
//		debugHandSizes = new int[]{5,4,4,4};
		
		
		//kokushi
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 34, 34, 7};
		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34, 7};
//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
//		int[] h1 = {1,1,1,3,3,3,5,5,5,7,7,7,15,15};
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34, 7};
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};	//KANS
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,5,7};	//CALL PRIORITY
		
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h2 = {4,4,4,4,10,12,14,16,18,20,22,24,26};	//KANS
//		int[] h2 = {4,4,5,5,10,12,14,16,18,20,22,24,26};	//ENDGAME
		int[] h2 = {-2,0,5,5,10,12,14,16,18,20,22,24,26};	//CALL PRIORITY
//		
		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};	//ENDGAME
////		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		
//		int[] h4 = {0,0,6,8,10,12,14,16,18,20,22,24,26};	//ENDGAME
		int[] h4 = {0,0,6,8,10,12,14,16,18,20,22,24,26};	//CALL PRIORYT
////		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		for (int i = 0; i < h2.length; i++){h2[i] += 3;	h3[i] -= 1;	h4[i] += 2;}
		tsumo2 = tsumo3 = tsumo4 = tsumo5 = tsumo6 = tsumo7 = tsumo8 = 2;
		tsumo9 = 1;
		
		//5 kans
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,5,7};
//		int[] h3 = {2,2,2,2,2,2,2,2,2,2,2,2,5,7};
//		int[] h4 = {2,2,2,2,2,2,2,2,2,2,2,2,5,7};
//		for (int i = 0; i < h2.length; i++){h2[i] += 3;	h3[i] -= 1;	h4[i] += 2;}
		
//		int[] h2 = {0,0,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {0,0,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {0,0,6,8,10,12,14,16,18,20,22,24,26};
		
		
		
		//desired hands (ENTER HERE)
//		int[] h1 = {1, 9, 10, 18, 19, 27, 28, 29, 30, 31, 32, 33, 34, 7};
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
		
//		//chiitoi
//		int[] h1 = {2,2,11,11,25,25,28,28,29,32,33,33,34,34};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		for (int i = 0; i < h2.length; i++){h2[i] += 3;	h3[i] -= 1;	h4[i] += 2;}
		
//		int[] h1 = {2,2,4,4,6,6,8,8,10,10,12,12,15,15};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		

		
		
		//kan
//		int[] h1 = {15,2,10,6,4,6,8,2,4,2,12,12,2,10};
//		int[] h2 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h3 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
		
		
//		//priority calling
////		int[] h1 = {2,2,5,5,6,6,8,8,10,10,12,12,15,15};
//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,20};
////		int[] h2 = {1,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h2 = {5,5,5,5,5,5,5,5,5,5,5,5,5};
//		int[] h3 = {1,1,1,2,3,4,5,6,7,8,9,9,9};
////		int[] h3 = {5,5,5,5,5,5,5,5,5,5,5,5,5};
////		int[] h4 = {2,4,6,8,10,12,14,16,18,20,22,24,26};
//		int[] h4 = {1,1,1,1,1,1,1,1,1,1,1,1,1};
//		
//		for (int i = 0; i < h1.length; i++){
//			h1[i] += 9;
//			if (i < h2.length){
//				h4[i] += 9 + 6;
//				h3[i] += 9;
//			}
//		}
////		tsumo2 = 0;
//		tsumo3 = 1 + 9;
////		tsumo4 = 0;
		
		
//		int[] h1 = {2,2,2,2,2,2,2,2,2,2,2,2,2,7};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		

//		int[] h1 = {1,1,1,2,3,4,5,6,7,8,9,9,9,15};
//		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
//		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
//		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		
		
		
		
		/*
		int[] h1 = {1,2,2,2,3,4,5,6,7,8,9,9,9,3};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		*/
		
		
		/*
		int[] h1 = {1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		int[] h2 = {2,2,2,2,2,2,2,2,2,2,2,2,2};
		int[] h3 = {3,3,3,3,3,3,3,3,3,3,3,3,3};
		int[] h4 = {4,4,4,4,4,4,4,4,4,4,4,4,4};
		for(int i = 0; i < 13; i++){
			h1[i] += 18;
			h2[i] += 18;
			h3[i] += 18;
			h4[i] += 18;
		}
		h1[13] += 18;
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Integer h1i[] = new Integer[h1.length], h2i[] = new Integer[h2.length], h3i[] = new Integer[h3.length], h4i[] = new Integer[h4.length];
		for (int i = 0; i < h1.length; i++) h1i[i] = h1[i];
		for (int i = 0; i < h2.length; i++) h2i[i] = h2[i];
		for (int i = 0; i < h3.length; i++) h3i[i] = h3[i];
		for (int i = 0; i < h4.length; i++) h4i[i] = h4[i];
		
		GameTileList tilesE = new GameTileList(h1i);
		GameTileList tilesS = new GameTileList(h2i);
		GameTileList tilesW = new GameTileList(h3i);
		GameTileList tilesN = new GameTileList(h4i);
		
		while (tilesE.size() < 14) tilesE.add(new GameTile(0));
		while (tilesS.size() < 13) tilesS.add(new GameTile(0));
		while (tilesW.size() < 13) tilesW.add(new GameTile(0));
		while (tilesN.size() < 13) tilesN.add(new GameTile(0));
		
		if (debugHandSizes[0] > 14) debugHandSizes[0] = 14;
		for (int i = 1; i < debugHandSizes.length; i++) if (debugHandSizes[i] > 13) debugHandSizes[i] = 13;
		
		
		final int TAKEN_PER_ROUND = 16;
		final int TAKEN_PER_PLAYER = 4;
		
		//put desired hands in the wall
		int i, j;
		//each player takes 4, 3 times
		for (i = 0; i < 3; i++){
			//east takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 0*TAKEN_PER_PLAYER + offset] = tilesE.remove(0);
			//south takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 1*TAKEN_PER_PLAYER + offset] = tilesS.remove(0);
			//west takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 2*TAKEN_PER_PLAYER + offset] = tilesW.remove(0);
			//north takes 4
			for (j = 0; j < 4; j++)
				wallTiles[TAKEN_PER_ROUND*i + j + 3*TAKEN_PER_PLAYER + offset] = tilesN.remove(0);
		}
		//east takes 2
		wallTiles[3*TAKEN_PER_ROUND + 0 + offset] = tilesE.remove(0);
		wallTiles[3*TAKEN_PER_ROUND + 1 + offset] = tilesE.remove(0);

		//south takes 1
		wallTiles[3*TAKEN_PER_ROUND + 2 + offset] = tilesS.remove(0);

		//west takes 1
		wallTiles[3*TAKEN_PER_ROUND + 3 + offset] = tilesW.remove(0);

		//north takes 1
		wallTiles[3*TAKEN_PER_ROUND + 4 + offset] = tilesN.remove(0);
		
		
		if (tsumo2 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 1 + offset] = new GameTile(tsumo2);
		if (tsumo3 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 2 + offset] = new GameTile(tsumo3);
		if (tsumo4 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 3 + offset] = new GameTile(tsumo4);
		
		
		if (tsumo5 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 4 + offset] = new GameTile(tsumo5);
		if (tsumo6 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 5 + offset] = new GameTile(tsumo6);
		if (tsumo7 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 6 + offset] = new GameTile(tsumo7);
		if (tsumo8 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 7 + offset] = new GameTile(tsumo8);
		
		if (tsumo9 != 0) wallTiles[3*TAKEN_PER_ROUND + 4 + 8 + offset] = new GameTile(tsumo9);
		
		
		if (SCAM_DEAD_WALL)
			scamDeadWall(wallTiles, 2);
	}
	public static void loadDebugWall(GameTile[] wallTiles){loadDebugWall(wallTiles, 0);}
	public int[] DEMOgetDebugPlayerHandSizes(){return debugHandSizes;}
	
	
	public static void scamDeadWall(GameTile[] wallTiles, int scam){		
		for(int i = OFFSET_DEAD_WALL; i < MAX_SIZE_WALL; i++)
			wallTiles[i] = new GameTile(scam);
	}
	
	
	
//	public static void main(String[] args) {
//		for (int i=1; i<=1; i++)
//			System.out.println(i);
//	}
}
