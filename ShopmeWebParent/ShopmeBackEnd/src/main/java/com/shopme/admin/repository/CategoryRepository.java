package com.shopme.admin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}