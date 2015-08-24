package st.process.squala.expressions

import st.process.squala.Cond

case class BoolExpr(value: Boolean) extends Cond {

    lazy val sql = if (value) "1" else "0"

}
