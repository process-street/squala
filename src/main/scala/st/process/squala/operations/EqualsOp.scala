package st.process.squala.operations

import st.process.squala.Cond

case class EqualsOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} = ${rhs.sql})"

}
