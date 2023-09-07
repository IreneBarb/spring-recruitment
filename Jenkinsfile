pipeline {
    agent {
        docker {
            // Specify the custom Docker image tag
            image 'my-custom-image:latest'  // Use the tag you created
        }
    }

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

        stage('Dynamic Security Checks') {
            steps {
                sh 'echo "Running Dynamic Security Checks..."'
                script {
                    def myDockerImage = docker.image('my-custom-image:latest')
                    myDockerImage.inside {
                        // Perform system ports scanning and vulnerability scanning with Nmap
                        def nmapResult = sh(script: 'nmap -Pn -p1-65535 -T4 -A -oX nmap_output.xml target_host', returnStatus: true)

                        if (nmapResult == 0) {
                            echo "Nmap scan successful. Check 'nmap_output.xml' for results."
                        } else {
                            error "Nmap scan failed."
                        }

    //                     // Perform Docker vulnerability scanning
    //                     def dockerVulnerabilityResult = sh(script: 'docker scan your_docker_image', returnStatus: true)
    //
    //                     if (dockerVulnerabilityResult == 0) {
    //                         echo "Docker vulnerability scan successful."
    //                     } else {
    //                         error "Docker vulnerability scan found issues."
    //                     }

                        // Perform SQL injection testing using SQLMap (requires SQLMap installation)
                        def sqlMapResult = sh(script: 'sqlmap -r request_file.txt', returnStatus: true)

                        if (sqlMapResult == 0) {
                            echo "SQL injection testing with SQLMap successful."
                        } else {
                            error "SQLMap found SQL injection vulnerabilities."
                        }
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