package st.process.squala.expressions

import st.process.squala.SearchCond

case class IntExpr(value: Int) extends SearchCond {
     lazy val sql = value.toString
 }
