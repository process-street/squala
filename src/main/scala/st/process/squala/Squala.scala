package st.process.squala

import st.process.squala.expressions._
import st.process.squala.operations.{OrOp, EqualsOp, AndOp}

// Loosely following this BNF: http://savage.net.au/SQL/sql-92.bnf.html

trait Sqlable {

    def sql: String

}

case class Select(columnNames: Seq[String]) extends Sqlable {

    def from(tableName: String) = From(Select(columnNames), tableName, optionalAlias = None)

    lazy val sql = "select " + columnNames.mkString(", ")

    override def toString = sql

}

case class From(selectList: Select, tableName: String, optionalAlias: Option[String]) extends Sqlable {

    def as(alias: String) = copy(optionalAlias = Some(alias))
    
    def where(searchCondition: SearchCond) = Where(this, searchCondition)

    lazy val sql = List(Some(selectList.sql), Some("from"), Some(tableName), optionalAlias)
        .flatten.mkString(" ")

    override def toString = sql

}

case class Where(from: From, searchCondition: SearchCond) extends Sqlable {

    lazy val sql = List(from.sql, "where " + searchCondition.sql).mkString(" ")

    override def toString = sql

}

abstract class SearchCond extends Sqlable {

    def ===(that: SearchCond) = EqualsOp(this, that)

    def and(that: SearchCond) = AndOp(this, that)

    def or(that: SearchCond) = OrOp(this, that)

}

object SqualaImplicits {

    implicit def sqlableToSqlExpr(sqlable: Sqlable): SqlExpr = SqlExpr(sqlable.sql)
    implicit def stringToRefExpr(columnName: String): RefExpr = RefExpr(columnName)
    implicit def intToIntExpr(value: Int): IntExpr = IntExpr(value)

}

object Squala {

    def select(columnNames: String*) = Select(columnNames)

    def literal(value: String) = StrExpr(value)
    def sql(sql: String) = SqlExpr(sql)


}