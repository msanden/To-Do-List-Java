package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/* --------------------------------------------------------------------------------
* Because we're inserting an additional layer of programming between our
* data model and database server: Our DAO, The Task.java and TaskTest.java now
* deal only with the basic POJO (Plain Old Java Object) getter and setter methods.
* The other methods are tested in the DAO test file.
* ---------------------------------------------------------------------------------
* */

public class Task {

    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;

    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt()  {
        return createdAt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*
    * Comparing Objects from a Database Using equals() and hashCode()
    * hashCode() calculates, on a memory-based level, whether or not two objects are the same.
    * */

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() ) return false;
        Task task = (Task) o;
        return completed == task.completed &&
                id == task.id &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, completed, id);
    }

}
