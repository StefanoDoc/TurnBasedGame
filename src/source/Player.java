package source;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Player extends JFrame{
	private int width;
	private int height;
	private Container contentPane;
	private JTextArea message;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private int playerID;
	private int otherPlayer;
	
	private ClientSideConnection csc;
	
	public Player(int w, int h) {
		width=w;
		height=h;
		contentPane=this.getContentPane();
		message=new JTextArea();
		b1=new JButton("1");
		b2=new JButton("2");
		b3=new JButton("3");
		b4=new JButton("4");
	}
	
	public void setUpGUI() {
		this.setSize(width,height);
		this.setTitle("Playe# " +playerID);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.setLayout(new GridLayout(1,5));
		contentPane.add(message);
		message.setText("Simple turn-based game");
		message.setWrapStyleWord(true);
		message.setLineWrap(true);
		message.setEditable(false);
		contentPane.add(b1);
		contentPane.add(b2);
		contentPane.add(b3);
		contentPane.add(b4);
		
		if(playerID==1) {
			message.setText("You are player #1 , you go first");
			otherPlayer=2;
		}else {
			message.setText("You are player #2 , wait your turn");
			otherPlayer=1;
		}
		
		this.setVisible(true);
	}
	
	public void connectToServer() {
		csc=new ClientSideConnection();
	}
	
	//client connection inner class that encapsulate all the thing we need for the player to be able to connect and comunicate wih server
	private class ClientSideConnection{
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("client");
			try {
				socket=new Socket("localhost",51734);
				dataIn=new DataInputStream(socket.getInputStream());
				dataOut=new DataOutputStream(socket.getOutputStream());
				playerID=dataIn.readInt();
				System.out.println("connected to server as Player#" + playerID +"");
			}catch(IOException e) {
				System.out.println("IOException from Playe-> ClientSideConnection");
			}
		}
	}
	
	public static void main(String[] args) {
		Player p=new Player(500,100);
		p.connectToServer();
		p.setUpGUI();
	}

}
