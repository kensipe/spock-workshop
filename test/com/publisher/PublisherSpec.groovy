package com.publisher

import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

/**
 *
 * @author ksipe
 */
@Title("this has a title")
@Narrative("""
this is a description of what this thing is suppose to do
""")
class PublisherSpec extends Specification {

    /**
     * test that the publisher invokes all subscribers when the fire method is invoked
     */
    def "events are published to all subscribers"() {

        def publisher = new Publisher()

        when:
        publisher.fire("event")

        then:
        // replace with mock tests
        1 == 1
    }


    /**
     * test the order of mocks and that a 2nd subscriber is invoked even after the 1st subscriber threw an exception
     */
    def "events are published a subscriber throws an exception"() {
        def subscriber1 = Mock(Subscriber)
        def subscriber2 = Mock(Subscriber)

        def publisher = new Publisher()
        publisher.subscribers << subscriber1 << subscriber2

        // uncomment and the then body
//        when:
//        publisher.fire("event")

    }
}