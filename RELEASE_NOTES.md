# Release Notes

## 1.0.0

Implemented all the logic, planned up to MVP:
* User can sign up and sign in
* User can do authentication and authorization by access token that he get after signing in
* User can update the access token by given refresh token
* Admins has abilities to add role to user, delete it and delete the user account altogether

## 0.7.0-SNAPSHOT

* RT-9: added user IP tracing

## 0.6.0-SNAPSHOT
* RT-10: docker container with the service configured
* Added some small changes to endpoints names and logging messages

## 0.5.0-SNAPSHOT

* RT-7: added refresh token generation
* RT-7: added endpoint for access token refreshing

## 0.4.0-SNAPSHOT

* RT-6: logging added to the project

## 0.3.0-SNAPSHOT

* Added user deletion option for admins
* User becomes owner to roles
* RT-4: added email confirmation

## 0.2.1-SNAPSHOT

* RT-2: added role deleting option for admins 
* Some class and endpoint names were changed

## 0.2.0-SNAPSHOT

* RT-2: added Spring Security
* RT-2: implemented registration and authentication with JWT 
* RT-4:password confirmation added 
* RT-2: added role modifying for admins

## 0.1.0-SNAPSHOT

* RT-1: repository layer added
* RT-0: Spring Boot skeleton project added