import dao.Sql2oCategoryDao;
import models.Category;
import models.Task;
import dao.Sql2oTaskDao;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {

    public static void main(String[] args) {

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oTaskDao taskDao = new Sql2oTaskDao(sql2o);
        Sql2oCategoryDao categoryDao = new Sql2oCategoryDao(sql2o);

        //get: show all tasks
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("allCategories", allCategories);
            List<Task> tasks = taskDao.getAll();
            model.put("tasks", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("categories/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> categories = categoryDao.getAll();
            model.put("categories", categories);
            return new ModelAndView(model,"category-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/categories", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Category newCategory = new Category(name);
            categoryDao.add(newCategory);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tasks
        get("/categories/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            categoryDao.clearAllCategories();
            taskDao.clearAllTasks();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/tasks/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            taskDao.clearAllTasks();
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/categories/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToFind = Integer.parseInt(request.params("id"));
            Category foundCategory = categoryDao.findById(idOfCategoryToFind);
            model.put("category", foundCategory);
            List<Task> allTasksByCategory = categoryDao.getAllTasksByCategory(idOfCategoryToFind);
            model.put("tasks", allTasksByCategory);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "category-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category
        get("/categories/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editCategory", true);
            Category category = categoryDao.findById(Integer.parseInt(req.params("id")));
            model.put("category", category);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "category-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            categoryDao.update(idOfCategoryToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/categories/:category_id/tasks/:task_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("task_id"));
            taskDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/tasks/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> categories = categoryDao.getAll();
            model.put("categories", categories);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/tasks", (request, response) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            String description = request.queryParams("description");
            int categoryId = Integer.parseInt(request.queryParams("categoryId"));
            Task newTask = new Task(description,categoryId);
            taskDao.add(newTask);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/categories/:category_id/tasks/:task_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("task_id"));
            Task foundTask = taskDao.findById(idOfTaskToFind);
            int idOfCategoryToFind = Integer.parseInt(req.params("category_id"));
            Category foundCategory = categoryDao.findById(idOfCategoryToFind);
            model.put("task", foundTask);
            model.put("category", foundCategory);
            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "task-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryDao.getAll();
            model.put("categories", allCategories);
            Task task = taskDao.findById(Integer.parseInt(req.params("id")));
            model.put("task", task);
            model.put("editTask", true);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/tasks/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToDelete = Integer.parseInt(req.params("id"));
            taskDao.deleteById(idOfTaskToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task
        get("/tasks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("id"));
            Task foundTask = taskDao.findById(idOfTaskToFind);
            model.put("task", foundTask);
            return new ModelAndView(model, "task-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/tasks/:id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
            Task editTask = taskDao.findById(idOfTaskToEdit);
            model.put("editTask", editTask);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/tasks/:id", (request, response) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int taskToEditId = Integer.parseInt(request.params("id"));
            String newContent = request.queryParams("description");
            int newCategoryId = Integer.parseInt(request.queryParams("categoryId"));
            taskDao.update(taskToEditId, newContent, newCategoryId);
            response.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}
