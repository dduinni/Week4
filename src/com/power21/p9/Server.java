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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;

public class Server {
    private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
    private static Text text;
    public static String str;
    
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

        shell.open();
        shell.layout();
        
        
        //Thread start ->
        new Thread(() -> {
            Socket socket = null;
            ServerSocket ss = null;
            BufferedReader in = null;
            PrintWriter out = null;

            try {
                ss = new ServerSocket(4444);
            } catch(IOException e) {
                System.out.println("해당 포트가 열려있습니다.");
                return;
            }

            try {
                System.out.println("서버가 열렸습니다.");
                socket = ss.accept();

                while(true) {
                	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                	out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

                	str = in.readLine();
                
                	if(str.equals("종료")) {
                		
                		// Invalid thread access 오류는 일반적으로 UI 스레드가 아닌 다른 스레드에서 UI 업데이트를 시도할 때 발생합니다.
                		// 해당 오류를 해결하기 위해 다음과 같은 방법을 시도할 수 있습니다.
                		Display.getDefault().asyncExec(() -> {
    						showMsgbox(shell, "close", "사용자가 접속을 종료하였습니다.");
                		    shell.close();
                		});
                		socket.close();
                		return;
                	}
                
                	Display.getDefault().asyncExec(() -> {
                		text.setText(str);
                	});

                	out.write(str);
                	out.flush();
                }
                
            } catch(IOException e) {

            }
        }).start();
        //Thread end <-
        // 다음 코드를 사용하면 UI를 구현하는 부분과 SOCKET에서 클라이언트의 응답을 대기하는 부분이
        // 분리될 수 있어서 클라이언트의 응답을 받기 전까지 UI가 응답없음 상태가 되는 것을 해결할 수 있다.
        
        
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


