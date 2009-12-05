// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import static org.eclipse.jface.window.Window.OK;
import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.projectusus.ui.internal.util.UITexts.testSuiteSelectorDialog_buttonLabel;
import static org.projectusus.ui.internal.util.UITexts.testSuiteSelectorDialog_msg;
import static org.projectusus.ui.internal.util.UITexts.testSuiteSelectorDialog_title;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

class TestSuiteSelectorDialogOpener {

    private int dialogResult;

    boolean openDialog() {
        Display.getDefault().syncExec( new Runnable() {
            public void run() {
                int result = new TestSuiteSelectorDialog( findShell() ).open();
                setDialogResult( result );
            }
        } );
        return dialogResult == OK;
    }

    private void setDialogResult( int dialogResult ) {
        this.dialogResult = dialogResult;
    }

    private Shell findShell() {
        IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
        return window != null ? window.getShell() : new Shell();
    }

    private static class TestSuiteSelectorDialog extends MessageDialog {
        private static final String[] BUTTON_LABELS = new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, testSuiteSelectorDialog_buttonLabel };

        public TestSuiteSelectorDialog( Shell parentShell ) {
            super( parentShell, testSuiteSelectorDialog_title, null, testSuiteSelectorDialog_msg, QUESTION, BUTTON_LABELS, 0 );
        }

        @Override
        protected void buttonPressed( int buttonId ) {
            if( buttonId == 2 ) { // 'No, but...' button
                new TestSuiteSelectionAction( getShell() ).run();
            }
            super.buttonPressed( buttonId );
        }
    }
}
