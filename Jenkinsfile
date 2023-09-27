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

        stage('Check Docker version') {
            steps {
                echo 'env.GIT_BRANCH'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn clean install'
            }

            post {
                always {
                    script {
                        try {
                            junit 'target/surefire-reports/*.xml'
                        } catch (Exception e) {
                            currentBuild.result = 'FAILURE'
                        }
                    }
                }
            }
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

            steps {
                bat 'docker build -t $DOCKERHUB_CREDENTIALS_USR/spring-boot-app:dev .'
                bat 'echo $DOCKERHUB_CREDENTIALS_PSW| docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                bat 'docker push $DOCKERHUB_CREDENTIALS_USR/spring-boot-app:dev'
            }

            post {
                always {
                  script {
                        try {
                            bat 'docker logout'
                        } catch (Exception e) {
                            currentBuild.result = 'FAILURE'
                        }
                    }
                }
            }
        }
    }
}
