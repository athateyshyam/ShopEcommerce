package com.shopme.admin.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.admin.repository.CategoryRepository;
import com.shopme.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CategoryServiceImplTest {
	@MockBean
	private CategoryRepository repository;
	
	@InjectMocks
	private CategoryServiceImpl service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer id=null;
		String name="Computers";
		String alias="abcd";
		Category category=new Category(id,name,alias);
		Mockito.when(repository.findByName(name)).thenReturn(category);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("Duplicate");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer id=null;
		String name="ABCD";
		String alias="computers";
		Category category=new Category(id,name,alias);
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInNewModeReturnOk() {
		Integer id=null;
		String name="ABCD";
		String alias="computers";
		
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("OK");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer id=1;
		String name="Computers";
		String alias="abcd";
		Category category=new Category(2,name,alias);
		Mockito.when(repository.findByName(name)).thenReturn(category);
		Mockito.when(repository.findByAlias(alias)).thenReturn(null);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("Duplicate");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer id=1;
		String name="ABCD";
		String alias="computers";
		Category category=new Category(2,name,alias);
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("DuplicateAlias");
	}
	
	@Test
	public void testCheckUniqueInEditModeReturnOk() {
		Integer id=1;
		String name="ABCD";
		String alias="computers";
		Category category=new Category(id,name,alias);
		Mockito.when(repository.findByName(name)).thenReturn(null);
		Mockito.when(repository.findByAlias(alias)).thenReturn(category);
		String result=service.checkUnique(id, name, alias);
		assertThat(result).isEqualTo("OK");
	}

}
