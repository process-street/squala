package st.process.squala

// Loosely following this BNF: http://savage.net.au/SQL/sql-92.bnf.html

case class Select(columnNames: Seq[String]) {

    def from(tableName: String) = From(Select(columnNames), tableName, optionalAlias = None)

    override def toString = "select " + columnNames.mkString(", ")

}

case class From(selectList: Select, tableName: String, optionalAlias: Option[String]) {

    def as(alias: String) = copy(optionalAlias = Some(alias))
    
    def where(searchCondition: SearchCond) = Where(this, searchCondition)

    override def toString =
        List(Some(selectList.toString), Some("from"), Some(tableName), optionalAlias)
        .flatten.mkString(" ")

}

case class Where(from: From, searchCondition: SearchCond) {

    override def toString = List(from.toString, "where " + searchCondition.toString).mkString(" ")

}

abstract class SearchCond {

    def ===(that: SearchCond) = EqualsOp(this, that)

    def and(that: SearchCond) = AndOp(this, that)

    def or(that: SearchCond) = OrOp(this, that)

}

case class EqualsOp(lhs: SearchCond, rhs: SearchCond) extends SearchCond {
    override def toString = s"($lhs = $rhs)"
}

case class AndOp(lhs: SearchCond, rhs: SearchCond) extends SearchCond {
    override def toString = s"($lhs and $rhs)"
}

case class OrOp(lhs: SearchCond, rhs: SearchCond) extends SearchCond {
    override def toString = s"($lhs or $rhs)"
}

case class RefExpr(columnName: String) extends SearchCond {
    override def toString = columnName
}

case class StrExpr(value: String) extends SearchCond {
    override def toString = Squala.quote(value)
}

case class IntExpr(value: Int) extends SearchCond {
    override def toString = value.toString
}

object Squala {

    implicit def symbolToRefExpr(columnName: Symbol): RefExpr = RefExpr(columnName.name)
    implicit def stringToStrExpr(value: String): StrExpr = StrExpr(value)
    implicit def intToIntExpr(value: Int): IntExpr = IntExpr(value)

    def select(columnNames: String*) = Select(columnNames)

    def quote(value: String) = "'%s'".format(escape(value))
    def escape(value: String) = value.replaceAll("'", "''")

}