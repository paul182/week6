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
          volumeMounts:
          - name: shared-storage
            mountPath: /tmp
        - name: kaniko
          image: gcr.io/kaniko-project/executor:debug
          command:
          - sleep
          args:
          - 30d
          volumeMounts:
          - name: shared-storage
            mountPath: /tmp
          - name: kaniko-secret
            mountPath: /kaniko/.docker
        restartPolicy: Never
        volumes:
        - name: shared-storage
          persistentVolumeClaim:
            claimName: jenkins-pv-claim
        - name: kaniko-secret
          secret:
            secretName: dockercred
            items:
            - key: .dockerconfigjson
              path: config.json
     '''
    }
  }
  stages {
          container('gradle'){
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
          }
  }
}