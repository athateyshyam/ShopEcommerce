package com.shopme.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.admin.exception.CategoryNotFoundException;
import com.shopme.admin.repository.CategoryRepository;
import com.shopme.admin.service.CategoryService;
import com.shopme.common.entity.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repository;
	
	@Override
	public List<Category> findAll() {
		// return (List<Category>) repository.findAll();
		List<Category> rootCategories = repository.findRootCategories();
		return listHierarchicalCategories(rootCategories);
	}

	private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<Category>();
		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyAll(rootCategory));
			Set<Category> children = rootCategory.getChildren();
			for(Category subCategory:children) {
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyAll(subCategory,name));
				listSubHierarchicalCategories(hierarchicalCategories,subCategory,1);
			}

		}

		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,Category parent,int subLevel) {
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel + 1;
		for(Category subCategory:children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyAll(subCategory,name));
			listSubHierarchicalCategories(hierarchicalCategories,subCategory,subLevel);
		}
	}

	@Override
	public List<Category> listCategoriesUsedInForm() {
		List<Category> categoriesUsedInForm = new ArrayList<Category>();
		Iterable<Category> categoriesInDB = repository.findAll();

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				// categoriesUsedInForm.add(new Category(category.getName()));
				categoriesUsedInForm.add(new Category(category.getId(), category.getName()));
				Set<Category> children = category.getChildren();
				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUsedInForm.add(new Category(subCategory.getId(), name));
					listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
				}
			}
		}

		return categoriesUsedInForm;
	}

	private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUsedInForm.add(new Category(subCategory.getId(), name));

			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}
	}

	@Override
	public Category save(Category category) {
		return repository.save(category);
	}

	@Override
	public Category get(Integer id) throws CategoryNotFoundException {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
	}

	@Override
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew=(id==null || id==0);
		Category category=repository.findByName(name);
		if(isCreatingNew) {
			if(category!=null) {
				return "Duplicate";
			}else {
				Category categoryByAlias=repository.findByAlias(alias);
				if(categoryByAlias!=null)
					return "DuplicateAlias";
			}
		}else {
			if(category!=null && category.getId()!=id) {
				return "Duplicate";
			}
			Category categoryByAlias=repository.findByAlias(alias);
			if(categoryByAlias!=null && categoryByAlias.getId()!=id)
				return "DuplicateAlias";
		}
		return "OK";
	}
}
