pipeline {
    agent any
//         {
//             dockerfile {
//                 filename '/Users/irenebarbouni/Documents/backendRecruitment/backend/Dockerfile'
//             }
//         }
      tools {
        'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'Docker 18.09.0'
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
                    def dockerRunResult = sh(script: 'docker run -d --name my-container my-custom-image:latest', returnStatus: true)

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

//         stage('SonarQube Analysis - static code analyzer') {
//             steps {
//                 sh 'docker stop sonarqube'
//                 sh 'docker rm sonarqube'
//                 sh 'docker pull sonarqube:latest'
//                 sh 'docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 -e SONARQUBE_JDBC_URL=jdbc:h2:tcp://192.168.1.249:9092/sonar -e SONARQUBE_JDBC_USERNAME=sonar -e SONARQUBE_JDBC_PASSWORD=sonar sonarqube:latest'
//                 sh 'docker run --rm -e SONAR_HOST_URL=http://192.168.1.249:9000 -e SONAR_LOGIN=admin -e SONAR_PASSWORD=admin -v "$PWD:/src" sonarsource/sonar-scanner-cli'
//             }
//         }

        stage('Run Nmap Scan') {
            steps {
                script {
//                     sh 'curl -LO https://nmap.org/dist/nmap-7.94-1.x86_64.rpm'
//                     sh 'tar -xzvf nmap-*.tar.gz'
                       sh '/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)" -y'
//                     sh 'git clone https://github.com/nmap/nmap.git'
//                     sh 'cd nmap'
//                     sh 'ls'
//                     sh './autogen.sh'   // Run the autogen script
//                     sh './configure'   // Configure the build
//                     sh 'make'   // Build Nmap
//                     sh 'sudo make install'   // Install Nmap

//                     sh 'git clone https://github.com/vulnersCom/nmap-vulners.git'
                    sh 'nmap -sV --script nmap-vulners/ 127.0.0.1'
                }
            }
        }

        stage('Run Nmap Scan Inside Docker Container') {
            steps {
                script {
                    // Define the Nmap scan command
                    def nmapCommand = "nmap 8-p 0-10000 127.0.0.1"

                    def nmapVulnerabilitiesCommand = "nmap -sV --script=vulners 127.0.0.1"

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