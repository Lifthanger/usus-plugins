// Copyright (c) 2009-2010 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.proportions.rawdata.jdtdriver;

import static org.eclipse.jdt.core.JavaCore.createCompilationUnitFrom;
import static org.eclipse.jdt.core.dom.ASTParser.newParser;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.projectusus.core.metrics.MetricsCollector;
import org.projectusus.core.statistics.UsusModelProvider;

public class JavaFileDriver {

    private final IFile file;

    public JavaFileDriver( IFile file ) {
        this.file = file;
    }

    public void compute( Set<MetricsCollector> metricsExtensions ) {
        CompilationUnit compilationUnit = parseFile();
        for( MetricsCollector visitor : metricsExtensions ) {
            setup( visitor );
            compilationUnit.accept( visitor );
        }
    }

    private void setup( MetricsCollector visitor ) {
        visitor.setup( file, UsusModelProvider.getMetricsWriter() );
    }

    private CompilationUnit parseFile() {
        return parse( createCompilationUnitFrom( file ) );
    }

    private static CompilationUnit parse( ICompilationUnit unit ) {
        ASTParser parser = newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setResolveBindings( true );
        parser.setSource( unit );
        return (CompilationUnit)parser.createAST( null );
    }

}
