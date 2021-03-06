// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.adapter;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import org.projectusus.core.statistics.UsusModelProvider;

/**
 * convenience implementation of target that runs over the whole workspace.
 * 
 * @author leiffrenzel
 */
class WorkspaceCodeProportionComputationTarget extends ProjectCodeProportionComputationTarget {

    WorkspaceCodeProportionComputationTarget() {
        super( getWorkspace().getRoot().getProjects() );
        UsusModelProvider.ususModelForAdapter().aboutToStartFullRecompute();
    }
}
