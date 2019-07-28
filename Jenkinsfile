pipeline {
  agent any
  stages {
    stage('Replace application.yml') {
      steps {
        sh '''mkdir /root/.jenkins/workspace/parking_lot_back_end_master/src/main/resources
cp /gradleConfig/application.yml /root/.jenkins/workspace/parking_lot_back_end_master/src/main/resources/application.yml'''
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
        sh '''scp -i /keys/null.pem /root/.jenkins/workspace/parking_lot_back_end_master/build/libs/parkinglot-0.0.1-SNAPSHOT.jar centos@3.112.193.240:/workspace/BackEnd/pro.jar
ssh centos@3.112.193.240 -i /keys/null.pem > /dev/null 2>&1 <<\\ eeooff

cd /workspace/BackEnd


p=`jps | grep jar | grep -P \'\\d+\' -o`
sudo kill -9 $p


JENKINS_NODE_COOKIE=dontKillMe

sudo nohup java -jar pro.jar >run.txt 2>&1 &


exit
eeooff
'''
      }
    }
  }
}