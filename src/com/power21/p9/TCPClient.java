package com.power21.p9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
	
	public static void main(String[] args) {
		// Server와 통신하기 위한 Socket
		Socket socket = null;
		// Server로부터 데이터를 읽어들이기 위한 입력 스트림
		BufferedReader in = null;
		// 키보드로부터 읽어들이기 위한 입력 스트림
		BufferedReader in2 = null;
		// 서버로 내보내기 위한 출력 스트림
		PrintWriter out = null;
		
		InetAddress ia = null;
		
		try {
			ia = InetAddress.getLocalHost();
			
			socket = new Socket(ia, 4444);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in2 = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			System.out.println(socket.toString());
		}catch(IOException e) {
			
		}
		
		try {
			System.out.print("서버로 보낼 메세지 : ");
			String data = in2.readLine();
			out.println(data);
			out.flush();
			
			String str2 = in.readLine();
			System.out.println("서버로부터 되돌아온 메세지 : " + str2);
			socket.close();
		}catch(IOException e){
			
		}
	}
}