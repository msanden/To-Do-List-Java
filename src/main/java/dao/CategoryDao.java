package dao;

import models.Category;
import models.Task;
import java.util.List;

public interface CategoryDao {

    void add(Category category);

    Category findById(int id);

    List<Category> getAll();

    List<Task> getAllTasksByCategory(int categoryId);

    void update(int id, String name);

    void deleteById(int id);
    void clearAllCategories();

}
