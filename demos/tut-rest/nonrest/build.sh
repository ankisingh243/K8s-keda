#! /bin/bash
set -euxo pipefail
echo ${CR_PASS} | docker login docker-registry.devops.gupshup.me --username gupshup-docker --password-stdin
mvn clean install package
docker build -t nonrest .
docker tag nonrest docker-registry.devops.gupshup.me/gsdevops/nonrest
docker push docker-registry.devops.gupshup.me/gsdevops/nonrest