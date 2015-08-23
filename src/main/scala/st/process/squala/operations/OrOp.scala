package st.process.squala.operations

import st.process.squala.SearchCond

case class OrOp(lhs: SearchCond, rhs: SearchCond) extends SearchCond {
    lazy val sql = s"(${lhs.sql} or ${rhs.sql})"
}
