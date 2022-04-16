# ⚠️ This project has been deprecated ⚠️

This project contains some very old code that does not reflect my standards anymore. I will be leaving
the project in an archived state in case it is still useful to someone.

# FiKey

FiKey is an API designed to make it as simple as possible to implement U2F user registration and authentication on a service. There is also an example server provided for demonstrating the usage of the API.

## Full Documentation

See the [Wiki](https://dkanellis.github.com/dkanellis/FiKey/wiki/) for full documentation, examples, operational details and other information.

See the [Javadoc](http://netflix.github.com/FiKey/javadoc) for the API documentation.

## Getting started

### Prerequisites

* [Java Development Kit 8+](http://www.oracle.com/technetwork/java/javase/downloads/)
* [Maven 3.3.3+](https://maven.apache.org/download.cgi)

### Packaging

Navigate to the module you want to use:

```
cd module-path
```

Package with Maven:

```
mvn package
```

### FiKey API

#### Usage

##### Initialization

In the starting point of your application initialize the storages:

```java
FiKeyAuth.initDefaultStorage();
```

Create an `Authenticator` object for your backend and pass it the application ID (usually the service's URL):

```java
Authenticator fiKeyAuth = new FiKeyAuth(APP_ID);
```

##### Registration

For a new user registration you need to use 3 methods of the FiKey object. Your frontend should first provide you with the username and password of the new user and FiKey will take care of the rest and notify you (through exceptions) if something went wrong. Then you need to trigger the start of the device authentication and it's finishing through the other two FiKey registration operations. FiKey will store all the data that you need so the only thing you would have to do is execute each of the 3 appropriate methods when needed and handling their exceptions.

The basic outline is like this:

1. `fiKeyAuth.registerUser(username, password);`
2. `fiKeyAuth.startDeviceRegistration(username);`
3. `fiKeyAuth.finishDeviceRegistration(response, username);`

##### Authentication

Similarly to registration you only have to execute the 3 appropriate methods again and make sure you handle the exceptions, everything else is taken care of.

Outline:

1. `fiKeyAuth.authenticateUser(username, password);`
2. `fiKeyAuth.startDeviceAuthentication(username);`
3. `fiKeyAuth.finishDeviceAuthentication(response, username);`

### Example server

#### Usage

Make sure your directory contains these files:

* fikey-server-example-x.x.x.jar
* fikey-config.yml
* keystore.jks

Start the server running the command:

```
java -jar fikey-server-example-x.x.x.jar server fikey-config.yml
```

Enter the website by pointing a U2F compatible browser (like Chrome) to:

[https://localhost:8080/](https://localhost:8080/)

Stop the server on the terminal:

```
Ctrl + C
```

## Third-party libraries

 * [Dropwizard 0.8.1](http://www.dropwizard.io/)
 * [Yubico U2F](https://developers.yubico.com/java-u2flib-server/)

## License

This application itself is released under **MIT** license, see [LICENSE](./LICENSE).

All 3rd party open sourced libs distributed with this application are still under their own license.
