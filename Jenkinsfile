pipeline {
  agent any
  podTemplate(containers: [
    containerTemplate(
      name: 'gradle',
      image: 'gradle:6.3-jdk14',
      command: 'sleep',
      args: '30d'
    ),
  ]) {
    node{
      stage('Run pipeline against a gradle project') {
        git 'https://github.com/paul182/week6'
        container('gradle') {
          stage('Build a gradle project') {
            sh '''
            ls -l
            chmod +x gradlew
            ./gradlew test 
            '''
          }
          stage("Code coverage") {
            sh '''
              ./gradlew jacocoTestCoverageVerification
              ./gradlew jacocoTestReport 
              '''
            publishHTML(target: [
              reportDir: 'build/reports/tests/test',
              reportFiles: 'index.html',
              reportName: "JaCoCo Code Coverage Report"
            ])
          }
          stage("Checkstyle") {
            script{
              try{
                sh '''
                pwd
                  ./gradlew checkstyleMain
                  '''
              } catch (ex) {
                echo "checkstyle fails"
              }
            }
            publishHTML(target: [
              reportDir: 'build/reports/checkstyle',
              reportFiles: 'main.html',
              reportName: "JaCoCo checkstyle"
            ])
          }
        }
      }
    }
  }
}