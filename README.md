# quarkus-opentrace-svc1 project

This example showcases opentracing using Quarkus and Smallrye-Opentracing

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application

#### Local

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

#### Container

Or to deploy in a container:
```
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

## Procedure

#### Create Docker Network

**Note**: This is required when deploying locally using docker. If you want to deploy on a local kubernetes/minikube (or minishift) instance, then the procedure will be different but ensure traffic that be allowed between the pods for ports related to Jaeger.

Create a new docker network (named app-network). This will ensure the containers are deployed on the same network and allow name resolution:
```
docker network create app-network
docker network ls
``` 

#### Jaeger end-to-end distributing tracing system

Start the Jaeger open tracing system in the app-network
```
docker container run --name jaeger --network app-network -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:latest
```

Access the UI to validate that Jaeger is up: http://localhost:16686/search

#### Running the Microservice

Now, run quarkus svc1 using the native container docker file again in the app-network that we just created. Jaeger configurations are set as environment variables in that dockerfile
```
./mvnw package -Pnative -Dquarkus.native.container-build=true
docker image build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-opentrace-svc1 .
docker container run --name svc1 --network app-network -i --rm -p 8080:8080 quarkus/quarkus-opentrace-svc1
``` 

Try to call the GET endpoint
```
curl http://localhost:8080/api/health
```

Following this action, refresh the Jaeger UI and you should see "quarkus-opentrace-svc1" under services with the trace ID info