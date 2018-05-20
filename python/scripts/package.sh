#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


ARCHIVE_FILENAME='myproject.tgz'
FILE_LIST="
	myproject/
	vendor/
	requirements.txt
	runtime.txt
"

################################################################################

checkpoint 'Collecting Python dependencies'

rm -rf vendor
pip download -d vendor -r requirements.txt

################################################################################

checkpoint "Building archive '$ARCHIVE_FILENAME'"

rm -f "$ARCHIVE_FILENAME"
tar -czvf "$ARCHIVE_FILENAME" \
	--exclude '__pycache__' \
	--exclude 'tests' \
	--exclude '.*' \
	--exclude '.*' \
	--exclude '*.pyc' \
	--exclude '*.pyo' \
	-- \
	$FILE_LIST
