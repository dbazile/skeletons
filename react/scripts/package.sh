#!/bin/bash -e

source "$(dirname $0)/_common.sh"
################################################################################


PROJECT_NAME=$(node -p "require('./package').name")
PROJECT_VERSION=$(node -p "require('./package').version")
ARCHIVE_FILENAME="$PROJECT_NAME-$PROJECT_VERSION.zip"
NODE_ENV=${NODE_ENV:-production}

export NODE_ENV


./scripts/compile.sh "$@"

checkpoint "Building archive at '$ARCHIVE_FILENAME'..."

cd dist
cp ../nginx.conf .

rm -f "$ARCHIVE_FILENAME"
zip -r "$ARCHIVE_FILENAME" . -x "$ARCHIVE_FILENAME"
