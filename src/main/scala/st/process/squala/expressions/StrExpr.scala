package st.process.squala.expressions

import st.process.squala.{Cond, singleQuote}

case class StrExpr(value: String) extends Cond {

    lazy val sql = singleQuote(value)

}


