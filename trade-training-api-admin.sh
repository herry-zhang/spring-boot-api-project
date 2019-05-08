#!/bin/sh

ADMIN_API_PATH=/var/spring-boot/admin/trade-training-api-admin.jar
pid=`ps -ef |grep java |grep $ADMIN_API_PATH |awk '{if($3==1)print $2}'`
echo "pid:" $pid
kill -9 $pid
sleep 1s
nohup java -jar $ADMIN_API_PATH > online_admin_web.`date +%Y%m%d`.log 2>&1 &