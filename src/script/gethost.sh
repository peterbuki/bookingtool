#!/usr/bin/bash

SERVICE_PROTOCOL=http
SERVICE_HOST=localhost
SERVICE_PORT=8080
SERVICE_PATH=/findByHostname

URL=${SERVICE_PROTOCOL}://${SERVICE_HOST}:${SERVICE_PORT}${SERVICE_PATH}

PARAMS="hostname=`hostname`&columns="${COLUMNS}

#echo ${URL}?${PARAMS}
curl -s -o - ${URL}?${PARAMS}
