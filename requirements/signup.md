# Registration

> ## Success case

1. ✅ Receives a request of type **POST** in the route **/api/v1/signup**
2. ✅ Validates mandatory data **name**, **email**, **password** and **passwordConfirmation**
3. ✅ Validates that **password** and **passwordConfirmation** are the same
4. ✅ Validate that the **email** field is a valid email address
5. ✅ **Validates** if there is already a user with the email provided
6. ✅ Generates a **encrypted** password (this password cannot be decrypted)
7. ✅ **Creates** an account for the user with the data entered, **replacing** the password with the encrypted password

> ## Exceptions

1. ✅ Returns **400** error if name, email, password or passwordConfirmation are not provided by the client
2. ✅ Returns error **422** if password and passwordConfirmation are not the same
3. ✅ Returns **422** error if the email field is an invalid email
4. ✅ Returns error **409** if the email provided is already in use
5. ✅ Returns **500** error if there is an internal server error