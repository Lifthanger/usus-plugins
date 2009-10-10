// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.perspective;

import static org.eclipse.ui.IPageLayout.BOTTOM;
import static org.eclipse.ui.IPageLayout.LEFT;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.projectusus.ui.internal.coveredprojects.CoveredProjectsView;
import org.projectusus.ui.internal.history.HistoryView;
import org.projectusus.ui.internal.proportions.CodeProportionsCockpit;
import org.projectusus.ui.internal.proportions.HotSpotsView;
import org.projectusus.ui.internal.yellowcount.YellowCountView;

public class USUSPerspective implements IPerspectiveFactory {

    private static final List<String> INTERESTING_THIRD_PARTY_VIEWS = collectInterestingViews();

    public void createInitialLayout( IPageLayout layout ) {
        layoutViews( layout );
        createViewShortcuts( layout );
        createPerspectiveShortcuts( layout );
    }

    private void layoutViews( IPageLayout layout ) {
        // left-hand side
        IFolderLayout projects = layout.createFolder( "projects", LEFT, 0.4f, layout.getEditorArea() ); //$NON-NLS-1$
        projects.addView( "org.eclipse.jdt.ui.PackageExplorer" ); //$NON-NLS-1$
        projects.addView( CoveredProjectsView.class.getName() );
        layout.addView( CodeProportionsCockpit.class.getName(), BOTTOM, 0.5f, "projects" ); //$NON-NLS-1$
        layout.addView( YellowCountView.class.getName(), BOTTOM, 0.8f, CodeProportionsCockpit.class.getName() );

        // right-hand side
        IFolderLayout analysis = layout.createFolder( "analysis", BOTTOM, 0.33f, layout.getEditorArea() ); //$NON-NLS-1$
        analysis.addView( HistoryView.class.getName() );
        analysis.addView( HotSpotsView.class.getName() );
        IFolderLayout inputs = layout.createFolder( "inputs", BOTTOM, 0.5f, HistoryView.class.getName() ); //$NON-NLS-1$
        for( String viewId : INTERESTING_THIRD_PARTY_VIEWS ) {
            inputs.addView( viewId );
        }
    }

    private void createViewShortcuts( IPageLayout layout ) {
        layout.addShowViewShortcut( HistoryView.class.getName() );
        layout.addShowViewShortcut( CodeProportionsCockpit.class.getName() );
        layout.addShowViewShortcut( HotSpotsView.class.getName() );
        layout.addShowViewShortcut( YellowCountView.class.getName() );
        layout.addShowViewShortcut( CoveredProjectsView.class.getName() );

        for( String viewId : INTERESTING_THIRD_PARTY_VIEWS ) {
            layout.addShowViewShortcut( viewId );
        }
        layout.addShowViewShortcut( "org.eclipse.jdt.ui.PackageExplorer" ); //$NON-NLS-1$
    }

    private void createPerspectiveShortcuts( IPageLayout layout ) {
        layout.addPerspectiveShortcut( "org.eclipse.jdt.ui.JavaPerspective" ); //$NON-NLS-1$
        layout.addPerspectiveShortcut( "org.eclipse.pde.ui.PDEPerspective" ); //$NON-NLS-1$
    }

    private static List<String> collectInterestingViews() {
        List<String> result = new ArrayList<String>();
        result.add( "org.eclipse.ui.views.TaskList" ); //$NON-NLS-1$
        result.add( "org.eclipse.ui.views.ProblemView" ); //$NON-NLS-1$
        result.add( "net.sf.eclipsecs.ui.duplicates.DuplicatedCodeView" ); //$NON-NLS-1$
        result.add( "net.sf.eclipsecs.ui.stats.views.MarkerStatsView" ); //$NON-NLS-1$
        result.add( "net.sf.eclipsecs.ui.stats.views.GraphStatsView" ); //$NON-NLS-1$
        result.add( "org.eclipse.jdt.junit.ResultView" ); //$NON-NLS-1$
        result.add( "com.mountainminds.eclemma.ui.CoverageView" ); //$NON-NLS-1$
        return result;
    }
}
