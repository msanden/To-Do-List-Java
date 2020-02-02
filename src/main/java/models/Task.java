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
    private int categoryId;

    public Task(String description, int categoryId) {
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.categoryId = categoryId;
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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }



    public LocalDateTime getCreatedAt()  {
        return createdAt;
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
                categoryId == task.categoryId &&
                Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, completed, id, categoryId);
    }

}
