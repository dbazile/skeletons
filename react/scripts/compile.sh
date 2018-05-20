#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint "Compiling into 'dist'..."

rm -rf dist
webpack --colors --progress --hide-modules "$@"
