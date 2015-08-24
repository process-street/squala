package st.process.squala.expressions

import st.process.squala.SearchCond

case class RefExpr(columnName: String) extends SearchCond {
     lazy val sql = columnName
}
