#!/bin/bash

# Generate a unique tag using the current timestamp
TAG="v$(date +'%Y%m%d%H%M%S')"

# Build and tag the Docker image
docker build -t "$DOCKER_USERNAME/spring-boot-app:$TAG" .

# Push the image with the unique tag
docker push "$DOCKER_USERNAME/spring-boot-app:$TAG"
