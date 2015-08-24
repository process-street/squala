package st.process.squala.operations

import st.process.squala.Cond

case class AndOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} and ${rhs.sql})"

}
