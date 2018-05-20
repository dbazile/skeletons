#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint 'Running linter...'
./scripts/lint.sh


checkpoint 'Running type checks...'
tsc --noEmit --pretty


checkpoint 'Running unit tests...'
jest "$@"
