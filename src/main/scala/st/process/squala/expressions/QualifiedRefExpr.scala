package st.process.squala.expressions

import st.process.squala.SearchCond

case class QualifiedRefExpr(qualifier: String, columnName: String) extends SearchCond {

    lazy val sql = quote(qualifier) + "." + columnName

    private def quote(value: String) = "\"%s\"".format(value.replaceAll("\"", "\"\""))

}
