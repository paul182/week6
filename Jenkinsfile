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
        beforeAgent true
        not {
          branch 'master' 
        }
      }
      steps {
        echo "I am a feature branch"
      }
    }
    stage('master') {
      when {
          beforeAgent true
          branch 'master' 
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
      when {
        expression {
          return env.GIT_BRANCH == "noop"
        }
      }
      steps {
        sh "./gradlew test"
      }
    }
    stage("Code coverage") {
      when {
        expression {
          return env.GIT_BRANCH == "noop"
        }
      }
      steps {
        sh "./gradlew jacocoTestReport"
        sh "./gradlew jacocoTestCoverageVerification"
      }
    }
    stage("Static code analysis") {
      when {
        expression {
          return env.GIT_BRANCH == "noop"
        }
      }
      steps {
        sh "./gradlew checkstyleMain"
      }
    }
  }
}