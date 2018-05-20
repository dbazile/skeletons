#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint 'Cleaning up'

rm -rfv \
	'dist' \
	'npm-debug.log' \
	'yarn-error.log'
