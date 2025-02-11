package com.backoffice.moffice.category.service;

import com.backoffice.moffice.category.dto.CategoryConverter;
import com.backoffice.moffice.category.dto.CategoryDTO;
import com.backoffice.moffice.category.dto.CategorySearchDTO;
import com.backoffice.moffice.category.mapper.CategoryMapper;
import com.backoffice.moffice.category.model.Category;
import com.backoffice.moffice.category.model.CategoryForExcel;
import com.backoffice.moffice.common.dto.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryConverter categoryConverter;

    public Category selectCategoryById(Long indexNo){
        log.info("service indexNo: {}", indexNo);
        return categoryMapper.selectCategoryById(indexNo);
    }

    private void checkIfExistsOrException(Category category) throws NotFoundException {
        log.info("service checkIfExistsOrException category: {}", category);
        if( category == null){
            throw new NotFoundException( "카테고리를 찾을 수 없습니다." );
        }
    }

    private void selectCategoryByIdOrException(Long indexNo) throws NotFoundException {
        Category category = this.selectCategoryById(indexNo);
        log.info("service selectCategoryByIdOrException category: {}", category);
        checkIfExistsOrException(category);
    }
    public Category selectCategoryByName(String categoryName) {
        return categoryMapper.selectCategoryByName(categoryName);
    }
    public List<Category> selectCategoryList(CategorySearchDTO categorySearchDTO){
        return categoryMapper.selectCategoryList(categorySearchDTO);
    }
    public List<Category> selectCategoryListByUseYn(CategorySearchDTO categorySearchDTO){
        categorySearchDTO.setUseYn("Y");
        return categoryMapper.selectCategoryListByUseYn(categorySearchDTO);
    }

    public CategorySearchDTO selectCategoryListWithPaging(CategorySearchDTO categorySearchDTO){
        //총 개수 조회
        int totalCount = categoryMapper.getTotalCount(categorySearchDTO);
        categorySearchDTO.setTotalCount(totalCount);

        //페이징 정보 계산
        categorySearchDTO.setPaging();

        List<Category> list = categoryMapper.selectCategoryListWithPaging(categorySearchDTO);
        categorySearchDTO.setList(list);

        return categorySearchDTO;
    }

    public List<CategoryForExcel> selectCategoryListForExcel(CategorySearchDTO categorySearchDTO){
        return categoryMapper.selectCategoryListForExcel(categorySearchDTO);
    }

    @Transactional
    public int createCategory(Category category) {
        return categoryMapper.insertCategory(category);
    }
    @Transactional
    public int deleteCategory(Long indexNo){
        return categoryMapper.deleteCategory(indexNo);
    }


    @Transactional
    public int saveCategory(CategoryDTO categoryDTO) {
        int affectedRowCount = 0;
        log.info("service categoryDTO: {}", categoryDTO);

        // DTO를 모델로 변환
        Category category = categoryConverter.convertDtoToModel(categoryDTO);

        // 중복 체크
        Category existingCategories = selectCategoryByName(category.getCategoryName());
        if (existingCategories != null) {
            log.warn("중복된 카테고리가 존재합니다: {}", existingCategories);
            return 0; // 중복된 경우 추가 작업 없음
        }

        // 중복이 없으면 카테고리 생성
        affectedRowCount = createCategory(category);
        return affectedRowCount;
    }

    @Transactional
    public int updateCategory(CategoryDTO categoryDTO){
        log.info("service categoryDTO: {}", categoryDTO);

        // DTO를 모델로 변환
        Category category = categoryConverter.convertDtoToModel(categoryDTO);
        return categoryMapper.updateCategory(category);
    }


    @Transactional
    public int removeCategory(Long indexNo) throws NotFoundException {

        // 카테고리 존재 여부 확인
        this.selectCategoryByIdOrException(indexNo);

        int affectedRowCount = this.deleteCategory(indexNo);
        if( affectedRowCount == 0){
            throw new RuntimeException("카테고리 삭제에 실패했습니다.");
        }
        // TODO 사용여부 체크
/*        // 연관된 상품이 있는지 확인
        if (categoryMapper.hasRelatedProducts(indexNo)) {
            throw new IllegalStateException("해당 카테고리에 연결된 상품이 존재합니다.");
        }*/
        return affectedRowCount;
    }


}
