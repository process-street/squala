package st.process.squala.expressions

import st.process.squala.Cond

case class StrExpr(value: String) extends Cond {

    lazy val sql = quote(value)

    private def quote(value: String) = "'%s'".format(value.replaceAll("'", "''"))

}


