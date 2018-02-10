deors-demos-annotations
=======================

Demonstration of how to generate code using Annotation Processors

List of artefacts
-----------------

deors-demos-annotations-base - some simple annotation types

deors-demos-annotations-base-client - a 'client' project making use of annotation types defined above

deors-demos-annotations-base-processors - annotation processors which handle annotation types defined above

deors-demos-annotations-beaninfo - annotation types related to BeanInfo metadata

deors-demos-annotations-beaninfo-client - a 'client' project making use of BeanInfo annotation types

deors-demos-annotations-beaninfo-processors - annotation processors which generate BeanInfo classes based on annotated metadata

deors-demos-annotations-velocity - annotation types related to BeanInfo metadata for the Apache Velocity processor

deors-demos-annotations-velocity-client - a 'client' project making use of BeanInfo annotation types, to be used with the Apache Velocity processor

deors-demos-annotations-velocity-processors - annotation processors which generate BeanInfo classes but using Apache Velocity as a template engine

deors-demos-annotations-entity - annotation types related to GenerateEntity example

deors-demos-annotations-entity-client - a 'client' project making use of GenerateEntity annotation types

deors-demos-annotations-entity-processors - annotation processors which generate Entity classes from annotated interfaces


Instructions
------------

A) Base annotations example

A1) Install the artefact deors-demos-annotations-base:

    cd /<PROJECT_HOME>/deors-demos-annotations-base
    mvn clean install

A2) Install the artefact deors-demos-annotations-base-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-base-processors
    mvn clean install

A3) Compile or test the artefact deors-demos-annotations-base-client:

    cd /<PROJECT_HOME>/deors-demos-annotations-base-client
    mvn clean test

A4) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

B) BeanInfo generation example

B1) Install the artefact deors-demos-annotations-beaninfo:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo
    mvn clean install

B2) Install the artefact deors-demos-annotations-beaninfo-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo-processors
    mvn clean install

B3) Compile or test the artefact deors-demos-annotations-beaninfo-client:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo-client
    mvn clean test

B4) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

C) BeanInfo generation example using Apache Velocity templates

C1) Install the artefact deors-demos-annotations-velocity

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity
    mvn clean install

C2) Install the artefact deors-demos-annotations-velocity-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity-processors
    mvn clean install

C3) Compile or test the artefact deors-demos-annotations-velocity-client

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity-client
    mvn clean test

C4) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

D) Entity generation example using Apache Velocity templates

D1) Install the artefact deors-demos-annotations-entity

    cd /<PROJECT_HOME>/deors-demos-annotations-entity
    mvn clean install

D2) Install the artefact deors-demos-annotations-entity-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-entity-processors
    mvn clean install

D3) Compile or test the artefact deors-demos-annotations-entity-client

    cd /<PROJECT_HOME>/deors-demos-annotations-entity-client
    mvn clean test

D4) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat


NOTE: Generated sources can be found on each client module in folder target/generated-sources/annotations.
