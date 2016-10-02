#!/bin/bash
mvn install:install-file -Dfile=./target/schema-0.0.1.jar -DgroupId=com.drfloob.insight.pulse -DartifactId=schema -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true
