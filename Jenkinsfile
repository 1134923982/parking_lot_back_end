pipeline {
  agent any
  stages {
    stage('Replace application.yml') {
      steps {
        sh '''mkdir /root/.jenkins/workspace/parking_lot_back_end_dev/src/main/resources
cp /gradleConfig/application.yml /root/.jenkins/workspace/parking_lot_back_end_dev/src/main/resources/application.yml'''
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
        sh '''cp /root/.jenkins/workspace/parking_lot_back_end_dev/build/libs/parkinglot-0.0.1-SNAPSHOT.jar /workspace/BackEnd/dev.jar
cd /workspace/BackEnd
p=`jps | grep jar | grep -P \'\\d+\' -o`
sudo kill -9 $p
JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar dev.jar >run.txt 2>&1 &


'''
      }
    }
  }
}