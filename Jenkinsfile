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
          branch pattern: "feature", comparator: "REGEXP"
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
          branch pattern: "feature", comparator: "REGEXP"
      }
      steps {
        sh "./gradlew checkstyleMain"
      }
    }
  }
}