// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.projectusus.core.project.FindUsusProjects;

class ProjectCodeProportionComputationTarget implements ICodeProportionComputationTarget {

    private final Iterable<IProject> projects;

    public ProjectCodeProportionComputationTarget( IProject... projects ) {
        this( asList( projects ) );
    }

    public ProjectCodeProportionComputationTarget( Iterable<IProject> projects ) {
        this.projects = projects;
    }

    public Collection<IProject> getProjects() {
        return findUsusProjects().compute();
    }

    public Collection<IProject> getRemovedProjects() {
        return findUsusProjects().computeOpposite();
    }

    private FindUsusProjects findUsusProjects() {
        return new FindUsusProjects( projects );
    }

    public Collection<IFile> getFiles( IProject project ) throws CoreException {
        return collectFiles( project );
    }

    public Collection<IFile> getRemovedFiles( IProject project ) throws CoreException {
        return emptyList();
    }

    // internal
    // /////////

    private Collection<IFile> collectFiles( IProject project ) throws CoreException {
        Map<String, IFile> files = new HashMap<String, IFile>();
        List<IResource> resources = collectResources( project );
        for( IResource resource : resources ) {
            if( resource instanceof IFile ) {
                addFile( (IFile)resource, files );
            }
        }
        return files.values();
    }

    private List<IResource> collectResources( final IContainer container ) throws CoreException {
        List<IResource> resources = new ArrayList<IResource>();
        IResource[] children = container.members();
        for( IResource child : children ) {
            resources.add( child );
            if( child instanceof IContainer ) {
                resources.addAll( collectResources( (IContainer)child ) );
            }
        }
        return resources;
    }

    private void addFile( IFile file, Map<String, IFile> files ) {
        files.put( file.getLocation().toString(), file );
    }

    public boolean isNotEmpty() {
        return true; // simplifying assumption...
    }
}
