FROM debezium/connect:2.5
LABEL authors="sherifabdulraheem"

# Define plugin directory explicitly
ENV DEBEZIUM_DIR=$KAFKA_CONNECT_PLUGINS_DIR/transformer

## Create plugin directory
#RUN mkdir -p $DEBEZIUM_DIR
##
### Ensure correct file permissions
RUN chmod -R 777 $DEBEZIUM_DIR
# Copy custom JAR into plugins directory
COPY ./custom-debezium-tranformer-0.0.1.jar $DEBEZIUM_DIR/


#Deploying '<unknown> Dockerfile: read-write-db/Dockerfile'…
#[+] Building 0.1s (8/8) FINISHED                                 docker:default
# => [internal] load build definition from Dockerfile                       0.0s
# => => transferring dockerfile: 426B                                       0.0s
# => [internal] load metadata for docker.io/debezium/connect:2.5            0.0s
# => [internal] load .dockerignore                                          0.0s
# => => transferring context: 2B                                            0.0s
# => [1/3] FROM docker.io/debezium/connect:2.5                              0.0s
# => [internal] load build context                                          0.0s
# => => transferring context: 58B                                           0.0s
# => CACHED [2/3] RUN mkdir -p /kafka/connect/debezium-transformer          0.0s
# => CACHED [3/3] COPY ./custom-debezium-tranformer-0.0.1.jar /kafka/conne  0.0s
# => exporting to image                                                     0.0s
# => => exporting layers   sha256:c8be01e64188327371b491df4495c31290c6397eec5dfd9f52cb13a0cb32e7a7                                                 0.0s
# => => writing image sha256:cc58b180922edf6ee45fc2c2200ca2fd7aa870f636ef1  0.0s


#Deploying '<unknown> Dockerfile: read-write-db/Dockerfile'…
#[+] Building 0.1s (8/8) FINISHED                                 docker:default
# => [internal] load build definition from Dockerfile                       0.0s
# => => transferring dockerfile: 1.68kB                                     0.0s
# => [internal] load metadata for docker.io/debezium/connect:2.5            0.0s
# => [internal] load .dockerignore                                          0.0s
# => => transferring context: 2B                                            0.0s
# => [1/3] FROM docker.io/debezium/connect:2.5                              0.0s
# => [internal] load build context                                          0.0s
# => => transferring context: 58B                                           0.0s
# => CACHED [2/3] RUN chmod -R 777 /kafka/connect                           0.0s
# => CACHED [3/3] COPY ./custom-debezium-tranformer-0.0.1.jar /kafka/conne  0.0s
# => exporting to image                                                     0.0s
# => => exporting layers                                                    0.0s
# => => writing image sha256:368a4cc2aa68556d7d99522e0e26fb74d9ca42f657b39  0.0s
#
#



