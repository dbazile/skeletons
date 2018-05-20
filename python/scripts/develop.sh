#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint 'Running server'

gunicorn \
	--reload \
	myproject:app
