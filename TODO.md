# Todo

// CHARLIE-TODO: `bcrypt.hash()`?

## Secure the database

The passwords currently in the database aren't hashed!

- [x] In `src/main/java/com/corndel/utils` you'll find a class called `PasswordHasher`

- [x] In this file, write your code to get all of the users currently store in the database you may want to review both the User model and UserPrepository. .

- [x] Loop through the list, and use `BCrypt.withDefaults().hashToString()` to hash each password.

- [x] Replace each user's plaintext password in the database with the hashed
      password.

- [x] Check the database to make sure the passwords appear to be hashed.

> [!WARNING]
>
> running this script twice will hash the hash of the password! If you want to
> run it again, you should run both flyway commands on the README to reset the passwords to their
> raw plain text form, first.

## Update POST /users

The `POST /users` endpoint is still storing raw passwords in the database. Let's
fix that.

- [x] Find the `UserController` class

- [x] Read the `UserController.create()` function to understand what it is doing

- [x] Before the password is stored in the database, make sure to hash it with
      `bcrypt.hash()`

- [x] Manually test the `POST /users` endpoint to make sure it is hashing before
      creating the user. Fill out the new user form and check out the new row in
      the database.

## Update GET /todos

The `GET /todos` endpoint is broken, because it is now trying to compare raw
passwords in the request with hashed passwords in the database.

- [ ] Try viewing the todos of a user to see that it's broken

- [ ] Find the `TodoController` class

- [ ] Read the `TodoController.renderTodos` function to understand what it is doing

- [ ] Instead of comparing the database password to the request password
      directly, use `bcrypt.compare()`

- [ ] Try viewing the user's todos again - it should work now

## Add POST /todos

Finally, let's add a new page allowing the logged in user to create a new todo.

- [ ] In `src/main/resources/templates/todos` make a `new.html` file and create a form which adds a new
      todo. It should make a `POST` request to the `/todo` endpoint.

- [ ] Hook up a new endpoint in the `App.getEndpointGroup()` method. This Endpoint should be to the `GET /todo/new` and it should render the new html file.
      which renders the form.

- [ ] In the `TodoController` class, add a `create` method. It should verify the
      username and password of the creating user, much in the same way that
      `GET /todo` and the `TodoController.renderTodos()` does (in fact, you could probably copy some of the code).

> [!IMPORTANT]
>
> If authentication fails, set the status to `401` and set the
> `WWW-Authenticate` header to `Basic realm="cryptodo"`. This is what makes the
> browser show the login dialogue.

Try out your form. Make sure the todo gets created and is correctly associated
with the logged in user.

## :gem: Extension: middleware

// CHARLIE-TODO: IS THIS POSSIBLE WITH JAVALIN?

If you get through everything and need an extra challenge, notice that the code
which checks the user password gets repeated in `renderUserTodos` and again in
`createTodo`.

This could be extracted to a
[middleware function](https://expressjs.com/en/guide/using-middleware.html).

If we named our function `protect`, it might look like this:

```js
// middleware/protect.js
const protect = (req, res, next) => {
  // TODO: check the password
}
```

Then we could use it like this:

```js
// routes/todo.routes.js
app.get('/todos', protect, renderUserTodos)
app.post('/todos', protect, createTodo)
```

This is a powerful pattern which makes it easy to password-protect any endpoint
in the app.
