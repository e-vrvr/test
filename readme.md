# Blog server

## Important note
When you are done share the solution with us as a private repository or send an archive back to us. Do not post it in publicly available code repositories!

## Business story
A revolutionary startup from _Lalaland_ is preparing to beat _Wordpress_ and _Wix_ forever. Their product already has the essential features, like creating blog posts and even getting them! They are preparing to add a great feature of sending notifications to the users when their favorite author adds a new piece of content. The service responsible for sending these notifications has been already prepared by other team and is working. Your job is to put it all together: **add a subscription feature to the system**, and take care of **invoking the notification service when the author adds something new**. The one important thing to notice is that the current code of blog posting service has been written in quite a rush (everybody knows it well: fresh startups, bustling with ideas, always short on time) so you've also been assigned a task to **refactor it and reorganize the codebase as well as to write appropriate tests** according to your expertise.

# Service description

## 1. Blog posting service

### Description
An existing service which you will change during your task. It is responsible for posting and getting blog posts. This service is exposed on port 8080 by default.

### Build & Deployment

    (...)/blog-server$ mvn clean package
    (...)/blog-server/target$ java -jar blog-server-0.0.1-SNAPSHOT.jar

###Creating new posts
Example request:

    curl -X POST http://localhost:8080/blogPostCreate -H 'content-type: application/json' -d '{"authorName": "Stephen King", "authorUserName": "kingy27", "content": "Yet another 2345 pages book", "modificationDate": 234234, "subject": "Are you scared?", "tags": ["deadAnimals", "ghosts"]}'

Responds with the same body after successfully adding content to database. Returning id of new post has not been yet implemented.

### Getting existing post
Example request:

    curl http://localhost:8080/blogPostGet?postId=4

Response:

    {"content":"Yet another 2345 pages book","subject":"Are you scared?","tags":["dead animals","ghosts"],"modificationDate":-3600000,"authorName":"Stephen King","authorUserName":"kingy27"}

## 2. Notification service

### Deployment & running
This application is provided by other team, so you have just to run it in your local environment:
     
     (...)/notifications-server$: java -jar notifications-0.0.1-SNAPSHOT.jar

### Sending notification text to users
Example request:

    curl -X POST http://localhost:8093/sendNotification -H 'content-type: application/json' -d '[{"userId": "some-user-id-34", "message": "Hello from the other side"}, {"userId": "john-doe-12", "message": "Wow! A new book from Stephen King!"}]'

    Responds with 204 No content

This is just a fake service - it simulates the delay of sending the message and writes it to log.
'userId' field content is arbitrary - it's up to you, notifications service doesn't perform any checks in that regard.

# Tasks to implement

### a) Refactor existing solution and write appropriate tests
This code has been written in a really bad way on purpose. You are expected to refactor it, remove bad smells, change the API request/response paths, correct body and error codes for existing endpoints according to what you think they should look like. You don't have to add any additional fields/endpoints unless you are explicitly asked to though. Keeping backward compatibility is not required. Implement all kinds of tests needed.

### b) Extend existing solution with new blog posts notifications subscription feature
It should work as follows:

#### User should be able to subscribe for new blog posts using following request
Request:

    POST <host>/subscriptions
    content-type: application/json
    {"userId": <String>, "authorUserName": <String>}

Response:

    content-type: application/json
    {"subscriptionId": <long>}

`userId` is not present in the system yet, apart from the way notification service handles it, so it's up to you how it will be done in the internals. `authorUserName` is expected to be the same piece of data as `authorUserName` field in 'add blog post' endpoint.

#### User should be able to unsubscribe from receiving notifications about new blog posts by using the following request:
Request:

    DELETE <host>/subscriptions/<subscriptionId>

When user is subscribed for the author it's expected that notification service will be called with appropriate `userId` identifier. Blog post may be considered created (blog creating endpoint may respond), even when the notification has not yet been sent, ‘eventual consistency’ is sufficient in this matter.

### Notes
You can use any techniques, libraries and frameworks you find appropriate for solving this task according to your experience and expertise. If anything changes in the way the application is deployed and run (or if there is a new application) or there are any other important notes from your side please provide them in this readme file. Take your time - the code quality and refactoring part are as important as implementing new working features. Good luck!
