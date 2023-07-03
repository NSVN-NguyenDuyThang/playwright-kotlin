package shou.retryconfig

import org.testng.IAnnotationTransformer
import org.testng.annotations.ITestAnnotation
import java.lang.reflect.Constructor
import java.lang.reflect.Method

class RetryListener : IAnnotationTransformer {
    override fun transform(
        annotation: ITestAnnotation,
        testClass: Class<*>?,
        testConstructor: Constructor<*>?,
        testMethod: Method?
    ) {
        annotation.setRetryAnalyzer(RetryTestFailed::class.java)
    }
}
