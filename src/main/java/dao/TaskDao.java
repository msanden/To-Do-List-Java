package dao;

import models.Task;
import java.util.List;

/*
* ----------------------------------------------------------------------------
* These are the behaviors we need to see here in order to build out our app.
*
* In order to add Tasks to our database we'll need an add() method.
* This method won't need to return anything.
* In order to also retrieve all Tasks we need a getAll() method that retrieves a list of Task type.
* Additionally, we’ll want to be able to retrieve a single Task and display it.
* This means we should probably also have a findById() method that returns a single Task type.
* ------------------------------------------------------------------------------
*/

public interface TaskDao {

    // LIST
    List<Task> getAll();

    // CREATE
    void add(Task task);

    // READ
    Task findBy(int id);

    // UPDATE
    // void update(int id, String content);

    // DELETE
    // void deleteById(int id);
    // void clearAllTasks();

}
