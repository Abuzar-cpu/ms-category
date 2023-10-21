package academy.ingress.mscategory.controller

import academy.ingress.mscategory.exception.GlobalHandler
import academy.ingress.mscategory.model.requests.CreateCategoryRequest
import academy.ingress.mscategory.model.requests.PageRequestDto
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest
import academy.ingress.mscategory.model.responses.CreateCategoryResponse
import academy.ingress.mscategory.model.responses.GetCategoryResponse
import academy.ingress.mscategory.service.abstracts.CategoryService
import academy.ingress.mscategory.specification.CategorySpecificationDto
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CategoryControllerTest extends Specification {
    CategoryService categoryService
    CategoryController categoryController
    MockMvc mockMvc
    String BASE_URL

    def setup() {
        categoryService = Mock(CategoryService)
        categoryController = new CategoryController(categoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).setControllerAdvice(
                new GlobalHandler()
        ).build()
        BASE_URL = "/internal/v1/categories/"
    }

    def "CategoryControllerTest createCategory - case success"() {
        given:
        def url = BASE_URL
        def requestDto = new CreateCategoryRequest("test")
        def createResponse = new CreateCategoryResponse("test", false, true)
        def requestJson = """
            {
                "name": "test"
            }
        """
        def expectedResult = """{
                                          "name": "test",
                                          "isFavorite": false,
                                          "isVisible": true
                                       }"""
        when:
        def result = mockMvc
                .perform(
                        post(url)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(requestJson)
                                .characterEncoding("UTF-8")
                )
        then:
        1 * categoryService.createCategory(requestDto) >> createResponse
        result.andExpectAll(
                status().isCreated(),
                content().json(expectedResult)
        )

    }

    def "CategoryControllerTest createCategory - case constraint violation for name"() {
        given:
        def url = BASE_URL
        def requestJson = """{"name": ""}"""
        def expectedResult = '''{
                                            "message": "name is mandatory"
                                       }'''
        when:
        def result = mockMvc
                .perform(
                        post(url)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(requestJson)
                )
        then:
        0 * categoryService.createCategory(_)
        result.andExpectAll(
                status().isBadRequest(),
                content().json(expectedResult)
        )
    }

    def "CategoryControllerTest getAllCategoriesBySpecifications - case success"() {
        given:
        def specs = CategorySpecificationDto.builder()
                .id(1L)
                .name("test")
                .isFavorite(false)
                .build()
        def page = new PageRequestDto(1, 1)
        def url = BASE_URL + "/?id=${specs.id}&name=${specs.name}&isFavorite=" +
                "${specs.isFavorite}&page=${page.page}&size=${page.size}"
        def getResponse = GetCategoryResponse.builder()
                .name("test")
                .id(1L)
                .isFavorite(false)
                .build()
        def expectedResult = """[{
                                          "id": 1,
                                          "name": "test",
                                          "isFavorite": false
                                       }]"""
        when:
        def result = mockMvc.perform(
                get(url)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        )
        then:
        1 * categoryService.getAllCategoriesBySpecifications(specs, page) >> [getResponse]
        result.andExpectAll(
                status().isOk(),
                content().json(expectedResult)
        )
    }

    def "CategoryControllerTest updateCategory - case success"() {
        given:
        def id = 2L
        def url = BASE_URL + "/$id"
        def updateCategoryRequest = UpdateCategoryRequest.builder()
                .name("test")
                .isVisible(false)
                .build()
        def requestJson = """{"name": "test", "isVisible": false}"""

        when:
        def result = mockMvc.perform(
                put(url)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
        )

        then:
        1 * categoryService.updateCategory(id, updateCategoryRequest)
        result.andExpectAll(
                status().isNoContent()
        )
    }

    def "CategoryControllerTest deleteCategory - case success"() {
        given:
        def id = 2L
        def url = BASE_URL + "/$id"

        when:
        def result = mockMvc.perform(
                delete(url)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        )

        then:
        1 * categoryService.deleteCategory(id)
        result.andExpectAll(
                status().isNoContent()
        )

    }
}