  podTemplate(containers: [
    containerTemplate(
      name: 'gradle',
      image: 'gradle:6.3-jdk14',
      command: 'sleep',
      args: '30d'
    ),
  ]) {
    node(POD_LABEL) {
      stage('Run pipeline against a gradle project') {
        git 'https://github.com/paul182/week6'
        container('gradle') {
          stage('Build a gradle project') {
            when { not { branch 'playground' } }
            sh '''
            chmod +x gradlew
            ./gradlew test 
            '''
          }
          stage("Code coverage") {
            when { not { branch 'playground' } }
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
            when { not { branch 'playground' } }
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