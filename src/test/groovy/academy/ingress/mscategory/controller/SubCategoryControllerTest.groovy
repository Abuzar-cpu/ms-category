package academy.ingress.mscategory.controller


import academy.ingress.mscategory.exception.GlobalHandler
import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest
import academy.ingress.mscategory.model.requests.PageRequestDto
import academy.ingress.mscategory.model.responses.CreateSubCategoryResponse
import academy.ingress.mscategory.model.responses.GetCategoryResponse
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest
import academy.ingress.mscategory.service.abstracts.SubCategoryService
import academy.ingress.mscategory.specification.SubCategorySpecificationDto
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SubCategoryControllerTest extends Specification {
    SubCategoryService subCategoryService
    SubCategoryController subCategoryController
    MockMvc mockMvc
    String BASE_URL

    def setup() {
        subCategoryService = Mock(SubCategoryService)
        subCategoryController = new SubCategoryController(subCategoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(subCategoryController).setControllerAdvice(
                new GlobalHandler()
        ).build()
        BASE_URL = "/internal/v1/subCategories"
    }

    def "SubCategoryControllerTest createSubCategory - case success"() {
        given:
        def url = BASE_URL
        def createSubCategoryRequest = CreateSubCategoryRequest.builder()
                .name("test")
                .parentId(1L)
                .build()
        def createSubCategoryResponse = CreateSubCategoryResponse.builder()
                .name("test")
                .isFavorite(false)
                .isVisible(true)
                .build()

        def requestJson = """
            {
                "name": "test",
                "parentId": 1
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
                )
        then:
        1 * subCategoryService.createSubCategory(createSubCategoryRequest) >> createSubCategoryResponse
        result.andExpectAll(
                status().isCreated(),
                content().json(expectedResult)
        )

    }

    def "SubCategoryControllerTest createSubCategory - case constraint violation for name"() {
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
        0 * subCategoryService.createSubCategory(_)
        result.andExpectAll(
                status().isBadRequest(),
                content().json(expectedResult)
        )
    }

    def "SubCategoryControllerTest getAllSubCategoriesBySpecifications - case success"() {
        given:
        def id = 2L
        def specs = SubCategorySpecificationDto.builder().id(id).name("test").isFavorite(false).
                build()

        def page = new PageRequestDto(1, 1)
        def url = BASE_URL + "/?id=${specs.id}&name=${specs.name}&isFavorite=" +
                "${specs.isFavorite}&page=${page.page}&size=${page.size}"
        def getResponse = GetCategoryResponse.builder()
                .name("test")
                .id(id)
                .isFavorite(false)
                .build()
        def expectedResult = """[{
                                          "id": $id,
                                          "name": "test",
                                          "isFavorite": false
                                       }]"""
        when:
        def result = mockMvc
                .perform(
                        get(url)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                )

        then:
        1 * subCategoryService.getAllSubCategoriesBySpecifications(specs, page) >> [getResponse]
        result.andExpectAll(
                status().isOk(),
                content().json(expectedResult)
        )
    }

    def "SubCategoryControllerTest updateSubCategory - case success"() {
        given:
        def id = 2L
        def url = BASE_URL + "/$id"
        def updateSubCategoryRequest = UpdateSubCategoryRequest.builder()
                .isFavorite(true)
                .name("test")
                .build()
        def requestJson = """{"isFavorite": true, "name": "test"}"""
        when:
        def result = mockMvc.perform(
                put(url)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(requestJson)
        )

        then:
        1 * subCategoryService.updateSubCategory(id, updateSubCategoryRequest)
        result.andExpectAll(
                status().isNoContent()
        )
    }

    def "SubCategoryControllerTest deleteSubCategory - case success"() {
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
        1 * subCategoryService.deleteSubCategory(id)
        result.andExpectAll(
                status().isNoContent()
        )
    }
}