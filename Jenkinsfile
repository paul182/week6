pipeline {
  agent {
    kubernetes {
      yaml '''
      spec:
        containers:
        - name: gradle
          image: gradle:6.3-jdk14 
          command:
          - sleep
          args:
          - 30d
     '''
    }
  }
  stages {
    stage('debug') {
      steps {
        echo env.GIT_BRANCH
        echo env.GIT_LOCAL_BRANCH
      }
    }
    stage('feature') {
      when {
        expression {
          return env.GIT_BRANCH == "origin/feature"
        }
      }
      steps {
        echo "I am a feature branch"
      }
    }
    stage('master') {
      when {
        expression {
          return env.GIT_BRANCH == "master"
        }
      }
      steps {
        echo "I am a main branch"
      }
    }
    stage("prepare") {
      steps {
        sh "chmod +x ./gradlew"
      }
    }
    stage("Unit test") {
      steps {
        sh "./gradlew test"
      }
    }
    stage("Code coverage") {
      steps {
        sh "./gradlew jacocoTestReport"
        sh "./gradlew jacocoTestCoverageVerification"
      }
    }
    stage("Static code analysis") {
      steps {
        sh "./gradlew checkstyleMain"
      }
    }
  }
}