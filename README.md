# Spark Framework TChile

Template project. Integration of Spark + Intellij Idea + Scala.

### Initialize

- Install Docker --> https://www.docker.com/products/docker-desktop

- Install IntellliJ --> https://www.jetbrains.com/es-es/idea/download/other.html

- Run Docker

- Run IntelliJ

- Open these project template in IntelliJ

- Go to terminal in IntelliJ and run:

        $ make build

        $ make up

        $ make logs 


### Configuration
    (don't touch these parameters)

**.env -->**

    SBT_VERSION=0.13.15.2

**build.sbt -->** 

    scalaVersion := "2.11.8"

    spark := "2.3.0"

### Modify:
**.env -->** 

    USER=your_user

    HOSTNAME=server_ip

    REMOTE_PATH=/your_path

**Makefile -->** 

    rsync -v -r ./scripts/ $(USER)@$(HOSTNAME):$(REMOTE_PATH)/template

### Coding
You can create your processors and add you table schemas in de path /src/main/scala/tchile/advanced/analytics/

And then create the script to call the processors in /scripts/

**run.sh-->**

        CLASS=tchile.advanced.analytics.processors.Tentados_web
        TARGET=hdfs://nn:8020/applications/template/spark-project_2.11-1.0.jar

Modify the CLASS you need to call and the TARGET you will deposit the jar file

And also you can modify other parameters your project need to run

### Deploy
In other terminal in IntelliJ run:

        $ make deploy
