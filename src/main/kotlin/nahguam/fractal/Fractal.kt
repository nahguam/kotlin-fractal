package nahguam.fractal

import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import kotlin.math.hypot

class Complex(
  private val real: Double,
  private val imaginary: Double
) {
  val abs
    get() = hypot(real, imaginary)

  operator fun plus(other: Complex) = Complex(
    real + other.real,
    imaginary + other.imaginary
  )

  operator fun times(other: Complex) = Complex(
    (real * other.real) - (imaginary * other.imaginary),
    (real * other.imaginary) + (imaginary * other.real)
  )
}

data class Rectangle(val left: Double, val top: Double, val width: Double, val height: Double)
data class Size(val width: Int, val height: Int)
data class Fractal(
  val rectangle: Rectangle,
  val size: Size,
  val colors: IntArray,
  val function: (Complex, Complex) -> Complex
)

fun julia(c: Complex) = { z: Complex, _: Complex -> (z * z) + c }
fun mandelbrot() = { z: Complex, z0: Complex -> (z * z) + z0 }

fun Fractal.image(): BufferedImage {
  val image = BufferedImage(size.width, size.height, TYPE_INT_RGB)
  for (x in 0 until size.width) {
    for (y in 0 until size.height) {
      val x0 = rectangle.left + (x * (rectangle.width / size.width))
      val y0 = rectangle.top + (y * (rectangle.height / size.height))
      val z0 = Complex(x0, y0)
      val i: Int = escape(z0, colors.size, function)
      image.setRGB(x, y, colors[i])
    }
  }
  return image
}

internal fun escape(z0: Complex, max: Int, function: (Complex, Complex) -> Complex): Int {
  var z = z0
  for (i in 0 until max) {
    if (z.abs > 2.0) {
      return i
    }
    z = function(z, z0)
  }
  return max - 1
}
