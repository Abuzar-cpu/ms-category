package academy.ingress.mscategory.mapper

import academy.ingress.mscategory.dao.entity.Category
import academy.ingress.mscategory.model.requests.CreateCategoryRequest
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest
import academy.ingress.mscategory.model.responses.CreateCategoryResponse
import academy.ingress.mscategory.model.responses.GetCategoryResponse
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static academy.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER

class CategoryMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()

    def "CategoryMapperTest mapUpdateRequestToEntity - success"() {
        given:
        def updateCategoryRequest = UpdateCategoryRequest.builder()
                .isVisible(true)
                .name("test")
                .parentId(5)
                .isFavorite(true)
                .build()
        def category = Category.builder()
                .isFavorite(true)
                .name("test")
                .isVisible(true)
                .id(1)
                .build()
        when:
        def result = CATEGORY_MAPPER.rebuildCategoryFromUpdateRequest(category, updateCategoryRequest)

        then:
        result.id == 1
        result.name == "test"
        result.isVisible == true
        result.isFavorite == true
        result.id == 1
    }

    def "CategoryMapperTest mapEntityToCreateResponse - success"() {
        given:
        def category = Category.builder()
                .isFavorite(true)
                .name("test")
                .isVisible(false)
                .id(1)
                .build()

        def createResponse = CreateCategoryResponse.builder()
                .isVisible(false)
                .name("test")
                .isFavorite(true)
                .build()

        when:
        def result = CATEGORY_MAPPER.mapEntityToCreateResponse(category)

        then:
        result.isVisible == createResponse.isVisible
        result.isFavorite == createResponse.isFavorite
        result.name == createResponse.name
    }

    def "CategoryMapperTest mapEntityToGetResponse - success"() {
        given:
        def category = Category.builder()
                .isFavorite(true)
                .name("test")
                .isVisible(false)
                .id(1)
                .isFavorite(false)
                .subCategories(List.of())
                .build()

        def getResponse = GetCategoryResponse.builder()
                .id(1)
                .name("test")
                .isFavorite(false)
                .build()

        when:
        def result = CATEGORY_MAPPER.mapEntityToGetResponse(category)

        then:
        result.isFavorite == getResponse.isFavorite
        result.name == getResponse.name
        result.id == getResponse.getId()
    }

    def "CategoryMapperTest buildCategoryFromCreateRequest - success"() {
        given:
        def createRequest = CreateCategoryRequest.builder()
                .name("test")
                .build()

        when:
        def result = CATEGORY_MAPPER.buildCategoryFromCreateRequest(createRequest)

        then:
        result.name == createRequest.name
    }

}
