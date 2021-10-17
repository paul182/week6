pipeline {
  agent {
    kubernetes {
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'
      defaultContainer 'gradle'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
    stage('debug') {
      steps {
        echo env.GIT_BRANCH
        echo env.GIT_LOCAL_BRANCH
      }
    }
    stage("prepare") {
      when {
          beforeAgent true
          not {
              branch 'playground' 
          }
      }
      steps {
        sh "chmod +x ./gradlew"
      }
    }
    stage("Unit test") {
      when {
          beforeAgent true
          not {
              branch 'playground' 
          }
      }
      steps {
        sh "./gradlew test"
      }
    }
    stage("Code coverage") {
      when {
          beforeAgent true
          branch 'master' 
      }
      steps {
        sh "./gradlew jacocoTestReport"
        sh "./gradlew jacocoTestCoverageVerification"
      }
    }
    stage("Static code analysis") {
      when {
          beforeAgent true
          not {
              branch 'playground' 
          }
      }
      steps {
        sh "./gradlew checkstyleMain"
      }
    }
    stage("Build gradle Project") {
      when {
          beforeAgent true
          not {
              branch 'playground' 
          }
      }
      steps {
        sh "./gradlew build"
        sh "mv ./build/libs/calculator-0.0.1-SNAPSHOT.jar /mnt"
      }
    }
    stage("Build image") {
      when {
          beforeAgent true
          not {
              branch 'playground' 
          }
      }
      steps {
        container('kaniko') { 
          sh '''
              FILE_NAME='calculator:1.0'
              if [ $env.GIT_BRANCH == 'feature' ]
              then
                FILE_NAME='calculator-feature:0.1'
              fi
             '''
          sh "mv /mnt/calculator-0.0.1-SNAPSHOT.jar ."
          sh "echo 'FROM openjdk:8-jre' > Dockerfile"
          sh "echo 'COPY calculator-0.0.1-SNAPSHOT.jar app.jar' >> Dockerfile"
          sh '''echo 'ENTRYPOINT ["java", "-jar", "app.jar"]' >> Dockerfile
                /kaniko/executor --destination paul182/$FILE_NAME
              '''
        }
      }
    }
  }
}