# Toronto Smart Library (TSL)

**Toronto Smart Library** provides Json REST APIs to search Libraries, Books and gives metrics for potential analysis.

### Swagger
/swagger-ui.html
 
### Versioning
This API is versioned, with current version <b>1</b>. Version will only be incremented when we introduce breaking changes.

Getting started 
----------------
<p>
 The TSL backend is a <b><i>Spring Boot</i></b> based application, developed and configured to run on Google App Engine (Standard).
</p>
<p>
 You can also run it locally as a Standard Spring Boot application, by just removing the "Tomcat" exclusion from the dependencies.
</p>

### Minimum requirements
To start coding, run and deploy the application, you will need the following tools installed on your machine:
<ul>
    <li>Java 8</li>
    <li>Maven 3.5</li>
    <li>Gcloud SDK with "app-engine-java" component</li>
    <li>Your favorite IDE</li>
    <li>If you use IntelliJ (optional):
        <ul>
            <li>Lombok plugin</li>
            <li>Gcloud SDK plugin</li>
        </ul>
    </li>
</ul>

### Configuration

### Profiles
Profiles are managed by `Maven`, to handle Spring active profiles and also override some environment variables.

We use the "maven-resources-plugin" to replace a properties in "application.properties" with profile properties; 
ie. If you build and run application with "mock" profile, this plugin will set the "spring.profiles.active" to "mock". 

<ul>
<li>mock</li>
<li>sit</li>
<li>uat</li>
<li>production</li>
</ul>

### Start app
To locally run the app, you have to use Gcloud `appengine-maven-plugin`. 

Run the following command to run the app:
`mvn clean appengine:run -DskipTests -P {PROFILE}`

Where `PROFILE` is one the profiles defined in [Profiles](#Profiles)

### Debug
#### Local
#### Remote


### Deployment
To manually deploy the app on GAE, you will need to have access to this project on GCloud console.
Once you have  access, you just need to use the Gcloud `appengine-maven-plugin` and run the following: 
 
`mvn clean appengine:deploy -DskipTests -P {PROFILE}`

**/!\ Make sure that you use the right gcloud config profile that matches your target environment** 
