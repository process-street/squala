package st.process.squala.expressions

import st.process.squala.SearchCond

case class StrExpr(value: String) extends SearchCond {

    lazy val sql = quote(value)

    private def quote(value: String) = "'%s'".format(value.replaceAll("'", "''"))

}


