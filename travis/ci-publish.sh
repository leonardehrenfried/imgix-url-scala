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
    openssl aes-256-cbc -K $encrypted_13ad22db1e75_key -iv $encrypted_13ad22db1e75_iv -in travis/all.gpg.enc -out travis/all.gpg -d
    gpg --import travis/all.gpg
    ls -la ~/.gnupg
    sbt publishSigned
  fi
else
  echo "Skipping publish, branch=$TRAVIS_BRANCH"
fi
