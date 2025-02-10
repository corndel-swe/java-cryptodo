<p align="center">
  <img width="200px" src="/public/x_logo.png" />
</p>

# Cryptodo

Welcome to Cryptodo! The security conscious todo list you know you can trust.

Sort of.

## Hashing libraries

This workshop makes frequent use of hashing. The recommended libraries for each
language are given below:

| language   | library                                                      |
|------------|--------------------------------------------------------------|
| JavaScript | [bcrypt](https://www.npmjs.com/package/bcrypt#usage)         |
| Java       | [bcrypt](https://github.com/jeremyh/jBCrypt)                 |
| Python     | [bcrypt](https://github.com/pyca/bcrypt#usage)               |
| C#         | [bcrypt](https://github.com/BcryptNet/bcrypt.net#how-to-use) |

## Getting started

1. Clone the repo so you have it locally.

2. Run the following to get the application's dependencies:

    ```bash
    ./mvnw clean
    ./mvnw compile
    ```

3. Run the following to migrate and seed the database using Flyway. If at any point during the workshop you need to
   reset your DB, this command will also do so:

    ```bash
    ./mvnw flyway:clean
    ./mvnw flyway:migrate
    ```

4. Run the following to start the development server:

    ```bash
    ./mvnw exec:java -Dexec.mainClass=com.corndel.cryptodo.App
    ```

5. Visit [localhost:5123](http://localhost:5123/todos) in your browser to see the home page.

## Exploring the project

The website has 3 pages:

- `/` the home page

- `/users/register` a sign up page

- `/todos` a password protected list of todos

Try the following:

1. Inspect the schema and seed SQL files within `src/main/resources/db/migration` and choose a username and password
   combination you’d like to log in with.

2. Visit `/todos` and input the username and password, you should now see their
   todos.

3. Close and re-open your browser to clear the session, allowing you to log in
   as another user.

4. Visit `/users/register` and create a new user using the form - take a look at your new
   user in the database

## Moving on

Once you've had a look around, move on to `TODO.md` to find your instructions.
