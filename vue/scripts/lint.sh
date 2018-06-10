#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


vue-cli-service lint "$@"
