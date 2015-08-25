package st.process.squala

import st.process.squala.expressions._

// Loosely following this BNF: http://savage.net.au/SQL/sql-92.bnf.html

trait Query {

    def sql: String

}

case class Select(columnNames: Seq[String]) extends Query {

    def from(tableName: String) = From(Select(columnNames), tableName, optionalAlias = None, joins = List.empty)

    lazy val sql = "select " + columnNames.mkString(", ")

    override def toString = sql

}

case class From(selectList: Select, tableName: String, optionalAlias: Option[String], joins: List[Join]) extends Query {

    def as(alias: String) = copy(optionalAlias = Some(alias))

    def innerJoin(tableName: String, alias: String) = JoinBuilder(this, "inner", tableName, alias)

    def where = WhereBuilder(this)

    lazy val sql = (List(
            Some(selectList.sql),
            Some("from"),
            Some(tableName),
            optionalAlias.map(doubleQuote)
        ).flatten ++ joins.map(_.sql)).mkString(" ")

    override def toString = sql

}

case class JoinBuilder(from: From, tpe: String, tableName: String, alias: String) {

    def on (cond: Cond) = from.copy(joins = from.joins :+ Join(tpe, tableName, alias, cond))
    
}

case class Join(tpe: String, tableName: String, alias: String, cond: Cond) {

    lazy val sql = s"$tpe join $tableName ${doubleQuote(alias)} on ${cond.sql}"

}

case class WhereBuilder(from: From) extends Prefix[Where] {

    def apply(cond: Cond) = Where(from, cond)

    override def not = PrefixOpBuilder(NotOp)(Where(from, _))

    override def exists(q: Query) = Where(from, ExistsOp(q))

    override def in(q: Query) = Where(from, InOp(q))

}

case class Where(from: From, cond: Cond) extends Query {

    lazy val sql = List(from.sql, "where " + cond.sql).mkString(" ")

    def and = InfixOpBuilder(AndOp, cond)(Where(from, _))

    def or = InfixOpBuilder(OrOp, cond)(Where(from, _))

    override def toString = sql

}

trait Cond extends Prefix[Cond] {

    def ===(that: Cond) = EqualsOp(this, that)

    def !==(that: Cond) = NotEqualsOp(this, that)

    def and = InfixOpBuilder(AndOp, this)(identity)

    def or = InfixOpBuilder(OrOp, this)(identity)

    def isNull = IsNullOp(this)

    def isNotNull = IsNotNullOp(this)

    override def not = PrefixOpBuilder(NotOp)(identity)

    override def exists(q: Query) = ExistsOp(q)

    override def in(q: Query) = InOp(q)

    def sql: String

}

case class InfixOpBuilder[A](op: (Cond, Cond) => Cond, lhs: Cond)(f: Cond => A) extends Prefix[A] {

    def apply(rhs: Cond) = f(op(lhs, rhs))

    def not = PrefixOpBuilder(NotOp) { rhs => f(op(lhs, rhs))}

    override def exists(q: Query) = f(op(lhs, ExistsOp(q)))

    override def in(q: Query) = f(op(lhs, InOp(q)))

}

case class PrefixOpBuilder[A](op: Cond => Cond)(f: Cond => A) extends Prefix[A] {

    def apply(operand: Cond) = f(op(operand))

    def not = PrefixOpBuilder[A](NotOp) { operand => f(op(operand)) }

    override def exists(q: Query) = f(op(ExistsOp(q)))

    override def in(q: Query) = f(op(InOp(q)))

}

trait Prefix[A] {

    def not: PrefixOpBuilder[A]

    def exists(q: Query): A

    def in(q: Query): A

}

object SqualaImplicits {

    implicit def queryToSqlExpr(query: Query): SqlExpr = SqlExpr(query.sql)

    implicit def stringToRefExpr(columnName: String): RefExpr = RefExpr(columnName)

    implicit def tuple2ToQualifiedRefExpr(tuple: (String, String)): QualifiedRefExpr = tuple match {
        case (qualifier, columnName) => QualifiedRefExpr(qualifier, columnName)
    }

    implicit def booleanToBoolExpr(value: Boolean): BoolExpr = BoolExpr(value)

    implicit def intToIntExpr(value: Int): IntExpr = IntExpr(value)

}

object Squala {

    def select(columnNames: String*) = Select(columnNames)

    // TODO These are temporary, they should not require import

    def literal(value: String) = StrExpr(value)

    def sql(sql: String) = SqlExpr(sql)

}