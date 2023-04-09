package com.power21.p9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;

public class Server {
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private static Text text;
	
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Server");
		
		Composite composite = formToolkit.createComposite(shell, SWT.NONE);
		composite.setBounds(0, 0, 432, 253);
		formToolkit.paintBordersFor(composite);
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(10, 10, 412, 233);
		formToolkit.adapt(text, true, true);
		
		Socket socket = null;
		ServerSocket ss = null;
		BufferedReader in = null;
		PrintWriter out = null;
		//test
		//수정
		//test2
		try {
			ss = new ServerSocket(4444);
		}catch(IOException e) {
			System.out.println("해당 포트가 열려있습니다.");
		}
		
		try {
			System.out.println("서버가 열렸습니다.");
			socket = ss.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			String str= null;
			str = in.readLine();
			
			System.out.println(str);
			
			out.write(str);
			out.flush();
			socket.close();
			
		}catch(IOException e) {
			
		}
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
