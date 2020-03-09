package nahguam.fractal

import java.awt.Color

fun gradient(color1: Color, color2: Color, size: Int): IntArray {
  val colors = IntArray(size)

  val redStep = (color2.red - color1.red) / size
  val greenStep = (color2.green - color1.green) / size
  val blueStep = (color2.blue - color1.blue) / size

  var red = color1.red
  var green = color1.green
  var blue = color1.blue

  for (i in 0 until size) {
    colors[i] = Color(red, green, blue).rgb
    red += redStep
    green += greenStep
    blue += blueStep
  }
  return colors
}
