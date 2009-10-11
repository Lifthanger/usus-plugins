// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.history;

import java.util.ArrayList;
import java.util.List;

import org.projectusus.core.internal.proportions.CodeProportion;
import org.projectusus.core.internal.proportions.IsisMetrics;
import org.projectusus.core.internal.proportions.checkpoints.ICheckpoint;

class Checkpoints2GraphicsConverter {

    private final List<ICheckpoint> checkpoints;

    Checkpoints2GraphicsConverter( List<ICheckpoint> checkpoints ) {
        this.checkpoints = checkpoints;
    }

    double[] get( IsisMetrics metric ) {
        List<Double> values = new ArrayList<Double>();
        for( ICheckpoint checkpoint : checkpoints ) {
            values.add( computeValue( metric, checkpoint ) );
        }
        return convert( values );
    }

    private Double computeValue( IsisMetrics metric, ICheckpoint checkpoint ) {
        CodeProportion codeProportion = find( metric, checkpoint.getEntries() );
        return codeProportion == null ? new Double( 0.0 ) : codeProportion.getGraphValue();
    }

    private double[] convert( List<Double> values ) {
        double[] result = new double[values.size()];
        for( int i = 0; i < values.size(); i++ ) {
            result[i] = values.get( i ).doubleValue();
        }
        return result;
    }

    private CodeProportion find( IsisMetrics metric, List<CodeProportion> entries ) {
        CodeProportion result = null;
        for( CodeProportion codeProportion : entries ) {
            if( codeProportion.getMetric() == metric ) {
                result = codeProportion;
            }
        }
        return result;
    }

}