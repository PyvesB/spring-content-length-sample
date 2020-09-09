# Spring Content Length Sample

Related to https://github.com/spring-projects/spring-framework/issues/25744

Simply run `mvn clean package` to build the project, and `java -jar target/spring-content-length-sample-0.0.1-SNAPSHOT.jar` to run the Spring application.

A Ruby script is available to simulate the case where a `Content-Length` is specified in the request, but the connection is terminated before the required number of bytes is sent. Simply run `bundle install` and `bundle exec ruby request.rb` to execute it.
