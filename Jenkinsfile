pipeline {
    agent any
//         {
//             dockerfile {
//                 filename '/Users/irenebarbouni/Documents/backendRecruitment/backend/Dockerfile'
//             }
//         }
      tools {
        // a bit ugly because there is no `@Symbol` annotation for the DockerTool
        // see the discussion about this in PR 77 and PR 52:
        // https://github.com/jenkinsci/docker-commons-plugin/pull/77#discussion_r280910822
        // https://github.com/jenkinsci/docker-commons-plugin/pull/52
        'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'Docker 18.09.0'
      }
      environment {
        DOCKER_CERT_PATH = credentials('id-for-a-docker-cred')
      }

    stages {

        stage('Test Docker') {
            steps {
                sh 'docker --version'
            }
        }

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
                    def dockerRunResult = sh(script: 'docker run -d --name my-container my-custom-image:latest', returnStatus: true)

                    if (dockerRunResult == 0) {
                        echo "Docker container started successfully."

                        // Copy the file from the Jenkins workspace to the container
                        sh "docker cp $(pwd())/request_file.txt my-container:/path/in/container/request_file.txt"

                        // You may need to add a delay here to ensure the copy completes
                        sleep time: 10, unit: 'SECONDS'
                    } else {
                        error "Failed to start Docker container."
                    }
                }
            }
        }

//         stage('Run SQLMap Inside Docker Container') {
//             steps {
//                 script {
//                     def containerName = 'my-container'
//                     def requestFilePath = "${pwd()}/request_file.txt"
//
//                     // Copy the request_file.txt into the Docker container
//                     sh "docker cp ${requestFilePath} ${containerName}:/path/in/container/request_file.txt"
//
//                     // Run SQLMap inside the container with the copied request_file.txt
//                     def sqlMapResult = sh(script: "docker exec ${containerName} sqlmap -r /path/in/container/request_file.txt", returnStatus: true)
//
//                     if (sqlMapResult == 0) {
//                         echo "SQLMap completed successfully."
//                     } else {
//                         error "SQLMap encountered an error."
//                     }
//                 }
//             }
//         }

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

//         stage('Static Analysis - Java') {
//             steps {
//                 sh 'echo "Running Static Analysis - Java..."'
//                 script {
//                     // Download Checkstyle JAR if not already downloaded (you can reuse the code from the "Download Checkstyle JAR" stage)
//                     def javaFiles = sh(script: 'find . -name "*.java" -not -path "./src/test/*"', returnStdout: true).trim().split('\n')
//                     def checkstyleResults = [:]
//
//                     for (filePath in javaFiles) {
//                         def fileName = filePath.tokenize('/').last()
//                         echo "Running Checkstyle on ${filePath}"
//                         def checkstyleOutput = sh(script: "java -jar checkstyle.jar -c sun_checks.xml ${filePath}", returnStdout: true, returnStatus: true)
//
//                         if (checkstyleOutput == 0) {
//                             checkstyleResults[fileName] = "No Checkstyle issues found"
//                         } else {
//                             checkstyleResults[fileName] = "Checkstyle issues found"
//                         }
//                     }
//
//                     // Display Checkstyle results summary
//                     echo "Checkstyle Results:"
//                     checkstyleResults.each { fileName, result ->
//                         echo "${fileName}: ${result}"
//                     }
//                 }
//             }
//         }

        stage('Run Nmap Scan Inside Docker Container') {
            steps {
                script {
                    // Define the Nmap scan command
                    def nmapCommand = "nmap -p 80-443 127.0.0.1"

                    // Run the Nmap scan inside the running Docker container
                    def dockerExecResult = sh(script: "docker exec my-container sh -c '${nmapCommand}'", returnStatus: true)

                    if (dockerExecResult == 0) {
                        echo "Nmap scan inside the Docker container executed successfully."
                    } else {
                        error "Failed to execute Nmap scan inside the Docker container."
                    }
                }
            }
        }

//         stage('Docker vulnerability scanning') {
//             steps {
//                 script {
//                 // Define the Nmap scan command
//                     def trivyCommand = "trivy image my-custom-image:latest"
//
//                     // Run the Nmap scan inside the running Docker container
//                     def dockerExecResult = sh(script: "docker exec my-container sh -c '${trivyCommand}'", returnStatus: true)
//
//                     if (dockerExecResult == 0) {
//                         echo "Trivy scan inside the Docker container executed successfully."
//                     } else {
//                         error "Failed to execute Trivy scan inside the Docker container."
//                     }
//                 }
//             }
//         }

        stage('SQL injection testing using SQLMap') {
            steps {
                script {
                // Define the Nmap scan command
                    def sqlCommand = "sqlmap -r request_file.txt"

                    // Run the Nmap scan inside the running Docker container
                    def dockerExecResult = sh(script: "docker exec my-container sh -c '${sqlCommand}'", returnStatus: true)

                    if (dockerExecResult == 0) {
                        echo "SQL injection scan inside the Docker container executed successfully."
                    } else {
                        error "Failed to execute SQL injection scan inside the Docker container."
                    }
                }
            }
        }

        stage('Check Sensitive Information') {
            steps {
                sh 'echo "Checking Sensitive Information..."'
                script {
                    // Define the root directory to search in (same directory as the Jenkinsfile)
                    def rootDir = pwd()  // This gets the current directory (where the Jenkinsfile is located)

                    // Define the subdirectory you want to search in (src folder)
                    def subdirectory = 'src'

                    // Define a regular expression pattern for sensitive information
                    def sensitivePattern = /(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$!%^&*])[A-Za-z\d@#$!%^&*]{8,}/

                    // Flag to keep track of whether sensitive information is found
                    def sensitiveInfoFound = false

                    // Find all files in the workspace, try to exclude .class files or include files only inside src folder
                    def allFiles = findFiles(glob: '**/*')

                    // Loop through all files and search for sensitive information
                    for (file in allFiles) {
                        def filePath = file.path
                        def fileContents = readFile(filePath)

                        if (fileContents =~ sensitivePattern) {
                            echo "Sensitive information found in ${filePath}"
                            sensitiveInfoFound = true
                        }
                    }

                    if (sensitiveInfoFound) {
                        error "Sensitive information found in one or more files. Aborting the build."
                    } else {
                        echo "No sensitive information found."
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