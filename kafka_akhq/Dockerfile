FROM alpine
WORKDIR /opt

ARG KAFKAV=kafka_2.13-3.3.1

# download utils
RUN  apk update \
    && apk upgrade \
    && apk add --update openjdk17 tzdata curl unzip bash \
    && rm -rf /var/cache/apk/*

# kafka
RUN curl https://downloads.apache.org/kafka/3.3.1/$KAFKAV.tgz -o $KAFKAV.tgz \
    && tar -xvf $KAFKAV.tgz \
    && mv $KAFKAV kafka \
    && rm $KAFKAV.tgz

COPY ./copy/ .

RUN echo
RUN echo =========================================
RUN echo Remember to modify akhq configuration if needed.
RUN echo akhq version : 0.22.0-all
RUN echo
RUN echo run this image with:
RUN echo "docker run -d -p 9092:9092 -p 8081:8080 <image name>"
RUN echo =========================================
RUN echo

# RUN mkdir -p /opt/akhq

CMD ["/opt/start_all.sh"]