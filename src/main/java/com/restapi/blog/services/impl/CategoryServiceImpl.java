package com.restapi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.blog.dto.CategoryDto;
import com.restapi.blog.entity.Category;
import com.restapi.blog.exceptions.CategoryNotFoundException;
import com.restapi.blog.exceptions.NoSuchRecordFoundException;
import com.restapi.blog.repositories.CategoryRepo;
import com.restapi.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		return mapper.map(categoryRepo.save(category), CategoryDto.class) ;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category  =categoryRepo.findById(categoryId).orElseThrow(() -> new 
				     CategoryNotFoundException("Category ", "Id : ", categoryId));
	    category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDiscription(categoryDto.getCategoryDiscription());
		
		return mapper.map(categoryRepo.save(category), CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		categoryRepo.findById(categoryId).orElseThrow(() -> new 
			     CategoryNotFoundException("Category ", "Id : ", categoryId));
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new 
			         CategoryNotFoundException("Category ", "Id : ", categoryId));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> getAllCategory =  categories.stream().map(cat -> mapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		
		if(getAllCategory.isEmpty()) throw new NoSuchRecordFoundException("No Record Found");
		else return getAllCategory;
	}

}
