#!/usr/bin/env bash

spark-submit \
	--class com.myassignment.orderloader.OrderParser \
	--master yarn-client \
	--name OrderLoader \
	--num-executors 2 \
	--executor-cores 2 \
	--conf spark.yarn.executor.memoryOverhead=512 \
	--conf spark.yarn.am.memory=512M \
	--executor-memory 512M \
	--driver-memory 512M \
	${project.name}-${project.version}-jar-with-dependencies.jar $1 $2

if [ $? -eq 0 ]; then
    exit 0;
else
    exit 1;
fi

