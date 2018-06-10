#!/bin/bash -e

cd $(dirname $(dirname $0))
################################################################################


NODE_MAJOR=9


################################################################################

checkpoint() {
    printf '\n\033[32m--- %s\033[0m\n\n' "$*"
}

################################################################################

if [ ! -d node_modules ]; then
	printf "It looks like your development environment is not set up.\n\nSet it up now?\n"
	read -p "(y/N) " -r

	if [[ ! "$REPLY" =~ ^[Yy] ]]; then
		printf '\n\e[31m%s\e[0m\n' "Exiting..."
		exit 1
	fi

	checkpoint 'Installing Node dependencies'

	if ! node -e "assert(process.version.slice(1).split('.')[0] >= $NODE_MAJOR)" 2>/dev/null; then
		printf '\e[31m%s\e[0m\n' "Node ${NODE_MAJOR}.0.0 or higher must be installed first"
		exit 1
	fi

    if ! hash yarn 2>/dev/null; then
        printf '\e[33m%s\e[0m\n\n' "Warning: yarn not installed; falling back to npm..."
        npm install
    else
        yarn install
    fi
fi

################################################################################


export PATH="$PWD/node_modules/.bin:$PATH"
