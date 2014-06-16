deors.demos.annotations
=======================

Demonstration of how to generate code using Annotation Processors

List of artifacts
-----------------

deors.demos.annotations.base - some simple annotation types

deors.demos.annotations.base.client - a 'client' project making use of annotation types defined above

deors.demos.annotations.base.processors - annotation processors which handle annotation types defined above

deors.demos.annotations.beaninfo - annotation types related to BeanInfo metadata

deors.demos.annotations.beaninfo.client - a 'client' project making use of BeanInfo annotation types

deors.demos.annotations.beaninfo.processors - annotation processors which generate BeanInfo classes based on annotated metadata

deors.demos.annotations.velocity.client - a 'client' project making use of BeanInfo annotation types, to be used with the Apache Velocity processor

deors.demos.annotations.velocity.processors - annotation processors which generate BeanInfo classes but using Apache Velocity as a template engine


Instructions
------------

1) Install the artifact deors.demos.annotations.base:

    cd /<PROJECT_HOME>/deors.demos.annotation.base
    mvn clean install

2) Install the artifact deors.demos.annotations.base.processors:

    cd /<PROJECT_HOME>/deors.demos.annotation.base.processors
    mvn clean install

3) Compile or test the artifact deors.demos.annotations.base.client:

    cd /<PROJECT_HOME>/deors.demos.annotation.base.client
    mvn clean test

4) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

5) Same for BeanInfo annotation, install the artifact deors.demos.annotations.beaninfo:

    cd /<PROJECT_HOME>/deors.demos.annotations.beaninfo
    mvn clean install

6) Install the artifact deors.demos.annotations.beaninfo.processors:

    cd /<PROJECT_HOME>/deors.demos.annotations.beaninfo.processors
    mvn clean install

7) Compile or test the artifact deors.demos.annotations.beaninfo.client:

    cd /<PROJECT_HOME>/deors.demos.annotations.beaninfo.client
    mvn clean test

8) Alternatively, use directly the Java Compiler javac with the provided batch file:

    build-with-javac.bat

9) For the demo using Velocity templates, install the artifact deors.demos.annotations.velocity.processors:

    cd /<PROJECT_HOME>/deors.demos.annotations.velocity.processors
    mvn clean install

10) And finally, compile or test the artifact deors.demos.annotations.velocity.client

    cd /<PROJECT_HOME>/deors.demos.annotations.velocity.client
    mvn clean test


NOTE: Generated sources can be found on target/generated-sources/annotations.
