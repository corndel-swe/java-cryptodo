package com.corndel.cryptodo;

import com.corndel.cryptodo.controllers.AuthController;
import com.corndel.cryptodo.controllers.TodoController;
import com.corndel.cryptodo.controllers.UserController;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.FileRenderer;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {

    public static void main(String[] args) {
        javalin().start(5123);
    }

    public static Javalin javalin() {
        return Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add("src/main/resources/public", Location.EXTERNAL);
            javalinConfig.fileRenderer(getFileRenderer());
            javalinConfig.router.apiBuilder(getEndpointGroup());
        });
    }

    private static EndpointGroup getEndpointGroup() {
        return () -> {
            path("/", () -> get(context -> context.render("/index.html")));
            before("/todo", AuthController::protect);
            path("/todo", () -> {
                post(TodoController::create);
                get(TodoController::renderTodos);
                get("/new", TodoController::renderCreateTodo);
            });
            path("/user", () -> {
                post(UserController::create);
                get("/register", UserController::register);
            });
        };
    }

    private static FileRenderer getFileRenderer() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        return new JavalinThymeleaf(engine);
    }
}
