package com.extensions

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 *
 * @author kensipe
 */
class SayOnFailExtension extends AbstractAnnotationDrivenExtension<SayOnFail> {

    @Override
    void visitSpecAnnotation(SayOnFail sayOnError, SpecInfo spec) {
    }

    @Override
    void visitFeatureAnnotation(SayOnFail sayOnError, FeatureInfo feature) {
    }
}
