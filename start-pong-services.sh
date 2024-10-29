#!/bin/bash

var port=8080
# 启动一个Pong服务实例
java -jar target/pongService-0.0.1-SNAPSHOT.jar --spring.application.name=pong-service --server.port=$port &


echo "Pong services started."
