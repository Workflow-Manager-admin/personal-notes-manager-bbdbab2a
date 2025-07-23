#!/bin/bash
cd /home/kavia/workspace/code-generation/personal-notes-manager-bbdbab2a/frontend_android
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

