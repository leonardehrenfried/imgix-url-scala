# imgix-url

[![Latest version](https://index.scala-lang.org/leonardehrenfried/imgix-url-scala/imgix-url/latest.svg)](https://index.scala-lang.org/leonardehrenfried/imgix-url-scala/imgix-url)

A tiny library for constructing Imgix URLs. Also available for ScalaJS.

## Installation

```
libraryDependencies += "io.leonard" %%% "imgix-url" % "$version"
```

## Usage

```scala
val builder = io.leonard.imgix.ImgixUrlBuilder("bucketname")
val url = builder.width(150).height(150).build("some-image.jpg")
```

There are many more parameters that can be added to an image URL. Just explore with 
your IDE.
