package com.math

import spock.lang.FailsWith
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static spock.util.matcher.HamcrestMatchers.closeTo
import static spock.util.matcher.HamcrestSupport.that

// hamcrest requires, runtime of core, plus the matchers, plus that or expect
/**
 *
 * @author ksipe
 */
class SimpleInterestCalculatorSpec extends Specification {

    def "interest rate calcs with Simple calculator"() {

    }

    @Unroll("int: #interest amt: #amt year: #year")
    def "showing off vars list in calc"() {

    }
}
