# quarkus-opentrace-svc1 project

This example showcases opentracing using Quarkus and Smallrye-Opentracing

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/. The specific guide followed was this one https://quarkus.io/guides/opentracing but with a focus on deploying everything as containers.

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

#### Deploy on Kubernetes Minikube

- minikube start
- eval $(minikube docker-env) -- this will ensure we build images to minikube's docker env
- docker image build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-opentrace-svc1 .
- kubectl create -f k8s-deploy/namespace.yaml
- kubectl apply -f k8s-deploy/jaeger-deploy.yaml
- kubectl apply -f k8s-deploy/jaeger-service.yaml
- kubectl apply -f k8s-deploy/app-deploy.yaml
- kubectl apply -f k8s-deploy/app-service.yaml

_Access the application_
  
Use "minikube service list" to list all exposed services and the associated NodePort. Use these URLs to access curl or access on the browser

Identify two important URLs (with their NodePorts): the jaeger console service, and the application service
- Access the jaeger console using the jaeger-console-svc url
- Try to curl the application service at the endpoint /api/health. Example: http://192.168.99.102:31363/api/health

Following this action, refresh the Jaeger UI and you should see "quarkus-opentrace-svc1" under services with the trace ID info

_Troubleshooting_

To troubleshoot k8s and connectivity between services, I use a busybox with curl such as the following:
```  
kubectl run busybox --image=yauritux/busybox-curl --restart=Never --rm -it /bin/sh
```
