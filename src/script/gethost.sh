#!/usr/bin/env bash

SERVICE_PROTOCOL=http
SERVICE_HOST=localhost
SERVICE_PORT=8080
SERVICE_PATH=/findByHostname

URL=${SERVICE_PROTOCOL}://${SERVICE_HOST}:${SERVICE_PORT}${SERVICE_PATH}

curl -s -o  - ${URL}/?hostname=`hostname`
