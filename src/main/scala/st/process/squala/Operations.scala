package st.process.squala

// Infix

case class AndOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} and ${rhs.sql})"

}

case class OrOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} or ${rhs.sql})"

}

case class EqualsOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} = ${rhs.sql})"

}

case class NotEqualsOp(lhs: Cond, rhs: Cond) extends Cond {

    lazy val sql = s"(${lhs.sql} <> ${rhs.sql})"

}

// Prefix

case class NotOp(cond: Cond) extends Cond {

    lazy val sql = s"not (${cond.sql})"

}

case class ExistsOp(query: Query) extends Cond {

    lazy val sql = s"exists (${query.sql})"

}

case class InOp(query: Query) extends Cond {

    lazy val sql = s"in (${query.sql})"

}

// Postfix

case class IsNullOp(cond: Cond) extends Cond {

    lazy val sql = s"(${cond.sql} is null)"

}

case class IsNotNullOp(cond: Cond) extends Cond {

    lazy val sql = s"(${cond.sql} is not null)"

}