package st.process.squala.expressions

import st.process.squala.Cond

case class IntExpr(value: Int) extends Cond {

    lazy val sql = value.toString

}
