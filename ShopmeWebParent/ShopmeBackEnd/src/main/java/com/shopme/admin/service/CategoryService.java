package com.shopme.admin.service;

import java.util.List;

import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.common.entity.Category;

public interface CategoryService {
	public List<Category> findAll(String sortDir);
	public List<Category>listCategoriesUsedInForm();
	public Category save(Category category);
	public Category get(Integer id) throws CategoryNotFoundException;
	public String checkUnique(Integer id,String name,String alias);
}
