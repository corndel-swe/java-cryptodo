# Todo

## Secure the database

The passwords currently in the database aren't hashed. They are stored in plaintext!

- [ ] In `src/main/java/com/corndel/utils`, you'll find a class called `PasswordHasher`.
    - This class's responsibility will be to **hash the passwords once so they are not plaintext.**

- [ ] In the main method, you will want to get all the users currently stored in the database.
    - Don't reinvent the wheel, make use of the `UserRepository` and `User` classes provided.

- [ ] Loop through the list of users and use `BCrypt.hashpw()` to hash each user's password.

- [ ] Create an SQL statement to update each user's plaintext password in the database with the newly hashed password.

- [ ] Check the database to make sure the passwords appear to be hashed and are not in plaintext.

> [!WARNING]
> This only needs to run once!
> Running this script twice will hash the hash of the password! If you want to
> run it again, you should run both Flyway commands in the README to reset the passwords to their
> raw plaintext form first.

## Update POST /users

The `POST /users` endpoint is still storing plaintext passwords in the database. Let's fix that.

- [ ] Find the `UserController` class.

- [ ] Review the `UserController.create()` method to understand what it is doing.

- [ ] Before the password is stored in the database, make sure to hash it with `BCrypt.hashpw()`.

- [ ] Manually test the `POST /users` endpoint to make sure it is hashing before creating the user.
    - Fill out the new user form and check the new row in the database.
    - Try and view their todos.

## Update GET /todos

The `GET /todos` endpoint is broken because it is now trying to compare raw passwords in the request with hashed
passwords in the database.

- [ ] Try viewing the todos of a user to see that it's broken.

- [ ] Find the `TodoController` class and review the `TodoController.renderTodos()` method to understand what it is
  doing.

- [ ] Instead of comparing the database password to the request password directly, use the `BCrypt.checkpw()` method.

- [ ] Try viewing the user's todos again - it should work now.
    - If it is not, make sure your passwords have only been hashed once.

## Add POST /todos

Finally, let's add a new page allowing the logged-in user to create a new todo.

- [ ] In `src/main/resources/templates/todos`, view the `new.html` file. Add a new method to the `TodoController` to
  handle the rendering of this HTML file.
    - This form is for adding new to-do items, with a field for the description and a submit button.

- [ ] Hook up a new GET endpoint to the newly created method in the `App.getEndpointGroup()` method.
    - This endpoint should be `GET /todo/new` and will call the method created above on the `TodoController`.

- [ ] In the `TodoController` class, add a `create` method.
    - It should verify the username and password of the creating user, much in the same way that `GET /todo` and the
      `TodoController.renderTodos()` do (in fact, you could probably copy some of the code).
    - Once the user is verified, the method will need to add a todo to the database.
        - Don't reinvent the wheel, make use of the `UserRepository`, `TodoRepository`, and `Todo` classes provided.
            - What properties does a Todo need? What methods can you use from the repositories to get those properties?

> [!IMPORTANT]
>
> If authentication fails, set the status to `401` and set the `WWW-Authenticate` header to `Basic realm="cryptodo"`.
> This is what makes the browser show the login dialogue.

- [ ] Now, hook up a new POST endpoint to the newly created method in the `App.getEndpointGroup()` method.
    - This endpoint should be `POST /todo` and will call the `create` method on the `TodoController`.

Try out your form. Make sure the todo gets created and is correctly associated with the logged-in user.

## :gem: Extension: middleware

In a Javalin application, before handlers are used to execute code before processing a context, similar to middleware in
other languages, where they can handle tasks like authentication, logging, or modifying the context.

If you get through everything and need an extra challenge, notice that the code which checks the user password gets
repeated in `TodoController.renderTodos()` and again in `TodoController.create()`. These are both within the "/todo"
endpoints.

This logic could be extracted to a [Before handler](https://javalin.io/documentation#before-handlers).

If we created an `AuthController` and named our method `protect`, it might look like this:

```js
public class AuthController {
    public static void protect(Context context) {
        // TODO: AUTHENTICATE USER
    }
}
```

Then we could use it like this:

```js
public class App {

    private static EndpointGroup getEndpointGroup() {
        return () -> {
            before("/todo", AuthController::protect);
            path("/todo", () -> {
                post(TodoController::create);
                get(TodoController::renderTodos);
            });
        };
    }

   // Other code, methods and endpoints are removed for brevity.
}
```

This is a powerful pattern which makes it easy to password-protect any endpoint in the app, we simply add the before
handler to run before the other handlers.

---