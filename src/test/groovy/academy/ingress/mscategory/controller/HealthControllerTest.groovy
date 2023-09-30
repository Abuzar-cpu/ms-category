package academy.ingress.mscategory.controller

import academy.ingress.mscategory.exception.GlobalHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class HealthControllerTest extends Specification {
    MockMvc mockMvc
    HealthController healthController

    def setup() {
        healthController = new HealthController()
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).setControllerAdvice(
                new GlobalHandler()
        ).build()
    }

    @Unroll
    def "Test healthCheck"() {
        when:
        def result = mockMvc
                .perform(
                        get(url)
                ).andReturn()

        then:
        result.response.status == expectedStatus

        where:
        url          | expectedStatus
        "/health"    | 200
        "/readiness" | 200
    }
}
