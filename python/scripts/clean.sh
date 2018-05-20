#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


checkpoint 'Cleaning up'

rm -rfv \
    '.coverage' \
    '.mypy_cache' \
    'myproject.tgz' \
    'myproject/__pycache__' \
    'vendor'
