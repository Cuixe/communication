#!/bin/bash

SOURCE_PATH='./messaging.proto'
DESTINY_PATH='../java'

protoc --java_out=${DESTINY_PATH} ${SOURCE_PATH}