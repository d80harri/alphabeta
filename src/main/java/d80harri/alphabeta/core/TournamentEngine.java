package d80harri.alphabeta.core;

import java.util.ArrayList;
import java.util.HashMap;

import d80harri.alphabeta.core.GameEngine.AgentDescription;
import d80harri.alphabeta.intfs.AlphaBetaPlayer;
import d80harri.alphabeta.intfs.IAgent;
import d80harri.alphabeta.intfs.IStartPositionCreator;

public class TournamentEngine {
	public static class TournamentResult {
		private IAgent agent = null;
		private int wins;
		private int losses;
		private int remis;

		public TournamentResult(IAgent agent, int wins, int losses, int remis) {
			super();
			this.agent = agent;
			this.wins = wins;
			this.losses = losses;
			this.remis = remis;
		}

		public void won() {
			this.wins++;
		}

		public void lost() {
			this.losses++;
		}

		public void remis() {
			this.remis++;
		}
		
		@Override
		public String toString() {
			return agent.toString() + "(" + wins + ":" + remis + ":" + losses + ")";
		}
	}

	private ArrayList<IAgent> agents = new ArrayList<IAgent>();
	private IStartPositionCreator startPositionCreator = null;
	private int numOfRounds = 10;

	public TournamentEngine(int numOfRounds, IStartPositionCreator spc) {
		this.numOfRounds = numOfRounds;
		this.startPositionCreator = spc;
	}

	public void addAgent(IAgent agent) {
		this.agents.add(agent);
	}

	public TournamentResult[] startTournament() {
		HashMap<IAgent, TournamentResult> tournamentResults = new HashMap<IAgent, TournamentResult>();
		for (int i = 0; i < numOfRounds; i++) {
			for (IAgent maxAgent : agents) {
				for (IAgent minAgent : agents) {
					if (maxAgent != minAgent) {
						AgentDescription[] descs = new AgentDescription[2]; // TODO
																			// games
																			// with
																			// more
																			// than
																			// two
																			// players
						descs[0] = new AgentDescription(AlphaBetaPlayer.MAX,
								maxAgent);
						descs[1] = new AgentDescription(AlphaBetaPlayer.MIN,
								minAgent);

						GameEngine ge = new GameEngine(startPositionCreator
								.createStartPosition(), descs);
						double result = ge.startGame();

						TournamentResult minResult = tournamentResults
								.get(minAgent);
						TournamentResult maxResult = tournamentResults
								.get(maxAgent);

						if (minResult == null) {
							minResult = new TournamentResult(minAgent, 0, 0, 0);
							tournamentResults.put(minAgent, minResult);
						}
						if (maxResult == null) {
							maxResult = new TournamentResult(maxAgent, 0, 0, 0);
							tournamentResults.put(maxAgent, maxResult);
						}

						if (result < 0.0) {
							minResult.won();
							maxResult.lost();
						} else if (result > 0.0) {
							minResult.lost();
							maxResult.won();
						} else { // result == 0.0
							minResult.remis();
							maxResult.remis();
						}
					}
				}
			}
		}
		return tournamentResults.values().toArray(new TournamentResult[0]);
	}
}
