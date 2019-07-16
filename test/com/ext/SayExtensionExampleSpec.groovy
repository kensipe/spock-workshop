package com.ext

import com.extensions.SayOnFail
import spock.lang.Specification

/**
 *
 * @author kensipe
 */
@SayOnFail
class SayExtensionExampleSpec extends Specification {


    def "bad expectations"() {
        expect:
        Math.max(1, 4) == 4
        // todo:  uncomment the below expection to test your extension
        // Math.max(1, 4) == 3
    }
}
