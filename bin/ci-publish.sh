#!/usr/bin/env bash
set -eu

if [[ "$TRAVIS_SECURE_ENV_VARS" == true ]]; then
  echo "Publishing..."
  git log | head -n 20
  #echo "$PGP_SECRET" | base64 --decode | gpg --import

  openssl aes-256-cbc -K $encrypted_0261605d70ea_key -iv $encrypted_0261605d70ea_iv -in travis/secrets.tar.enc -out travis/secrets.tar -d
  tar xv -C travis -f travis/secrets.tar;

  ls -la

  if [ -n "$TRAVIS_TAG" ]; then
    echo "Tag push, publishing stable release to Sonatype."
    sbt publishSigned sonatypeReleaseAll
  else
    echo "Merge, publishing snapshot to Sonatype."
    sbt publishSigned
  fi
else
  echo "Skipping publish, branch=$TRAVIS_BRANCH"
fi
