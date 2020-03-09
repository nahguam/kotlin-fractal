package nahguam.fractal

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.OutputStream
import javax.imageio.ImageIO
import kotlin.math.pow

private val size = Size(256, 256)
private val colors = listOf(
  gradient(Color.BLACK, Color.RED, 64),
  gradient(Color.RED, Color.GREEN, 32),
  gradient(Color.GREEN, Color.BLUE, 16),
  gradient(Color.BLUE, Color.BLACK, 8)
).reduce(IntArray::plus)
private val function = julia(Complex(-0.7, 0.27015))
//private val function = mandelbrot()

fun OutputStream.tile(z: Double, x: Double, y: Double) {
  rectangle(z, x, y).fractal().image().output("png", this)
}

fun rectangle(z: Double, x: Double, y: Double): Rectangle {
  val size = 4 / 2.0.pow(z)
  val left = (x * size) - 2
  val top = (y * size) - 2
  return Rectangle(left, top, size, size)
}

fun Rectangle.fractal() = Fractal(this, size, colors, function)

fun BufferedImage.output(format: String, output: OutputStream) {
  ImageIO.write(this, format, output)
}
