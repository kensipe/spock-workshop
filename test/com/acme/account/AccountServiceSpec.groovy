package com.acme.account

import spock.lang.Specification

/**
 *
 * @author ksipe
 */

class AccountServiceSpec extends Specification {


    def "deposit into account test refactor"() {

        given:
        def mock = Mock(AccountDao)
        def service = new AccountServiceImpl(mock)

        // todo:  uncomment the below when: and provide details for the mocks
//        when:
//        service.deposit nmr, amt
//
//        then:
        // mock stuff here

        // expect will be removed in the process of modifying this class
        // todo: remove the expect body for the lab
        expect:
        1 == 1

        where:
        nmr | account | amt | total
        "101" | new Account("101", 100) | 50 | 150
        "101" | new Account("101", 0)   | 50 | 50
    }

}
