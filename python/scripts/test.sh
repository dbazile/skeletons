#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


./scripts/mypy.sh

checkpoint 'Running tests with coverage'

coverage run --source=myproject/ -m unittest
coverage report
