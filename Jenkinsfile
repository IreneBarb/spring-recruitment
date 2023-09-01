pipeline {
    agent any

    stages {
        stage('Download Checkstyle JAR') {
            steps {
                script {
                    def response = httpRequest(
                        url: 'https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.44/checkstyle-8.44-all.jar',
                        httpMode: 'GET',
                        outputFile: 'checkstyle.jar'
                    )
                    if (response.status == 200) {
                        echo "Download successful"
                    } else {
                        error "Failed to download Checkstyle JAR"
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Add your build commands here
                sh 'echo "Building the project..."'
            }
        }

        stage('Test') {
            steps {
                // Add your testing commands here
                sh 'echo "Running tests..."'
            }
        }

        stage('Static Analysis - Java') {
            steps {
                // Download Checkstyle JAR if not already downloaded (you can reuse the code from the "Download Checkstyle JAR" stage)
                script {
                    def javaFiles = sh(script: 'find . -name "*.java"', returnStdout: true).trim().split('\n')
                    for (filePath in javaFiles) {
                        echo "Running Checkstyle on ${filePath}"
                        sh "java -jar checkstyle.jar -c sun_checks.xml ${filePath}"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                // Add your deployment commands here
                sh 'echo "Deploying the application..."'
            }
        }
    }
}