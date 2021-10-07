# Registration

> ## Success case

1. ✅ Receives a request of type **POST** in the route **/oauth/token**
2. ✅ Validates mandatory **email** and **password** data
3. ✅ Returns **200** status and an expiry date token

> ## Exceptions

1. ✅ Returns error **400** if email and password are not informed
2. ✅ Returns **401** error if credentials are invalid
3. ✅ Returns **500** error if there is an internal server error