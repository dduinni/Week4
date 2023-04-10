package com.power21.p9;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class Client {
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private static Text text;
	public static Socket socket;
	public static void main(String[] args) {
		//test
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Client");
		
		Composite composite = formToolkit.createComposite(shell, SWT.NONE);
		composite.setBounds(0, 0, 432, 253);
		formToolkit.paintBordersFor(composite);
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(6, 10, 320, 30);
		formToolkit.adapt(text, true, true);
		
		socket = null;

		Button btn = formToolkit.createButton(composite, "전송", SWT.PUSH);
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InetAddress ia = null;
				BufferedReader in = null;
				PrintWriter out = null;

				// 스트림 설정 부분
				try {
					ia = InetAddress.getLocalHost();
					if(socket == null) socket = new Socket(ia, 4444);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));					
				}catch(IOException exception) {
					
				}
				
				// 서버가 열려있지 않을때
				if(socket==null) {
					showMsgbox(shell, "Fail", "서버가 열려있지 않습니다");
					return;
				} else {
					System.out.println(socket.toString());
				}
				
				
				try {
					String data = text.getText();
					// 사용자로부터 입력받은 텍스트
					if (data.isEmpty()) {
						showMsgbox(shell, "Fail", "텍스트를 입력하세요");
						return;
					} else if (data.equals("종료")) {
						
						showMsgbox(shell, "close", "접속이 종료되었습니다.");
						out.println(data);
						out.flush();
						socket.close();
						shell.close();
						return;
					}
					out.println(data);
					out.flush();
						
					//String str2 = in.readLine();
						
					showMsgbox(shell, "Success", "메시지가 전송되었습니다.");
				}catch(IOException exception) {
					
				}
			}
		});
		btn.setBounds(332, 10, 93, 30);

		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public static void showMsgbox(Shell shell, String title, String msg) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK);
		messageBox.setText(title);
		messageBox.setMessage(msg);
		messageBox.open();
	}
}
