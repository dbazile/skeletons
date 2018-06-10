#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint "Compiling into 'dist'..."

rm -rf dist
vue-cli-service build "$@"
