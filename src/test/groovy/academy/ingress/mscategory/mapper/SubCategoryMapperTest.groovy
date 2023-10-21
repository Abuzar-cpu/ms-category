package academy.ingress.mscategory.mapper


import academy.ingress.mscategory.dao.entity.SubCategory
import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest
import academy.ingress.mscategory.model.responses.CreateCategoryResponse
import academy.ingress.mscategory.model.responses.GetCategoryResponse
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static academy.ingress.mscategory.mapper.SubCategoryMapper.SUB_CATEGORY_MAPPER

class SubCategoryMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()

    def "SubCategoryMapperTest buildSubCategoryFromCreateRequest - success"() {
        given:
        def createSubCategoryRequest = CreateSubCategoryRequest.builder()
                .name("test")
                .parentId(1L)
                .build()
        when:
        def result = SUB_CATEGORY_MAPPER.buildSubCategoryFromCreateRequest(createSubCategoryRequest)

        then:
        result.name == createSubCategoryRequest.name
        result.isFavorite == false
        result.isVisible == true
    }

    def "SubCategoryMapperTest mapEntityToCreateResponse - success"() {
        given:
        def subCategory = SubCategory.builder()
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
        def result = SUB_CATEGORY_MAPPER.mapEntityToCreateResponse(subCategory)

        then:
        result.isVisible == createResponse.isVisible
        result.isFavorite == createResponse.isFavorite
        result.name == createResponse.name
    }

    def "SubCategoryMapperTest mapEntityToGetResponse - success"() {
        given:
        def category = SubCategory.builder()
                .isFavorite(true)
                .name("test")
                .isVisible(false)
                .id(1)
                .isFavorite(false)
                .build()

        def getResponse = GetCategoryResponse.builder()
                .id(1)
                .name("test")
                .isFavorite(false)
                .build()

        when:
        def result = SUB_CATEGORY_MAPPER.mapEntityToGetResponse(category)

        then:
        result.isFavorite == getResponse.isFavorite
        result.name == getResponse.name
        result.id == getResponse.getId()
    }

    def "SubCategoryMapperTest rebuildSubCategoryFromUpdateRequest - success"() {
        given:
        def subCategory = SubCategory.builder()
                .isFavorite(true)
                .name("test")
                .isVisible(false)
                .id(1)
                .isFavorite(false)
                .build()

        def updateRequest = UpdateSubCategoryRequest.builder()
                .name("test")
                .isFavorite(true)
                .isVisible(false)
                .build()

        when:
        def result = SUB_CATEGORY_MAPPER.rebuildSubCategoryFromUpdateRequest(subCategory, updateRequest)

        then:
        result.name == updateRequest.name
        result.isFavorite == updateRequest.isFavorite
        result.isVisible == updateRequest.isVisible

    }

}
