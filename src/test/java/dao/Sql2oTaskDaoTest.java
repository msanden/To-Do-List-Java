package dao;

import models.Task;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;


/*
 * Our @Before annotation runs before every test, and
 * our @After annotation runs after every test.
 * ----------------------------------------------------------------------
 * We create a setUp() method we can call to prep the database for us.
 * Then, we connect to JDBC. JDBC stands for Java Database Connectivity.
 * This is an internal API that allows us to connect to a database.
 * Tell H2 to write the test database into memory, using the sql file we just created,
 * which preps the database for us.
 * Instruct the code to make a new Sql2o object so we can run methods on it,
 * such as open(), close() and more. This makes our database available in our entire file.
 * */

public class Sql2oTaskDaoTest {

    private Sql2oTaskDao taskDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql2oTaskDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingTaskSetsId() throws Exception {
        Task task = setUpNewTask();
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId());
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = setUpNewTask();
        taskDao.add(task);
        Task foundTask = taskDao.findById(task.getId());
        assertEquals(task, foundTask);
    }

    @Test
    public void addedTasksAreReturnedFromgetAll() throws Exception {
        Task task = setUpNewTask();
        taskDao.add(task);
        assertEquals(1,taskDao.getAll().size());
    }

    @Test
    public void noTasksReturnsEmptyList() throws Exception {
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void updateChangesTaskContent() throws Exception {
        String initialDescription = "mow lawn";
        Task task = new Task(initialDescription, 1);
        taskDao.add(task);
        taskDao.update(task.getId(), "wash dishes", 1);
        Task updatedTask = taskDao.findById(task.getId());
        assertNotEquals(initialDescription, updatedTask.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectTask() throws Exception{
        Task task = setUpNewTask();
        taskDao.add(task);
        taskDao.deleteById(task.getId());
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception{
        Task task = setUpNewTask();
        Task otherTask = new Task("buy groceries", 2);
        taskDao.add(task);
        taskDao.add(otherTask);
        int daoSize = taskDao.getAll().size();
        taskDao.clearAllTasks();
        assertEquals(0, taskDao.getAll().size());
    }

    @Test
    public void categoryIdIsReturnedCorrectly() throws Exception {
        Task task = setUpNewTask();
        int originalCatId = task.getCategoryId();
        taskDao.add(task);
        assertEquals(originalCatId, taskDao.findById(task.getId()).getCategoryId());
    }

    public Task setUpNewTask(){
        return new Task("mow lawn", 1);
    }

}
