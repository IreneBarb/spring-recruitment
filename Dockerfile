# Use a base image suitable for your needs
 FROM ubuntu:latest

# Install required packages
 RUN apt-get update && apt-get install -y \
     docker.io \
     nmap \
     sqlmap

# Set the entry point
CMD ["sleep", "infinity"]
