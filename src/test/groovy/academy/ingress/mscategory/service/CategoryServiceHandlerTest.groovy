package academy.ingress.mscategory.service

import academy.ingress.mscategory.dao.entity.Category
import academy.ingress.mscategory.dao.repository.CategoryRepository
import academy.ingress.mscategory.exception.NotFoundException
import academy.ingress.mscategory.model.requests.CreateCategoryRequest
import academy.ingress.mscategory.model.requests.UpdateCategoryRequest
import academy.ingress.mscategory.service.abstracts.CategoryService
import academy.ingress.mscategory.service.concretes.CategoryServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static academy.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER

class CategoryServiceHandlerTest extends Specification {
    private CategoryRepository categoryRepository
    private CategoryService categoryService
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()

    def setup() {
        categoryRepository = Mock()
        categoryService = new CategoryServiceHandler(categoryRepository)
    }

    def "CategoryServiceHandlerTest createCategory - case success"() {
        given:
        def category = random.nextObject(CreateCategoryRequest)
        def entity = random.nextObject(Category)
        when:
        categoryService.createCategory(category)

        then:
        1 * categoryRepository.save(CATEGORY_MAPPER.buildCategoryFromCreateRequest(category))
                >> entity

    }

    def "CategoryServiceHandlerTest updateCategory - case success"() {
        given:
        def id = random.nextLong()
        def updateCategoryRequest = random.nextObject(UpdateCategoryRequest)
        def entity = random.nextObject(Category)
        when:
        categoryService.updateCategory(id, updateCategoryRequest)

        then:
        1 * categoryRepository.findCategoryById(id) >> Optional.of(entity)
        1 * categoryRepository.save(CATEGORY_MAPPER.rebuildCategoryFromUpdateRequest(entity, updateCategoryRequest))
                >> entity
    }

    def "CategoryServiceHandlerTest updateCategory - case category not found"() {
        given:
        def id = random.nextLong()
        def updateCategoryRequest = random.nextObject(UpdateCategoryRequest)
        when:
        categoryService.updateCategory(id, updateCategoryRequest)

        then:
        1 * categoryRepository.findCategoryById(id) >> Optional.empty()
        0 * categoryRepository.save(_)
        NotFoundException e = thrown()
        e.message == "requested category not found"
    }

    def "CategoryServiceHandlerTest deleteCategory - case success"() {
        given:
        def id = random.nextLong()

        when:
        categoryService.deleteCategory(id)

        then:
        1 * categoryRepository.deleteById(id)
    }

}