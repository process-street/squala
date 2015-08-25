package st.process.squala.expressions

import st.process.squala.{Cond, doubleQuote}

case class QualifiedRefExpr(qualifier: String, columnName: String) extends Cond {

    lazy val sql = doubleQuote(qualifier) + "." + columnName

}
