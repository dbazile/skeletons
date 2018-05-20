#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint 'Running typechecks with mypy'

mypy "$@" -- myproject/ tests/
