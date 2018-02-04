#!/usr/bin/env bash
set -eu

if [[ "$TRAVIS_SECURE_ENV_VARS" == true ]]; then
  echo "Publishing..."
  git log | head -n 20

  if [ -n "$TRAVIS_TAG" ]; then
    echo "Tag push, publishing stable release to Sonatype."
    sbt publishSigned sonatypeReleaseAll
  else
    echo "Merge, publishing snapshot to Sonatype."
    sbt publish
  fi
else
  echo "Skipping publish, branch=$TRAVIS_BRANCH"
fi
