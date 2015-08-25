package st.process.squala.operations

import st.process.squala.{Cond, Query}

case class ExistsOp(query: Query) extends Cond {

    lazy val sql = s"exists (${query.sql})"

}
