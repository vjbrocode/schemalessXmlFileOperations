#!/bin/bash

##
# This script builds docker image of the employee record book service
# Run this script AFTER compiling the code and building the ems.jar in target folder
# 
# The docker image will be loaded in local docker.
# Image will also be saved as a tar file to be used to run it on remote docker engine / servers.
##

readonly IMAGE=ems
readonly VERSION=1.0

function is_docker_installed() {
    if [ -x "$(command -v docker)" ]; then
        return 0
    fi
    echo "FATAL! Cannot find 'docker' in PATH."
    return 1
}

function is_docker_running() {
    if pgrep -f docker > /dev/null; then
        return 0
    fi
    echo "FATAL! Cannot find a running docker daemon."
    return 1
}

function build_image() {
    docker build -t $IMAGE:$VERSION .
    rc=$?

    if [ $rc -eq 0 ]; then
        echo "Image $IMAGE:$VERSION built and installed successfully."
    else
        echo "Failed to build $IMAGE:$VERSION."
    fi

    return $rc
}

function export_image() {
    docker save -o ${IMAGE//-/}_$VERSION.tar $IMAGE:$VERSION
    rc=$?

    if [ $rc -eq 0 ]; then
        echo "Image $IMAGE:$VERSION exported successfully to $IMAGE $VERSION.tar"
    else
        echo "Failed to export $IMAGE:$VERSION."
    fi
}

#### main
if is_docker_installed && is_docker_running; then
    if build_image; then
    	echo "exporting image as tar..."
        export_image
    else
        echo "Canceling image export."
    fi
else
    echo "Please ensure docker is installed and running on this host."
fi