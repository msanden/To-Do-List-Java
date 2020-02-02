package dao;

import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

import models.Category;

public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao categoryDao;
    private Sql2oTaskDao taskDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "","");
        categoryDao = new Sql2oCategoryDao(sql2o);
        taskDao = new Sql2oTaskDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCategoriesSetsId() throws Exception {
        Category category = new Category("Work");
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception {
        Category category = new Category("Work");
        categoryDao.add(category);
        Category foundCategory = categoryDao.findById(category.getId());
        assertEquals(category, foundCategory );
    }

    @Test
    public void addedCategoriesAreReturnedFromgetAll() throws Exception {
        Category category = new Category("work");
        categoryDao.add(category);
        assertEquals(1, categoryDao.getAll().size());
    }

    @Test
    public void noCategoriesReturnsEmptyList() throws Exception {
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryName() throws Exception {
        String initialName = "work";
        Category category = new Category(initialName);
        categoryDao.add(category);
        categoryDao.update(category.getId(), "school");
        Category updatedCategory = categoryDao.findById(category.getId());
        assertNotEquals(initialName, updatedCategory.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectCategory() throws Exception{
        Category category = new Category("work");
        categoryDao.add(category);
        categoryDao.deleteById(category.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception{
        Category category = new Category("Work");
        Category otherCategory = new Category("School");
        categoryDao.add(category);
        categoryDao.add(otherCategory);
        int daoSize = categoryDao.getAll().size();
        categoryDao.clearAllCategories();
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Task newTask = new Task("mow the lawn", categoryId);
        Task otherTask = new Task("pull weeds", categoryId);
        Task thirdTask = new Task("trim hedge", categoryId);
        taskDao.add(newTask);
        taskDao.add(otherTask); //we are not adding task 3 so we can test things precisely.
        assertEquals(2, categoryDao.getAllTasksByCategory(categoryId).size());
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newTask));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherTask));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdTask)); //things are accurate!
    }

    public Category setUpNewCategory() {
        return new Category("School");
    }
}