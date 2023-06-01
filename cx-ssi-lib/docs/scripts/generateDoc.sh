#!/bin/bash
# generates a combined PDF out of all all md files

BASEDIR="$(dirname "$(dirname "$(readlink -fm "$0")")")"

docker build --no-cache --file "$BASEDIR"/pandoc.Dockerfile -t catena-x/pandoc-generator:latest .
containerId=$(docker create catena-x/pandoc-generator:latest)
docker cp "$containerId":/data/Documentation.pdf "$BASEDIR"
