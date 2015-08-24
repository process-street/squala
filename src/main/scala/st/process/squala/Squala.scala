package st.process.squala

import st.process.squala.expressions._
import st.process.squala.operations._

// Loosely following this BNF: http://savage.net.au/SQL/sql-92.bnf.html

trait Query {

    def exists = ExistsOp(this)

    def sql: String

}

case class Select(columnNames: Seq[String]) extends Query {

    def from(tableName: String) = From(Select(columnNames), tableName, optionalAlias = None, joins = List.empty)

    lazy val sql = "select " + columnNames.mkString(", ")

    override def toString = sql

}

case class From(selectList: Select, tableName: String, optionalAlias: Option[String], joins: List[Join]) extends Query {

    def as(alias: String) = copy(optionalAlias = Some(alias))

    def innerJoin(tableName: String, alias: String) = Joiner(this, "inner", tableName, alias)

    def where(searchCondition: Cond) = Where(this, searchCondition)

    lazy val sql =
        (List(Some(selectList.sql), Some("from"), Some(tableName), optionalAlias).flatten ++ joins.map(_.sql))
            .mkString(" ")

    override def toString = sql

}

case class Joiner(from: From, tpe: String, tableName: String, alias: String) {

    def on (cond: Cond) = from.copy(joins = from.joins :+ Join(tpe, tableName, alias, cond))
    
}

case class Join(tpe: String, tableName: String, alias: String, cond: Cond) {

    lazy val sql = s"$tpe join $tableName $alias on ${cond.sql}"

}

case class Where(from: From, cond: Cond) extends Query {

    lazy val sql = List(from.sql, "where " + cond.sql).mkString(" ")

    override def toString = sql

}

abstract class Cond {

    def ===(that: Cond) = EqualsOp(this, that)

    def and(that: Cond) = AndOp(this, that)

    def or(that: Cond) = OrOp(this, that)

    def sql: String

}

object SqualaImplicits {

    implicit def queryToSqlExpr(query: Query): SqlExpr = SqlExpr(query.sql)

    implicit def stringToRefExpr(columnName: String): RefExpr = RefExpr(columnName)

    implicit def tuple2ToQualifiedRefExpr(tuple: (String, String)): QualifiedRefExpr = tuple match {
        case (qualifier, columnName) => QualifiedRefExpr(qualifier, columnName)
    }

    implicit def intToIntExpr(value: Int): IntExpr = IntExpr(value)

}

object Squala {

    def select(columnNames: String*) = Select(columnNames)

    def literal(value: String) = StrExpr(value)

    def sql(sql: String) = SqlExpr(sql)

}