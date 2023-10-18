# Use a base image suitable for your needs
FROM ubuntu:latest

# Install required packages
RUN apt-get update && apt-get install -y \
    docker.io \
    nmap \
    sqlmap \
    wget \
    gnupg

# Clone your GitHub repository into the container
RUN git clone https://github.com/IreneBarb/spring-recruitment.git

RUN git clone https://github.com/vulnersCom/nmap-vulners.git

# Download and install Trivy for arm64
RUN wget https://github.com/aquasecurity/trivy/releases/download/v0.19.2/trivy_0.19.2_Linux-arm64.deb && \
    dpkg -i trivy_0.19.2_Linux-arm64.deb && \
    rm trivy_0.19.2_Linux-arm64.deb

#RUN apt-get update && apt-get install -y unzip

# Install SonarQube
#RUN wget https://binaries.sonarsource.com/Distribution/SonarQube.MSBuild.Runner-0.9.zip

# Extract the archive
#RUN unzip SonarQube.MSBuild.Runner-0.9.zip -d /opt/sonarqube

# Make the SonarQube Scanner executable
#RUN chmod +x /opt/sonarqube/SonarQube.MSBuild.Runner.exe

# Download and install Nmap Vulners script
RUN mkdir -p /usr/share/nmap/scripts
RUN wget https://raw.githubusercontent.com/vulnersCom/nmap-vulners/master/vulners.nse -P /usr/share/nmap/scripts

# Make the script executable
RUN chmod +x /usr/share/nmap/scripts/vulners.nse

# Set the entry point
CMD ["sleep", "infinity"]
