package com.shopme.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.admin.repository.CategoryRepository;
import com.shopme.admin.service.CategoryService;
import com.shopme.common.entity.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository repository;

	@Override
	public List<Category> findAll() {
		return (List<Category>) repository.findAll();
	}

	@Override
	public List<Category> listCategoriesUsedInForm() {
		List<Category>categoriesUsedInForm=new ArrayList<Category>();
		Iterable<Category>categoriesInDB=repository.findAll();
		
		for(Category category:categoriesInDB) {
			if(category.getParent()==null) {
				//categoriesUsedInForm.add(new Category(category.getName()));
				categoriesUsedInForm.add(new Category(category.getId(), category.getName()));
				Set<Category> children = category.getChildren();
				for(Category subCategory:children) {
					String name="--"+subCategory.getName();
					categoriesUsedInForm.add(new Category(subCategory.getId(),name));
					listChildren(categoriesUsedInForm,subCategory,1);
				}
			}
		}
		
		return categoriesUsedInForm;
	}
	
	private void listChildren(List<Category>categoriesUsedInForm,Category parent,int subLevel) {
		int newSubLevel=subLevel+1;
		Set<Category> children = parent.getChildren();
		for(Category subCategory:children) {
			String name="";
			for(int i=0;i<newSubLevel;i++) {
			name+="--";
			}
			name+=subCategory.getName();
			categoriesUsedInForm.add(new Category(subCategory.getId(),name));
			
			listChildren(categoriesUsedInForm,subCategory, newSubLevel);
		}
	}

	@Override
	public Category save(Category category) {
		return repository.save(category);
	}

}
