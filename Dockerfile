FROM openjdk:8

ARG SBT_VERSION

RUN \
  curl -L -o sbt-${SBT_VERSION}.zip https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.zip && \
  unzip sbt-${SBT_VERSION}.zip -d ops && \
  apt-get update && \
  /ops/sbt/bin/sbt sbtVersion

WORKDIR /code

ADD . /code

CMD /ops/sbt/bin/sbt clean ~package
