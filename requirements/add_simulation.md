# Registration

> ## Success case

1. ✅ Receives a request of type **POST** in the route **/api/v1/simulation**
2. ✅ Validates mandatory data:
   * origin
   * destination
   * planId (valid id of a plan acquired by a route that will make this data available)
   * timeInMinutes (Connection time in minutes)
3. ✅ Validates that the planId must exist in the database
4. ✅ Validates if the simulation is calculating with the correct percentage
5. ✅ Returns status 201 and an object with simulation data returning the calculated value

> ## Exceptions

1. ✅ Returns **400** error if the data is not informed
2. ✅ Returns **406** error if the plan is not found in the database
3. ✅ Returns **401** error if a valid token is not entered
5. ✅ Returns **500** error if there is an internal server error