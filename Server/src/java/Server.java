import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Consumer;

public class Server {
	private int clientCount = 0;
	private ArrayList<ClientThread> connectedClients = new ArrayList<>();
	private Consumer<Serializable> logMessage;
	private Consumer<Serializable> updateClientCount;
	private Consumer<Serializable> notifyClientActivity;
	private ServerSocket serverSocket;
	public boolean isRunning = false;
	private int portNumber;

	public Server(int portNum, Consumer<Serializable> logCallback,
				  Consumer<Serializable> clientCountCallback,
				  Consumer<Serializable> clientActivityCallback) {
		this.portNumber = portNum;
		this.logMessage = logCallback;
		this.updateClientCount = clientCountCallback;
		this.notifyClientActivity = clientActivityCallback;

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (isRunning) {
				stopServer();
			}
		}));

	}

	public synchronized void startServer(int selectedPort) {
		this.portNumber = selectedPort;
		if (isRunning) {
			logMessage.accept("Server is already running.");
			return;
		}

		isRunning = true;
		Thread serverThread = new Thread(new TheServer());
		serverThread.start();
		logMessage.accept("Server started on port " + portNumber);
	}

	public synchronized void stopServer() {
		if (!isRunning) {
			logMessage.accept("Server is not running.");
			return;
		}

		try {
			isRunning = false;
			// Close all client connections
			for (ClientThread client : connectedClients) {
				client.closeConnection();
			}
			// Close server socket
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			logMessage.accept("Server stopped.");
		} catch (IOException e) {
			logMessage.accept("Error stopping server: " + e.getMessage());
		}
	}

	public int getConnectedClientsCount() {
		return this.connectedClients.size();
	}

	public boolean isRunning() {
		return isRunning;
	}

	private class TheServer implements Runnable {
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(portNumber);
				logMessage.accept("Server is waiting for clients!");

				while (isRunning) {
					try {
						Socket clientSocket = serverSocket.accept();
						clientCount++;
						ClientThread clientThread = new ClientThread(clientSocket, clientCount);
						connectedClients.add(clientThread);
						new Thread(clientThread).start();

						logMessage.accept("Client #" + clientCount + " connected to server");
						updateClientCount.accept("Clients connected: " + connectedClients.size());
					} catch (SocketException e) {
						if (isRunning) {
							logMessage.accept("Server socket error: " + e.getMessage());
						}
						// If server is not running, this is expected during shutdown
						break;
					}
				}
			} catch (IOException e) {
				if (isRunning) {
					logMessage.accept("Server socket error: " + e.getMessage());
				}
			} finally {
				if (!serverSocket.isClosed()) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						logMessage.accept("Error closing server socket: " + e.getMessage());
					}
				}
			}
		}
	}

	private class ClientThread implements Runnable {
		private Socket connection;
		private int clientID;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private Dealer dealer;
		private volatile boolean clientRunning = true;

		ClientThread(Socket s, int clientID) {
			this.connection = s;
			this.clientID = clientID;
			this.dealer = new Dealer();
		}

		@Override
		public void run() {
			try {
				setupStreams();

				while (clientRunning && isRunning) {
					try {

						processGameRound();

						// Check if client wants to play again
//						PokerInfo response = (PokerInfo) in.readObject();
//						if (!response.isPlayAnotherGame()) {
//							break;
//						}

						dealer = new Dealer(); // New deck for new game
					} catch (Exception e) {
						logMessage.accept("Error in game round: " + e.getMessage());
						break;
					}
				}
			} catch (IOException e) {
				logMessage.accept("Client connection error: " + e.getMessage());
			} finally {
				handleClientDisconnection();
			}
		}

		private void setupStreams() throws IOException {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
			connection.setTcpNoDelay(true);
		}

		private void processGameRound() throws IOException, ClassNotFoundException {
			try {
				// Initialize new game state
				PokerInfo gameInfo = new PokerInfo();

				// Preserve total winnings from previous rounds if any
				double previousTotalWinnings = gameInfo.getTotalWinnings();
				gameInfo.setTotalWinnings(previousTotalWinnings);

				// Set initial state for betting
				gameInfo.setGameState(GameState.WAITING_FOR_BET);
				updateClient(gameInfo);

				// Wait for and handle betting phase
				gameInfo = (PokerInfo) in.readObject();
				double anteBet = gameInfo.getAnteBet();
				double pairPlus = gameInfo.getPairPlusBet();

				notifyClientActivity.accept("Client #" + clientID + " placed Ante bet: $" + anteBet);
				System.out.println("The Pair Plus bet: " + pairPlus);
				notifyClientActivity.accept("Client #" + clientID + " placed Pair Plus bet: $" + pairPlus);

				// Deal cards AFTER getting bets
				dealCards(gameInfo);

				// Handle player decision and calculate results
				handlePlayerDecisionAndResults(gameInfo);

				// Check if player wants to play another round
				checkPlayAgain(gameInfo);

			} catch (Exception e) {
				logMessage.accept("Error in game round: " + e.getMessage());
				throw e;
			}
		}

		private void dealCards(PokerInfo gameInfo) {
			try {
				System.out.println("Starting dealCards method");

				// Use dealer to deal hands
				ArrayList<Card> playerCards = dealer.dealHand();
				ArrayList<Card> dealerCards = dealer.dealHand();
				dealer.setHand(dealerCards); // Set dealer's hand

				System.out.println("Player cards dealt: " + playerCards);
				System.out.println("Dealer cards dealt: " + dealerCards);

				// Convert cards to image paths
				String[] playerHandPaths = new String[3];
				String[] dealerHandPaths = new String[3];

				for (int i = 0; i < 3; i++) {
					playerHandPaths[i] = playerCards.get(i).getImagePath();
					dealerHandPaths[i] = dealerCards.get(i).getImagePath();
				}

				// Update game info
				gameInfo.setPlayerCards(playerCards);
				gameInfo.setDealerCards(dealerCards);
				gameInfo.setPlayerHand(playerHandPaths);
				gameInfo.setDealerHand(dealerHandPaths);
				gameInfo.setGameState(GameState.CARDS_DEALT);

				updateClient(gameInfo);
				System.out.println("New hands dealt and sent to client");

			} catch (Exception e) {
				System.out.println("Error in dealCards: " + e.getMessage());
				e.printStackTrace();
				logMessage.accept("Error in dealCards: " + e.getMessage());
			}
		}

//		private void handlePlayerDecision(PokerInfo gameInfo) throws IOException, ClassNotFoundException {
//			String playerDecision = (String) in.readObject(); // Read "Play" or "Fold" from client
//			gameInfo.setDecision(playerDecision);
//
//			// Log the player's decision
//			notifyClientActivity.accept("Client #" + clientID + " decided to " + gameInfo.getDecision());
//
//			if (gameInfo.isPlayerFolded()) {
//
//				handleFold(gameInfo);
//			} else {
//				handlePlay(gameInfo);
//			}
//		}

		private void handlePlayerDecisionAndResults(PokerInfo gameInfo) throws IOException, ClassNotFoundException {
			gameInfo = (PokerInfo) in.readObject();
			boolean playerDecision = gameInfo.getDecision();

			System.out.println(playerDecision);

			if (playerDecision) {
				System.out.println("Player decision failed");
				// Handle fold
				gameInfo.setPlayerFolded(true);
				handleFold(gameInfo);
			} else {
				// Handle play decision
				gameInfo.setPlayerFolded(false);
				gameInfo.setPlayBet(gameInfo.getAnteBet());
				gameInfo.setDealerHandVisible(true);

				// Calculate full game result
				GameCalculator gameCalculator = new GameCalculator();
				gameCalculator.calculateGameResult(gameInfo);

				System.out.println("Player winnings: " + gameInfo.getTotalWinnings());


				// Make sure the total winnings are set in the gameInfo object
				double currentWinnings = gameInfo.getCurrentGameWinnings();
				double totalWinnings = gameInfo.getTotalWinnings();

				gameInfo.setCurrentGameWinnings(currentWinnings);
				gameInfo.setTotalWinnings(totalWinnings);

				// Log the result with both current game and total winnings
				notifyClientActivity.accept(String.format("Client #%d: %s | Game Winnings: $%.2f | Total Winnings: $%.2f",
						clientID,
						gameInfo.getGameResult(),
						currentWinnings,
						totalWinnings));
			}

			gameInfo.setGameState(GameState.GAME_OVER);
			// Ensure the total winnings are included in the update sent to the client
			updateClient(gameInfo);
		}

		private void handleFold(PokerInfo gameInfo) {
			double loss = -(gameInfo.getAnteBet() + gameInfo.getPairPlusBet());
			gameInfo.setGameResult("Player Folded");
			gameInfo.setPlayerWins(false);
			gameInfo.setCurrentGameWinnings(loss);
			gameInfo.updateTotalWinnings(); // Update total winnings with the loss
			gameInfo.setGameState(GameState.GAME_OVER);

			notifyClientActivity.accept(String.format("Client #%d folded | Game Loss: $%.2f | Total Winnings: $%.2f",
					clientID,
					loss,
					gameInfo.getTotalWinnings()));
		}

		private void checkPlayAgain(PokerInfo gameInfo) throws IOException, ClassNotFoundException {
			try {
				gameInfo = (PokerInfo) in.readObject();
				boolean playAgain = gameInfo.isPlayAnotherGame();


				if (!playAgain) {
					// Player wants to quit
					notifyClientActivity.accept("Client #" + clientID + " ended their session");
					logMessage.accept("Client #" + clientID + " chose to end the game");

					// Send final state back to client
					gameInfo.setGameState(GameState.GAME_OVER);
					updateClient(gameInfo);
				} else {
					// Player wants to continue
					notifyClientActivity.accept("Client #" + clientID + " wants to play another round");

					// Reset game state for next round
					gameInfo.resetGameState();
					gameInfo.resetBets();
					gameInfo.resetHands();

					// Send reset state back to client
					gameInfo.setGameState(GameState.WAITING_FOR_BET);
					updateClient(gameInfo);
				}
			} catch (Exception e) {
				logMessage.accept("Error checking play again status: " + e.getMessage());
				e.printStackTrace();
				throw new IOException("Failed to process play again request", e);
			}
		}

		private synchronized void updateClient(PokerInfo info) {
			try {
				// Basic validation
				if (out == null || connection == null || connection.isClosed()) {
					System.out.println("Invalid connection state");
					throw new IOException("Connection invalid");
				}

				// Ensure PokerInfo is valid
				if (info == null) {
					System.out.println("PokerInfo is null");
					throw new IOException("Null PokerInfo object");
				}

				try {
					out.reset();  // Reset the stream
					out.writeObject(info);  // Write the object
					out.flush();  // Ensure all data is sent
				} catch (IOException e) {
					System.out.println("Failed to send data: " + e.getMessage());
					throw e;  // Re-throw to be caught by outer catch block
				}

			} catch (IOException e) {
				System.out.println("Error in updateClient: " + e.getMessage());
				logMessage.accept("Error sending update to client #" + clientID);
				handleClientDisconnection();
			}
		}


		private void handleClientDisconnection() {
			try {
				closeConnection();
				connectedClients.remove(this);
				updateClientCount.accept("Clients connected: " + connectedClients.size());
				logMessage.accept("Client #" + clientID + " disconnected");
			} catch (Exception e) {
				logMessage.accept("Error during client disconnection: " + e.getMessage());
			}
		}

		private void closeConnection() {
			try {
				clientRunning = false;
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (IOException e) {
				logMessage.accept("Error closing client #" + clientID + " connection: " + e.getMessage());
			}
		}
	}
}