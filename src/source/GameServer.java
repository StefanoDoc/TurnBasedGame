package source;

import java.io.*;
import java.net.*;

public class GameServer {
	private ServerSocket ss;
	private int numPlayers;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	
	public GameServer() {
		System.out.println("Game Server");
		numPlayers=0;
		try {
			ss=new ServerSocket(51734); //porta , quando il player si collega al server deve specificare la stessa porta
		}catch(IOException e) {
			System.out.println("IOException GameServer Constructor");
		}
	}
	
	public void acceptConnection() {
		try {
			System.out.println("Waiting for connections");
			while(numPlayers<2) {
				Socket s = ss.accept();
				numPlayers++;
				System.out.println("Player #"+numPlayers+"has connected");
				ServerSideConnection ssc=new ServerSideConnection(s,numPlayers);
				if (numPlayers==1) {
					player1=ssc;
				}else {
					player2=ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("Max num of player reached");
		}catch(IOException e) {
			System.out.println("IOException from GameServer -> acceptConnections");
		}
	}
	
	private class ServerSideConnection implements Runnable {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s , int id) {
			socket=s;
			playerID=id;
			try {
				dataIn=new DataInputStream(socket.getInputStream());
				dataOut=new DataOutputStream(socket.getOutputStream());
			}catch(IOException e) {
				System.out.println("IOException from GameServer->ServerSideConnection");
			}
		}
		public void run() {
			try {
				dataOut.writeInt(playerID);
				dataOut.flush();
				while(true) {}
			}catch(IOException e) { System.out.println("IOExcpetion from GS->SSC->run"); }
		}
	}
	
	public static void main(String[] args) {
		GameServer gs=new GameServer();
		gs.acceptConnection();
	}

}
