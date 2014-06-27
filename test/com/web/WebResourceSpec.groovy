package com.web

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 *
 * @author kensipe
 */
class WebResourceSpec extends Specification {

    @Rule
    final TemporaryFolder temporaryFolder = new TemporaryFolder()

    def "file checks with Spy"() {

        setup:
        def resource = Spy(WebResource, constructorArgs: [WebResource.class])
        // todo:  code here

        expect:
        // todo:  uncomment expectation
//        exists == resource.exists(fileCheck)

        where:
        file | fileCheck || exists
        "css/reset.css" | "css/reset.css" || true
        "css/reset.css" | "css/bad.css"   || false
    }

    private void createResourceFile(file) {
        def cssFile = new File(temporaryFolder.root, file)
        cssFile.parentFile.mkdirs()
        cssFile << "text for file in test"
    }
}
