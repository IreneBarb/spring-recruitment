# Use a lightweight base image
FROM alpine:latest

# Update the package repository and install required packages
RUN apk update && \
    apk add nmap sqlmap

# Set the entry point
CMD ["/bin/sh"]
