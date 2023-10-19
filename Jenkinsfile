pipeline {
    agent any
      tools {
        'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'Docker 18.09.0'
        maven 'maven-3.9.5'
      }
      environment {
        DOCKER_CERT_PATH = credentials('id-for-a-docker-cred')
      }

    stages {

         stage('Cleanup') {
            steps {
                script {
                    // Stop and remove the Docker container when you're done
                    sh 'docker stop my-container'
                    sh 'docker rm my-container'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the Dockerfile in the current directory
                    def dockerBuildResult = sh(script: 'docker build -t my-custom-image:latest .', returnStatus: true)

                    if (dockerBuildResult == 0) {
                        echo "Docker image build successful."
                    } else {
                        error "Docker image build failed."
                    }
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Run a Docker container using the image you built
                    def dockerRunResult = sh(script: 'docker run -v /var/run/docker.sock:/var/run/docker.sock -d --name my-container my-custom-image:latest', returnStatus: true)

                    if (dockerRunResult == 0) {
                        echo "Docker container started successfully."

                        def createPathResult = sh(script: "docker exec my-container mkdir -p /files", returnStatus: true)

                        // Copy the file from the Jenkins workspace to the container
                        sh "docker cp ${WORKSPACE}/request_file.txt my-container:/files/request_file.txt"

                        // You may need to add a delay here to ensure the copy completes
                        sleep time: 2, unit: 'SECONDS'
                    } else {
                        error "Failed to start Docker container."
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Sensitive information') {
            steps {
                sh 'rm trufflehog || true'
                sh 'docker run --rm -v "$PWD:/pwd" trufflesecurity/trufflehog:latest github --repo https://github.com/IreneBarb/spring-recruitment.git'
            }
        }

        stage('SonarQube Analysis - static code analyzer') {
            steps {
                withSonarQubeEnv(installationName: 'sq1'){
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('trivy vulnerabilities scan') {
            steps {
                script {
                    def command = 'trivy image --format json --output results.json my-custom-image:latest'
                    def dockerExecResult = sh(script: "docker exec my-container sh -c '${command}'", returnStatus: true)
                    def resultContents = readFile('results.json')
                    echo resultContents
                }
            }
        }

        stage('Run Nmap Scan Inside Docker Container') {
            steps {
                script {
                    // Define the Nmap scan command
                    def nmapCommand = "nmap 8-p 0-10000 127.0.0.1"

                    def nmapVulnerabilitiesCommand = "nmap -sV --script nmap-vulners/ 127.0.0.1"

                    // Run the Nmap scan inside the running Docker container
                    def dockerExecResult = sh(script: "docker exec my-container sh -c '${nmapCommand}'", returnStatus: true)

                    def dockerExecVulnerabilitiesResult = sh(script: "docker exec my-container sh -c '${nmapVulnerabilitiesCommand}'", returnStatus: true)

                    if (dockerExecResult == 0) {
                        echo "Nmap scan inside the Docker container executed successfully."
                    } else {
                        error "Failed to execute Nmap scan inside the Docker container."
                    }

                    if (dockerExecVulnerabilitiesResult == 0) {
                        echo "Nmap scan inside the Docker container executed successfully."
                    } else {
                        error "Failed to execute Nmap scan inside the Docker container."
                    }
                }
            }
        }

        stage('Sql injection testing') {
            steps {
                script {
                    def containerName = 'my-container'

                    // Run SQLMap inside the container with the copied request_file.txt
                    def sqlMapResult = sh(script: "docker exec ${containerName} sqlmap -r /files/request_file.txt", returnStatus: true)

                    if (sqlMapResult == 0) {
                        echo "SQLMap completed successfully."
                    } else {
                        error "SQLMap encountered an error."
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