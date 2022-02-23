pipeline {
    agent any

    options {
        buildDiscarder logRotator(numToKeepStr: '15')
        disableConcurrentBuilds()
        timestamps()
    }

    stages {
        stage('build') {
            agent {
                docker {
                    image 'openjdk:17'
                }
            }
            steps {
                timeout(3) {
                    sh './mvnw clean verify -Pintegrationtests'
                }
                junit 'target/surefire-reports/*.xml'
                jacoco()
                stash includes: 'target/*.jar', name: 'jars'
            }
        }
        stage('build_image') {
            agent any
            steps {
                unstash 'jars'
                // TODO build docker image
            }
        }
    }
}
