#!/usr/bin/sh

# check if ES APM URL is set and if it is then use it then run java -jar app.jar
if [ -z "$ES_APM_URL" ]; then
    java -jar app.jar
else
    java -javaagent:elastic-apm-agent-1.39.0.jar \
    -Delastic.apm.service_name=tqs-store \
    -Delastic.apm.secret_token= \
    -Delastic.apm.server_url="$ES_APM_URL" \
    -Delastic.apm.environment=production \
    -Delastic.apm.application_packages=pt.ua.deti.store \
    -jar app.jar
fi