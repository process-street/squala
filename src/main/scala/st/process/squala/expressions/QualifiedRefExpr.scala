package st.process.squala.expressions

import st.process.squala.Cond

case class QualifiedRefExpr(qualifier: String, columnName: String) extends Cond {

    lazy val sql = quote(qualifier) + "." + columnName

    private def quote(value: String) = "\"%s\"".format(value.replaceAll("\"", "\"\""))

}
