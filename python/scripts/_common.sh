#!/bin/bash -e

cd $(dirname $(dirname $0))
################################################################################


VENV_HOME=.venv


################################################################################

checkpoint() {
    printf '\n\033[32m--- %s\033[0m\n\n' "$*"
}

################################################################################

if [ ! -d "$VENV_HOME" ]; then
    printf "It looks like your development environment is not set up.\n\nSet it up now?\n"
    read -p "(y/N) " -r

    if [[ ! "$REPLY" =~ ^[Yy] ]]; then
        printf '\n\e[31m%s\e[0m\n' "Exiting..."
        exit 1
    fi

    checkpoint "Creating $(python3 --version) environment at '$VENV_HOME'"

    rm -rf "$VENV_HOME"
    python3 -m venv "$VENV_HOME"
    source "$VENV_HOME/bin/activate"

    checkpoint 'Installing Python dependencies'

	pip install -r requirements.txt -r requirements-dev.txt
fi

################################################################################


source "$VENV_HOME/bin/activate"
