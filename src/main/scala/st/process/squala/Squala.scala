package st.process.squala

import st.process.squala.expressions._
import st.process.squala.operations._

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

case class WhereBuilder(from: From) extends Unary[Where] {

    def apply(cond: Cond) = Where(from, cond)

    override def not = NotBuilder(Where(from, _))

    override def exists(q: Query) = Where(from, ExistsOp(q))

    override def in(q: Query) = Where(from, InOp(q))

}

case class Where(from: From, cond: Cond) extends Query {

    lazy val sql = List(from.sql, "where " + cond.sql).mkString(" ")

    def and = BinaryOpBuilder(AndOp, cond, Where(from, _))

    def or = BinaryOpBuilder(OrOp, cond, Where(from, _))

    override def toString = sql

}

trait Cond extends Unary[Cond] {

    def ===(that: Cond) = EqualsOp(this, that)

    def !==(that: Cond) = NotEqualsOp(this, that)

    def and = BinaryOpBuilder(AndOp, this, identity)

    def or = BinaryOpBuilder(OrOp, this, identity)

    override def not = NotBuilder(identity)

    override def exists(q: Query) = ExistsOp(q)

    override def in(q: Query) = InOp(q)

    def sql: String

}

case class BinaryOpBuilder[A](binaryOp: (Cond, Cond) => Cond, cond: Cond, f: Cond => A) extends Unary[A] {

    def apply(that: Cond) = f(binaryOp(cond, that))

    def not = NotBuilder { that => f(binaryOp(cond, that))}

    override def exists(q: Query) = f(binaryOp(cond, ExistsOp(q)))

    override def in(q: Query) = f(binaryOp(cond, InOp(q)))

}

case class NotBuilder[A](f: Cond => A) extends Unary[A] {

    def apply(that: Cond) = f(NotOp(that))

    def not = NotBuilder { that => f(NotOp(that))}

    override def exists(q: Query) = f(NotOp(ExistsOp(q)))

    override def in(q: Query) = f(NotOp(InOp(q)))

}

trait Unary[A] {

    def not: NotBuilder[A]

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