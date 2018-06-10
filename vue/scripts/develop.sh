#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


DEFAULT_PORT=3000


vue-cli-service serve \
	$([[ "$*" =~ "--port" ]] || echo "--port $DEFAULT_PORT") \
	"$@"
