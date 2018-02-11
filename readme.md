# deors-demos-annotations

Demonstration of how to generate code using Annotation Processors.

## List of artefacts

* deors-demos-annotations-base - some simple annotation types

* deors-demos-annotations-base-client - a 'client' project making use of annotation types defined above

* deors-demos-annotations-base-processors - annotation processors which handle annotation types defined above

* deors-demos-annotations-beaninfo - annotation types related to BeanInfo metadata

* deors-demos-annotations-beaninfo-client - a 'client' project making use of BeanInfo annotation types

* deors-demos-annotations-beaninfo-processors - annotation processors which generate BeanInfo classes based on annotated metadata

* deors-demos-annotations-velocity - annotation types related to BeanInfo metadata for the Apache Velocity processor

* deors-demos-annotations-velocity-client - a 'client' project making use of BeanInfo annotation types, to be used with the Apache Velocity processor

* deors-demos-annotations-velocity-processors - annotation processors which generate BeanInfo classes but using Apache Velocity as a template engine

* deors-demos-annotations-entity - annotation types related to GenerateEntity example

* deors-demos-annotations-entity-client - a 'client' project making use of GenerateEntity annotation types

* deors-demos-annotations-entity-processors - annotation processors which generate Entity classes from annotated interfaces


## Instructions

### 1. Base annotations example

#### 1.1. Install the artefact deors-demos-annotations-base:

    cd /<PROJECT_HOME>/deors-demos-annotations-base
    mvn clean install

#### 1.2. Install the artefact deors-demos-annotations-base-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-base-processors
    mvn clean install

#### 1.3. Compile or test the artefact deors-demos-annotations-base-client:

    cd /<PROJECT_HOME>/deors-demos-annotations-base-client
    mvn clean test

#### 1.4. Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

### 2. BeanInfo generation example

#### 2.1. Install the artefact deors-demos-annotations-beaninfo:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo
    mvn clean install

#### 2.2. Install the artefact deors-demos-annotations-beaninfo-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo-processors
    mvn clean install

#### 2.3. Compile or test the artefact deors-demos-annotations-beaninfo-client:

    cd /<PROJECT_HOME>/deors-demos-annotations-beaninfo-client
    mvn clean test

#### 2.4. Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

### 3. BeanInfo generation example using Apache Velocity templates

#### 3.1. Install the artefact deors-demos-annotations-velocity

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity
    mvn clean install

#### 3.2. Install the artefact deors-demos-annotations-velocity-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity-processors
    mvn clean install

#### 3.3. Compile or test the artefact deors-demos-annotations-velocity-client

    cd /<PROJECT_HOME>/deors-demos-annotations-velocity-client
    mvn clean test

#### 3.4. Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

### 4. Entity generation example using Apache Velocity templates

#### 4.1. Install the artefact deors-demos-annotations-entity

    cd /<PROJECT_HOME>/deors-demos-annotations-entity
    mvn clean install

#### 4.2. Install the artefact deors-demos-annotations-entity-processors:

    cd /<PROJECT_HOME>/deors-demos-annotations-entity-processors
    mvn clean install

#### 4.3. Compile or test the artefact deors-demos-annotations-entity-client

    cd /<PROJECT_HOME>/deors-demos-annotations-entity-client
    mvn clean test

#### 4.4. Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat


NOTE: Generated sources can be found on each client module in folder target/generated-sources/annotations.
