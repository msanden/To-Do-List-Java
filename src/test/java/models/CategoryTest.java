package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void NewCategoryObjectGetsCorrectlyInstantiated_true() throws Exception {
        Category category = setUpNewCategory();
        assertEquals(true, category instanceof Category);
    }

    @Test
    public void CategoryInstantiatesWithDescription_true() throws Exception {
        Category category = setUpNewCategory();
        assertEquals("Work", category.getName());
    }

    public Category setUpNewCategory() {
        return new Category("Work");
    }
}