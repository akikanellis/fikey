# FiKey

FiKey is a website registration implementation based on the U2F second-factor authentication from [FIDO Alliance](https://fidoalliance.org/).

Getting started
========

1. Installing
===
  Make sure you have [the latest Java SDK installed](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
  
  Make sure you have [the latest Maven](https://maven.apache.org/download.cgi)
 
2. Compiling
===
Enter the server module:

`cd server`

Package with Maven:

`mvn package`

3. Starting
===
Make sure your directory contains these files:

* fikey-x.x.x.jar
* fikey-config.yml
* keystore.jks

Start the server running the command:

`java -jar fikey-x.x.x.jar server fikey-config.yml`

Enter the website by pointing a U2F compatible browser to:

`https://localhost:8080/`

Stop the server on the terminal:

`Ctrl + C`

Third-party libraries
========

 * [Dropwizard 0.8.1](http://www.dropwizard.io/)
 * [Yubico U2F](https://developers.yubico.com/java-u2flib-server/)

License
========

This application itself is released under **MIT** license, see [LICENSE](./LICENSE).

All 3rd party open sourced libs distributed with this application are still under their own license.
