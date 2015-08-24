package st.process.squala.operations

import st.process.squala.Cond

case class NotOp(cond: Cond) extends Cond {

    lazy val sql = s"not (${cond.sql})"

}
