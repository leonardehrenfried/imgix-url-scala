package io.leonard.imgix

import io.leonard.imgix.Parameter.{Crop, Fit, Height, Width}

abstract class Parameter(val value: String)
object Parameter {
  case object Width        extends Parameter("w")
  case object Height       extends Parameter("h")
  case object Fit          extends Parameter("fit")
  case object Crop         extends Parameter("crop")
  case object OutputFormat extends Parameter("fm")
}

abstract class FitMode(val value: String)
object FitMode {
  case object Clamp    extends FitMode("clamp")
  case object Clip     extends FitMode("clip")
  case object Crop     extends FitMode("crop")
  case object FaceArea extends FitMode("facearea")
  case object Fill     extends FitMode("fill")
  case object FillMax  extends FitMode("fillmax")
  case object Max      extends FitMode("max")
  case object Min      extends FitMode("min")
  case object Scale    extends FitMode("scale")
}

abstract class CropMode(val value: String)
object CropMode {
  case object Top        extends CropMode("top")
  case object Left       extends CropMode("left")
  case object Right      extends CropMode("right")
  case object Faces      extends CropMode("faces")
  case object FocalPoint extends CropMode("focalpoint")
  case object Edges      extends CropMode("edges")
  case object Entropy    extends CropMode("entropy")
}

/**
  * https://docs.imgix.com/apis/url/format/fm
  */
abstract class OutputFormat(val value: String)
object OutputFormat {
  case object Gif   extends OutputFormat("gif")
  case object Jp2   extends OutputFormat("jp2")
  case object Jpg   extends OutputFormat("jpg")
  case object Json  extends OutputFormat("json")
  case object Jxr   extends OutputFormat("jxr")
  case object Pjpg  extends OutputFormat("pjpg")
  case object Mp4   extends OutputFormat("mp4")
  case object Png   extends OutputFormat("png")
  case object Png8  extends OutputFormat("png8")
  case object Png32 extends OutputFormat("png32")
  case object Webp  extends OutputFormat("webp")
}

case class ImgixUrlBuilder(bucket: String, params: UrlParameter = Map.empty) {

  def width(int: Int)                = addParam(Width -> int)
  def height(int: Int)               = addParam(Height -> int)
  def crop(mode: CropMode)           = addParam(Fit -> FitMode.Crop.value, Crop -> mode.value)
  def fit(fit: FitMode)              = addParam(Crop -> fit.value)
  def outputFormat(fm: OutputFormat) = addParam(Parameter.OutputFormat -> fm.value)
  def build(imgName: String): String = s"https://$bucket.imgix.net/$imgName$params2string"

  private def addParam(elem1: (Parameter, Any), elem2: (Parameter, Any)*): ImgixUrlBuilder =
    copy(params = params + elem1 ++ elem2)

  private lazy val params2string: String = {
    if (params.isEmpty) ""
    else {
      params
        .map {
          case (key, value) => s"${key.value}=$value"
        }
        .mkString("?", "&", "")
    }
  }
}
