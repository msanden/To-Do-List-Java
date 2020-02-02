package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void NewTaskObjectGetsCorrectlyInstantiated_true() throws Exception {
        Task task = setupNewTask();
        assertEquals(true, task instanceof Task);
    }

    @Test
    public void TaskInstantiatesWithDescription_true() throws Exception {
        Task task = setupNewTask();
        assertEquals("Wash Dishes", task.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAtInstantiation() throws Exception {
        Task task = setupNewTask();
        assertEquals(false, task.getCompleted());
    }


    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Task task = setupNewTask();
        assertEquals(LocalDateTime.now().getDayOfWeek(), task.getCreatedAt().getDayOfWeek());
    }

    public Task setupNewTask() {
        return new Task("Wash Dishes", 1);
    }

}