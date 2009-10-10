// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.coveredprojects;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class CoveredProjectsCP implements IStructuredContentProvider {

    private Viewer viewer;
    private IResourceChangeListener listener;

    public CoveredProjectsCP() {
        createListener();
    }

    public Object[] getElements( Object input ) {
        Object[] result = new Object[0];
        if( input instanceof IWorkspaceRoot ) {
            IWorkspaceRoot wsRoot = (IWorkspaceRoot)input;
            result = wsRoot.getProjects();
        }
        return result;
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
        cleanListeners( oldInput );
        connectListeners( newInput );
        this.viewer = viewer;
    }

    public void dispose() {
        // unused
    }

    // internal
    // /////////

    private void connectListeners( Object newInput ) {
        if( newInput instanceof IWorkspaceRoot ) {
            IWorkspaceRoot wsRoot = (IWorkspaceRoot)newInput;
            wsRoot.getWorkspace().addResourceChangeListener( listener );
        }
    }

    private void cleanListeners( Object oldInput ) {
        if( oldInput instanceof IWorkspaceRoot ) {
            IWorkspaceRoot wsRoot = (IWorkspaceRoot)oldInput;
            wsRoot.getWorkspace().removeResourceChangeListener( listener );
        }
    }

    private void createListener() {
        this.listener = new RefreshViewerOnResourceChange( viewer );
    }
}