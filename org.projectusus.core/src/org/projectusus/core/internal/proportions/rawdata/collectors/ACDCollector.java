package org.projectusus.core.internal.proportions.rawdata.collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.projectusus.core.internal.UsusCorePlugin;

public class ACDCollector extends Collector {

    private ITypeBinding currentType;

    public ACDCollector( IFile file ) {
        super( file );
    }

    @Override
    public boolean visit( TypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( AnnotationTypeDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    @Override
    public boolean visit( EnumDeclaration node ) {
        setCurrentType( node );
        return true;
    }

    // referenced types

    @Override
    public boolean visit( ArrayType node ) {
        if( currentType == null ) {
            return true;
        }
        Type element = node.getElementType();
        if( element.isSimpleType() ) {
            visit( (SimpleType)element );
        }
        return true;
    }

    @Override
    public boolean visit( SimpleType node ) {
        if( currentType == null ) {
            return true;
        }

        ITypeBinding targetType = node.resolveBinding();
        if( targetType != null ) {
            targetType = targetType.getErasure();
            if( isTypeInSourceFile( targetType ) && hasNothingToDoWithTypeVariables( targetType ) ) {
                UsusCorePlugin.getUsusModelMetricsWriter().addClassReference( currentType, targetType );
            }
        }
        return true;
    }

    private boolean isTypeInSourceFile( ITypeBinding targetType ) {
        return targetType != null && targetType.isFromSource();
    }

    private boolean hasNothingToDoWithTypeVariables( ITypeBinding targetType ) {
        return !targetType.isTypeVariable() && !targetType.isCapture() && !targetType.isWildcardType();
    }

    /**
     * QualifiedType -- it should not be necessary to treat it:
     * 
     * A type like "A.B" can be represented either of two ways:
     * <ol>
     * <li>
     * <code>QualifiedType(SimpleType(SimpleName("A")),SimpleName("B"))</code></li>
     * <li>
     * <code>SimpleType(QualifiedName(SimpleName("A"),SimpleName("B")))</code></li>
     * </ol>
     * 
     * Therefore, it should be sufficient to treat SimpleType.
     */

    private void setCurrentType( AbstractTypeDeclaration node ) {
        currentType = node.resolveBinding().getErasure();
    }

}
