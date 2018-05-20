#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


tslint -t stylish '{src,test}/**/*.{ts,tsx}' "$@"
