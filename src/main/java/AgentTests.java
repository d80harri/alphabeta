import d80harri.alphabeta.core.GameEngine;
import d80harri.alphabeta.core.TournamentEngine;
import d80harri.alphabeta.core.GameEngine.AgentDescription;
import d80harri.alphabeta.core.TournamentEngine.TournamentResult;
import d80harri.alphabeta.intfs.AlphaBetaPlayer;
import d80harri.alphabeta.intfs.IPosition;
import d80harri.alphabeta.intfs.IStartPositionCreator;
import d80harri.alphabeta.sticks.FuzzySticksAgent;
import d80harri.alphabeta.sticks.SticksAgentV1;
import d80harri.alphabeta.sticks.SticksPosition;


public class AgentTests {
	
	public static void gameEngine(){
		GameEngine engine = new GameEngine(
				new SticksPosition(new int[]{5,6,7}, AlphaBetaPlayer.MAX),
				new AgentDescription(AlphaBetaPlayer.MAX, new SticksAgentV1()),
				new AgentDescription(AlphaBetaPlayer.MIN, new FuzzySticksAgent())
			);
		
		double result = engine.startGame();
		
		System.out.println(engine.getPositions());
		System.out.println(result);
	}
	
	public static void tournament() {
		TournamentEngine te = new TournamentEngine(100, new IStartPositionCreator(){
			@Override
			public IPosition createStartPosition() {
				return new SticksPosition(new int[]{5,6,7}, AlphaBetaPlayer.MAX);
			}	
		});
		te.addAgent(new SticksAgentV1());
		te.addAgent(new FuzzySticksAgent());
		
		TournamentResult[] res = te.startTournament();
		for (TournamentResult r : res){
			System.out.println(r);
		}
	}
	
	public static void main(String[] args) {
		tournament();
		
	}
}
