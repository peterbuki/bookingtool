#!/usr/bin/env bash

SERVICE_PROTOCOL=http
SERVICE_HOST=localhost
SERVICE_PORT=8080
SERVICE_PATH=/updateUsageByHostname

URL=${SERVICE_PROTOCOL}://${SERVICE_HOST}:${SERVICE_PORT}${SERVICE_PATH}

update='{"Host":"'`hostname`'", "Usage":"'$*'"}'

curl -s -o - --header "Content-Type: application/json" -X POST -d "$update" $URL