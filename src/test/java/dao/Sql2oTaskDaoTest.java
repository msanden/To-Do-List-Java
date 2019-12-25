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

}
