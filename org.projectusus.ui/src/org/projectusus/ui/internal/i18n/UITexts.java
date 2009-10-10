// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.ui.internal.i18n;

import org.eclipse.osgi.util.NLS;

public final class UITexts extends NLS {

    public static String columnDesc_covered;
    public static String columnDesc_project;

    private static final String BUNDLE_NAME = UITexts.class.getPackage().getName() + ".uitexts"; //$NON-NLS-1$

    static {
        NLS.initializeMessages( BUNDLE_NAME, UITexts.class );
    }
}