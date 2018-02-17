#!/usr/bin/env bash
set -eu

if [[ "$TRAVIS_SECURE_ENV_VARS" == true ]]; then
  if [ -n "$TRAVIS_TAG" ]; then
    echo "Tag push, publishing stable release to Sonatype."
    sbt sonatypeReleaseAll
  else
    echo "Not a tag, hence not releasing"
  fi
else
  echo "Skipping publish, branch=$TRAVIS_BRANCH"
fi
