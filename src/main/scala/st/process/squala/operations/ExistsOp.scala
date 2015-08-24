package st.process.squala.operations

import st.process.squala.{Query, SearchCond}

case class ExistsOp(query: Query) extends SearchCond {
    lazy val sql = s"exists (${query.sql})"
}
