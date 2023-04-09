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

	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Client");
		
		Composite composite = formToolkit.createComposite(shell, SWT.NONE);
		composite.setBounds(0, 0, 432, 253);
		formToolkit.paintBordersFor(composite);
		
		Button btn = formToolkit.createButton(composite, "전송", SWT.PUSH);
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Socket socket = null;
				InetAddress ia = null;
				BufferedReader in = null;
				BufferedReader in2 = null;
				PrintWriter out = null;
				
				System.out.println("서버로 메세지가 전송되었습니다.");
				// 서버한테 메세지가 전달되면 넣는 코드 - if문
				// 코드 추가해야 함.
				MessageBox messageBox = new MessageBox(shell, SWT.OK);
				messageBox.setText("Success");
				messageBox.setMessage("서버로 메세지가 전송되었습니다.");
				messageBox.open();
				
				try {
					ia = InetAddress.getLocalHost();
					socket = new Socket(ia, 4444);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					in2 = new BufferedReader(new InputStreamReader(System.in));
					out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
					
					System.out.println(socket.toString());
					
				}catch(IOException exception) {
					
				}
				
				try {
					String data = in2.readLine();
					out.println(data);
					out.flush();
					
					String str2 = in.readLine();
					socket.close();
				}catch(IOException exception) {
					
				}
			}
		});
		btn.setBounds(332, 10, 93, 30);
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(6, 10, 320, 30);
		formToolkit.adapt(text, true, true);

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
