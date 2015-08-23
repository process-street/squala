package st.process.squala.operations

import st.process.squala.SearchCond

case class AndOp(lhs: SearchCond, rhs: SearchCond) extends SearchCond {
    lazy val sql = s"(${lhs.sql} and ${rhs.sql})"
}
