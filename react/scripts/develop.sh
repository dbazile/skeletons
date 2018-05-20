#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


DEFAULT_PORT=3000


webpack-dev-server \
	--inline \
	--hot \
	--colors \
	$([[ "$*" =~ "--port" ]] || echo "--port $DEFAULT_PORT") \
	"$@"
