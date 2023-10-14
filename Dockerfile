# Use a base image suitable for your needs
FROM ubuntu:latest

# Install required packages
RUN apt-get update && apt-get install -y \
    docker.io \
    nmap \
    sqlmap \
    wget \
    gnupg

# Download and install Trivy for arm64
RUN wget https://github.com/aquasecurity/trivy/releases/download/v0.19.2/trivy_0.19.2_Linux-arm64.deb && \
    dpkg -i trivy_0.19.2_Linux-arm64.deb && \
    rm trivy_0.19.2_Linux-arm64.deb

# Install SonarQube
RUN wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-9.4.0.22115.zip && \
    unzip sonarqube-9.4.0.22115.zip && \
    mv sonarqube-9.4.0.22115 /opt/sonarqube

# Set the entry point
CMD ["sleep", "infinity"]
