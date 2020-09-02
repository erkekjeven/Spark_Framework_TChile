FROM openjdk:8

ARG SBT_VERSION

RUN \
  curl -L -o sbt-${SBT_VERSION}.deb http://dl.bintray.com/sbt/debian/sbt-${SBT_VERSION}.deb && \
  dpkg -i sbt-${SBT_VERSION}.deb && \
  rm sbt-${SBT_VERSION}.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /code

ADD . /code

CMD sbt clean ~package
