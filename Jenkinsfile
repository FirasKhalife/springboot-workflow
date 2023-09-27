pipeline {

    agent any

    tools {
        // install dependecies and add them to the PATH
        maven 'Maven 3.9.4'
        jdk 'jdk17'
    }

    environment {
        DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    }

    stages {
        
        // SCM is already checked out (heavyweight checkout - configured in the Web UI)

        stage('Build Project') {
            steps {
                sh 'mvn clean install'
            }
        }

        post {
            junit 'target/surefire-reports/*.xml' 
        }

        // only on pushes to the 'dev' branch
        stage('Containerize and Push to Docker Hub') {
            when {
                expression {
                    currentBuild.rawBuild.getCause(hudson.triggers.SCMTrigger.SCMTriggerCause)
                        .getShortDescription()
                        .contains("refs/heads/dev")
                }
            }

            stage('Build Docker Image') {
                steps {
                    sh 'docker build -t $DOCKERHUB_CREDENTIALS_USR/spring-boot-app:dev .'
                }
            }

            stage('Login to DockerHub') {
                steps {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW| docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                }
            }

            stage('Push Image to DockerHub') {
                steps {
                    sh 'docker push $DOCKERHUB_CREDENTIALS_USR/spring-boot-app:dev'
                }
            }

            post {
                always {
                    sh 'docker logout'
                }
            }
        }
    }
}
