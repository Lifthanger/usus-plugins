// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.basis;

import java.math.BigDecimal;

import org.eclipse.core.runtime.PlatformObject;

public class CodeStatistic extends PlatformObject {

    private final CodeProportionUnit unit;
    private final int value;

    public CodeStatistic( CodeProportionUnit unit ) {
        this( unit, 0 );
    }

    public CodeStatistic( CodeProportionUnit metric, int value ) {
        this.unit = metric;
        this.value = value;
    }

    public CodeProportionUnit getUnit() {
        return unit;
    }

    public int getValue() {
        return value;
    }

    public BigDecimal asBigDecimal() {
        return new BigDecimal( value );
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit.getLabel(); //$NON-NLS-1$
    }
}