// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.List;

import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.model.MetricCCHotspot;
import org.projectusus.core.internal.proportions.model.MetricMLHotspot;

public class MethodRawData implements IRawData {

    private final int startPosition;
    private final String className;
    private final String methodName;

    private int ccValue;
    private int mlValue;

    public MethodRawData( int startPosition, String className, String methodName ) {
        this.startPosition = startPosition;
        this.className = className;
        this.methodName = methodName;
    }

    public void setCCValue( int value ) {
        ccValue = value;
    }

    public int getCCValue() {
        return ccValue;
    }

    public void setMLValue( int value ) {
        mlValue = value;
    }

    public int getMLValue() {
        return mlValue;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getSourcePosition() {
        return startPosition;
    }

    public boolean violates( IsisMetrics metric ) {
        return metric.isViolatedBy( this );
    }

    public int getViolationBasis( IsisMetrics metric ) {
        return 1;
    }

    public int getViolationCount( IsisMetrics metric ) {
        return metric.isViolatedBy( this ) ? 1 : 0;
    }

    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        if( metric.isViolatedBy( this ) ) {
            if( metric.equals( IsisMetrics.CC ) ) {
                hotspots.add( new MetricCCHotspot( className, getMethodName(), getCCValue(), getSourcePosition() ) );
            } else if( metric.equals( IsisMetrics.ML ) ) {
                hotspots.add( new MetricMLHotspot( className, getMethodName(), getMLValue(), getSourcePosition() ) );
            }
        }
    }

    public int getOverallMetric( IsisMetrics metric ) {
        return metric.getValueFor( this );
    }
}
