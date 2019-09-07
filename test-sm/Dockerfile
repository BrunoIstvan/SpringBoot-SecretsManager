# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine as build

# Add Maintainer Info
LABEL maintainer="bruno.istvan@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/test-sm*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} test-sm*.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", \
				   "-Dapp.name=hello-world-demo", \	
				   "-Daws_access_key_id=xyz", \
				   "-Daws_secret_access_key=xyz", \   
                   "-jar","/test-sm*.jar"]
                   
                   
                   