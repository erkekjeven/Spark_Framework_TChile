# Spark Framework TChile

Template project. Integration of Spark + Intellij Idea + Scala.

### Initialize

- Install Docker --> https://www.docker.com/products/docker-desktop

- Install IntellliJ --> https://www.jetbrains.com/es-es/idea/download/other.html

- Run Docker

- Run IntelliJ

- Open these project template in IntelliJ

- Go to terminal in IntelliJ and run:

        $ make build #(Only the first time)

        $ make up

        $ make logs 


### Configuration
    (Don't touch these parameters)

**.env -->**

    SBT_VERSION=0.13.18

**build.sbt -->** 

    scalaVersion := "2.11.8"

    spark := "2.3.0"

### Modify:
**.env -->** 

    USER=[USER]
    HOSTNAME=[HOST_IP]
    REMOTE_PATH=[REMOTE_PATH]

**Makefile -->** 

    rsync -v -r ./scripts/ $(USER)@$(HOSTNAME):$(REMOTE_PATH)/[PROJECT_PATH]

### Coding
You can create your processors and add you table schemas in de path /src/main/scala/tchile/advanced/analytics/

And then create the script to call the processors in /scripts/

**run.sh-->**

Set the user you will use
        
        KEYTAB=~/.ssh/[USER].keytab
        PRINCIPAL=[USER]@TCHILE.LOCAL

Modify the CLASS you need to call and the TARGET you will deposit the jar file

        CLASS=tchile.advanced.analytics.processors.ETL_Example
        TARGET=hdfs://nn:8020/applications/[etl_example]/spark-project_2.11-1.0.jar

And also you can modify other parameters your project need to run

**application.conf-->**

Set Oracle parameters

        url=[ORACLE_URL]
        user=[ORACLE_USER]
        password=[ORACLE_PASSWORD]

### Deploy (From Unix-based system to Cluster)
In other terminal in IntelliJ run:

        $ make deploy

### Deploy (From Windows based system to Cluster)
In other terminal in IntelliJ run:

        $ make deploy-windows