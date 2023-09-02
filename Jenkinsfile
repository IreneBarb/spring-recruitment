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
                script {
                    // Download Checkstyle JAR if not already downloaded (you can reuse the code from the "Download Checkstyle JAR" stage)
                    def javaFiles = sh(script: 'find . -name "*.java" -not -path "./src/test/*"', returnStdout: true).trim().split('\n')
                    def checkstyleResults = [:]

                    for (filePath in javaFiles) {
                        def fileName = filePath.tokenize('/').last()
                        echo "Running Checkstyle on ${filePath}"
                        def checkstyleOutput = sh(script: "java -jar checkstyle.jar -c sun_checks.xml ${filePath}", returnStdout: true, returnStatus: true)

                        if (checkstyleOutput == 0) {
                            checkstyleResults[fileName] = "No Checkstyle issues found"
                        } else {
                            checkstyleResults[fileName] = "Checkstyle issues found"
                        }
                    }

                    // Display Checkstyle results summary
                    echo "Checkstyle Results:"
                    checkstyleResults.each { fileName, result ->
                        echo "${fileName}: ${result}"
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