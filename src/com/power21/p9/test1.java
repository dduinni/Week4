package com.power21.p9;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class test1 {
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Test");
		
		Composite composite = formToolkit.createComposite(shell, SWT.NONE);
		composite.setBounds(0, 0, 432, 253);
		formToolkit.paintBordersFor(composite);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}