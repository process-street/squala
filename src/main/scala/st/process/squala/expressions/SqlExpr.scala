package st.process.squala.expressions

import st.process.squala.SearchCond

case class SqlExpr(value: String) extends SearchCond {
    val sql = value
}
