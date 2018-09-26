node {  
  stage('Checkout') {
    git branch: 'master', credentialsId: '1c530311be4bbab34809d65af76a5424e2c787a6', url: 'https://github.com/team-cyhee/rabit_oauth2.git'
  }
  
  stage('Bulid') {
    sh 'chmod +x gradlew'
    sh './gradlew build -x test'
  }
  
  stage('Deploy') {
    def target = '/usr/local/rabit/'
    sh 'cp ./build/libs/*.jar ' + target + 'rest/rest.jar'
    sh 'docker --version'
    sh 'docker-compose -f ' + target + 'docker-compose.yml stop' 
    sh 'docker-compose -f ' + target + 'docker-compose.yml up -d' 
  }  
}
