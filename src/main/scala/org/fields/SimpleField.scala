package org.fields

import org.Pos

/**
 * Created by Alexey.Zavalin on 26.11.2014.
 */
class SimpleField extends RectField {

  override def width = 20
  override def height = 10

  override def hasWall(p: Pos) =
    (p.y == 4 && p.x >= 5 && p.x <= 15) ||
    (p.y == 5 && p.x >= 5 && p.x <= 10)

}
