package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import scala.util.Try

object Gzip {

  def compress(input: String): Array[Byte] = {
    compress(input.getBytes("utf-8"))
  }

  def compress(input: Array[Byte]): Array[Byte] = {
    val bos = new ByteArrayOutputStream(input.length)
    val gzip = new GZIPOutputStream(bos)
    gzip.write(input)
    gzip.close()
    val compressed = bos.toByteArray
    bos.close()
    compressed
  }

  def decompress(compressed: InputStream): Option[GZIPInputStream] = {
    Option{
      new GZIPInputStream(compressed)
    }
  }

  def decompress(compressed: Array[Byte]): Option[String] =
    Try {
      val inputStream = new GZIPInputStream(new ByteArrayInputStream(compressed))
      scala.io.Source.fromInputStream(inputStream).mkString
    }.toOption
}
