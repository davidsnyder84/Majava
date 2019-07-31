package majava.summary;

import java.util.List;


//functionality for printing endgame results
public class GameResultPrinter {

	private final List<RoundResultSummary> roundResults;
	private final List<String> winStrings;
	
	
	
	public GameResultPrinter(List<RoundResultSummary> results, List<String> wins){
		roundResults = results;
		winStrings = wins;
	}
	
	public void printGameResults(){
		System.out.print(this.toString());
	}
	
	public String gameResult(){
		String gameRes = "";
		gameRes += "\n\n\n\n\n\n~~~~~~~~~~~~~~~~~~~~~~\nGAMEOVER\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		gameRes += "\n~~~~~~~~~~~~~~~~~~~~~~\nPrinting win results\n~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		
		for (int i = 0; i < winStrings.size(); i++)
			gameRes += String.format("%3d: %s\n", i+1, winStrings.get(i));
		
		RoundResultSummary cr = null;
		for (int i = 0; i < roundResults.size(); i++){
			cr = roundResults.get(i);
			if (cr != null && !cr.isRyuukyokuHowanpai())
				gameRes += String.format("%3d: %s\n", i+1, cr.getAsStringResultType());
		}
		
		return gameRes;
	}
	
	public String reportCard(){
		String report = "";
		int numWins = 0;
		int numWinsByPlayer[] = new int[4];
		
		for (RoundResultSummary cr: roundResults){
			if (cr != null && cr.isVictory()){
				numWins++;
				numWinsByPlayer[cr.getWinningPlayer().getPlayerNumber()]++;
			}
		}
		report += "\n\n\n";

		report += "Wins by player:\n";
		int playerNum = 0;
		for (int i: numWinsByPlayer)
			report += "\tPlayer " + (++playerNum) + ": " + i + " wins\n";
		report += String.format("Win to ryuukyoku ratio: %.6f", ((double) numWins) / ((double) roundResults.size()));
		report += "\n\n\n";
		return report;
	}
	
	
	@Override
	public String toString(){return gameResult() + reportCard();}
	
}
