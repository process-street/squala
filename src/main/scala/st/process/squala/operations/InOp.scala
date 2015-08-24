package st.process.squala.operations

import st.process.squala.{Cond, Query}

case class InOp(query: Query) extends Cond {

    lazy val sql = s"in (${query.sql})"

}
