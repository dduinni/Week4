package com.power21.p9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	
	public static void main(String[] args) {
		// Client와 통신하기 위한 Socket
		Socket socket = null;
		// 서버 생성을 위한 ServerSocket
		ServerSocket ss = null;
		// Client로부터 데이터를 읽어들이기 위한 입력 스트림
		BufferedReader in = null;
		// Client로 데이터를 내보내기위한 출력 스트림
		PrintWriter out = null;
		
		try {
			ss = new ServerSocket(4444);
		}catch(IOException e) {
			System.out.println("해당 포트가 열려있습니다.");
		}
		
		try {
			System.out.println("서버가 열렸습니다.");
			// 서버 생성, Client 접속 대기
			socket = ss.accept();
			
			// 입력스트림 생성
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			
			// 출력스트림 생성
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			String str = null;
			
			// Client로부터 데이터를 읽어옴
			str = in.readLine();
			
			System.out.println("Client가 보낸 메세지 : " + str);
			
			out.write(str);
			out.flush();
			socket.close();
		}catch(IOException e) {
			
		}
	}
}