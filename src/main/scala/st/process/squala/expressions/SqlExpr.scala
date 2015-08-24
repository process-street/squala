package st.process.squala.expressions

import st.process.squala.Cond

case class SqlExpr(value: String) extends Cond {

    val sql = value

}
