package academy.ingress.mscategory.service

import academy.ingress.mscategory.dao.entity.SubCategory
import academy.ingress.mscategory.dao.repository.SubCategoryRepository
import academy.ingress.mscategory.exception.NotFoundException
import academy.ingress.mscategory.model.requests.CreateSubCategoryRequest
import academy.ingress.mscategory.model.responses.UpdateSubCategoryRequest
import academy.ingress.mscategory.service.abstracts.CategoryService
import academy.ingress.mscategory.service.abstracts.SubCategoryService
import academy.ingress.mscategory.service.concretes.SubCategoryServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static academy.ingress.mscategory.mapper.SubCategoryMapper.SUB_CATEGORY_MAPPER

class SubCategoryServiceHandlerTest extends Specification {
    private SubCategoryRepository subCategoryRepository
    private SubCategoryService subCategoryService
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()
    private CategoryService categoryService

    def setup() {
        subCategoryRepository = Mock()
        categoryService = Mock()
        subCategoryService = new SubCategoryServiceHandler(subCategoryRepository, categoryService)
    }

    def "SubCategoryServiceHandlerTest createSubCategory - case success"() {
        given:
        def subCategory = random.nextObject(CreateSubCategoryRequest)
        def entity = random.nextObject(SubCategory)
        when:
        subCategoryService.createSubCategory(subCategory)

        then:
        1 * subCategoryRepository.save(SUB_CATEGORY_MAPPER.buildSubCategoryFromCreateRequest(subCategory)) >> entity

    }

    def "SubCategoryServiceHandlerTest updateSubCategory - case success"() {
        given:
        def id = random.nextLong()
        def updateSubCategoryRequest = random.nextObject(UpdateSubCategoryRequest)
        def entity = random.nextObject(SubCategory)
        when:
        subCategoryService.updateSubCategory(id, updateSubCategoryRequest)

        then:
        1 * subCategoryRepository.findById(id) >> Optional.of(entity)
        1 * subCategoryRepository.save(SUB_CATEGORY_MAPPER.rebuildSubCategoryFromUpdateRequest(entity, updateSubCategoryRequest))
                >> entity
    }

    def "SubCategoryServiceHandlerTest updateSubCategory - case category not found"() {
        given:
        def id = random.nextLong()
        def updateSubCategoryRequest = random.nextObject(UpdateSubCategoryRequest)
        when:
        subCategoryService.updateSubCategory(id, updateSubCategoryRequest)

        then:
        1 * subCategoryRepository.findById(id) >> Optional.empty()
        0 * subCategoryRepository.save(_)
        NotFoundException e = thrown()
        e.message == "requested category not found"
    }

    def "SubCategoryServiceHandlerTest deleteSubCategory - case success"() {
        given:
        def id = random.nextLong()

        when:
        subCategoryService.deleteSubCategory(id)

        then:
        1 * subCategoryRepository.deleteById(id)
    }

}