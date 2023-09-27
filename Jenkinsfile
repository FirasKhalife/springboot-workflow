// void setBuildStatus(String message, String state) {
//   step([
//       $class: "GitHubCommitStatusSetter",
//       reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/my-user/my-repo"],
//       contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
//       errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
//       statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
//   ]);
// }

pipeline {

    agent any

    tools {
        // install dependecies and add them to the PATH
        maven 'Maven 3.9.4'
        jdk 'jdk17'
    }

    stages {
        
        // SCM is already checked out (heavyweight checkout - configured in the Web UI) 

        stage('Build Project') {
            steps {
                bat 'mvn clean install'
            }

            // post {
            //     always {
            //         script {
            //             try {
            //                 def testsPath = 'target/surefire-reports/*.xml'
            //                 def junitResults = junit testsPath

            //                 publishChecks name: 'Test Results', 
            //                     title: 'Pipeline Check', 
            //                     summary: junitResults ? 'success' : 'failure',
            //                     text: junitResults ? 'All tests passed' : 'Test failures found',
            //                     detailsURL: 'https://github.com/FirasKhalife/springboot-workflow.git',
            //                     actions: [[label:'an-user-request-action', description:'actions allow users to request pre-defined behaviours', identifier:'an unique identifier']]

            //             } catch (Exception e) {
            //                 currentBuild.result = 'FAILURE'
            //             }
            //         }
            //     }
            // }
        }

        // only on pushes to the 'dev' branch
        stage('Containerize and Push to Docker Hub') {
            when {
                expression {
                    "${env.GIT_BRANCH}" == "origin/dev"
                }
            }

            steps {
                withCredentials([usernamePassword(
                                    credentialsId: 'dockerhub', 
                                    usernameVariable: 'DOCKERHUB_CREDENTIALS_USR', 
                                    passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW'
                                    )]) {
                    bat 'docker build -t %DOCKERHUB_CREDENTIALS_USR%/spring-boot-app:dev .'
                    bat 'echo %DOCKERHUB_CREDENTIALS_PSW%| docker login -u %DOCKERHUB_CREDENTIALS_USR% --password-stdin'
                    bat 'docker push %DOCKERHUB_CREDENTIALS_USR%/spring-boot-app:dev'
                }
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

    // post {
    //     success {
    //         setBuildStatus("Build succeeded", "SUCCESS");
    //     }
    //     failure {
    //         setBuildStatus("Build failed", "FAILURE");
    //     }
    // }
}
