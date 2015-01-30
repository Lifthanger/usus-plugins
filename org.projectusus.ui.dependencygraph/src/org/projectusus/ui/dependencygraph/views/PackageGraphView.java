package org.projectusus.ui.dependencygraph.views;

import java.util.Set;

import org.projectusus.ui.dependencygraph.common.DependencyGraphModel;
import org.projectusus.ui.dependencygraph.common.DependencyGraphView;
import org.projectusus.ui.dependencygraph.nodes.GraphNode;
import org.projectusus.ui.dependencygraph.nodes.PackageRepresenter;

public class PackageGraphView extends DependencyGraphView {

    public static final String VIEW_ID = PackageGraphView.class.getName();

    private static final String ONLY_IN_CYCLES = "Only cyclic dependencies";

    private static final DependencyGraphModel packageGraphModel = new DependencyGraphModel() {

        @Override
        protected Set<? extends GraphNode> getRefreshedNodes() {
            return PackageRepresenter.getAllPackages();
        }
    };

    public PackageGraphView() {
        super( packageGraphModel );
    }

    @Override
    public String getFilenameForScreenshot() {
        return "usus-package-graph";
    }

    @Override
    protected String getCheckboxLabelName() {
        return ONLY_IN_CYCLES;
    }
}
