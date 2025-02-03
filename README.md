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
| ---------- | ------------------------------------------------------------ |
| JavaScript | [bcrypt](https://www.npmjs.com/package/bcrypt#usage)         |
| Java       | [bcrypt](https://github.com/patrickfav/bcrypt)               |
| Python     | [bcrypt](https://github.com/pyca/bcrypt#usage)               |
| C#         | [bcrypt](https://github.com/BcryptNet/bcrypt.net#how-to-use) |

## Getting started

1. Clone the repo so you have it locally

2. Run `npm install` to get the dependencies

3. Run `npm run db:reset` to migrate and seed the database

4. Run `npm run dev` to start the development server

5. Visit [localhost:5123](localhost:5123) in your browser to see the home page

## Exploring the project

The website has only 3 pages:

- `/` the home page

- `/users/new` a sign up page

- `/todos` a password protected list of todos

Try the following:

1. Inspect the database and choose a username and password combination you'd
   like to log in as

1. Visit `/todos` and input the username and password, you should now see their
   todos

1. Close and re-open your browser to clear the session, allowing you to log in
   as another user

1. Visit `/users/new` and create a new user using the form - take a look at your new
   user in the database

## Moving on

Once you've had a look around, move on to `TODO.md` to find your instructions.
