package st.process.squala.expressions

import st.process.squala.Cond

case class RefExpr(columnName: String) extends Cond {

    lazy val sql = columnName

}
