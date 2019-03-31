node {

    stage('compile') {
        docker.image('maven:3.6.0-jdk-11-slim').inside('-v $HOME/.m2:/root/.m2') {
            sh 'mvn compile'
        }
    }

    stage('sonarqube') {
        docker.image('maven:3.6.0-jdk-11-slim').inside('-v $HOME/.m2:/root/.m2') {
            try {
                // try the execution
                // https://github.com/gabrie-allaigre/sonar-gitlab-plugin/issues/213
                sh 'mvn sonar:sonar -Dsonar.projectKey=java-example -Dsonar.host.url=https://sonar.eks.criptalia.it -Dsonar.login=6e51a5989b5c0c848df36eb640f13a81dc2e0653'
            }
            catch (exc) {
                echo 'Go on, this is a well know bug.'
            }
        }
    }

}
