# Readme

## Goal

The application should process sign-up request, received as a plain form POST
http request. The requestor will be notified by a confirmation email. The
confirmation is send with a http GET

  * form http POST request

  * url http GET request

Those requests are approved or denied by the administrator

## Deployment
Compile the project with command mvn package. This will build the web-application.war file in target folder. 
Copy web-application.war file in jboss7\standalone\deployments folder and start the server if not already started.

## Running the application
The application can be accessed by the URL http://localhost:8080/web-application/login.jsf

## Posting signup requests
The URL used to post signup request to the server is http://localhost:8080/web-application/rest/signupRequest/registerSignupRequest
In request header set Content-Type=application/json.
Set request body and fill it with appropriate values as {"status":"","firstName":"","lastName":"","email":"","company":"","referer":"","datetime":1322421985478,"phoneNumber":""}

