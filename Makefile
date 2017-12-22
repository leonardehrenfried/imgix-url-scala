SCALAJS_VERSION:="1.0.0-M2"

release:
	sbt release
	git checkout `git describe --abbrev=0 --tags`
	SCALAJS_VERSION=${SCALJS_VERSION} sbt imgix-urlJS/publishSigned
	git checkout master
