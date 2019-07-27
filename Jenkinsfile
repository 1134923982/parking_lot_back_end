pipeline {
  agent any
  stages {
    stage('Replace application.yml') {
      steps {
        sh 'cp /gradleConfig/application.yml /root/.jenkins/workspace/parking_lot_back_end_dev/src/main/resources/application.yml'
      }
    }
    stage('Gradle Build') {
      steps {
        sh '''chmod 700 ./gradlew
./gradlew clean build'''
      }
    }
    stage('Deploy To Staging') {
      steps {
        sh '''#!/bin/bash

p=`jps | grep jar | grep -P \'\\d+\' -o`
kill -9 $p
cp /root/.jenkins/workspace/parking_lot_back_end_master/build/libs/ParkingLotNullITA-0.0.1-SNAPSHOT.jar /workspace/BackEnd/dev.jar

cd /workspace/BackEnd

JENKINS_NODE_COOKIE=dontKillMe

nohup java -jar dev.jar >run.txt 2>&1 &'''
      }
    }
  }
}