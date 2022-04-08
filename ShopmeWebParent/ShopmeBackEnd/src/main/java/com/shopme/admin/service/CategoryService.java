package com.shopme.admin.service;

import java.util.List;

import com.shopme.common.entity.Category;

public interface CategoryService {
	public List<Category> findAll();
	public List<Category>listCategoriesUsedInForm();
}
