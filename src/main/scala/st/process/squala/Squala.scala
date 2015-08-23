package st.process.squala

// Loosely following this BNF: http://savage.net.au/SQL/sql-92.bnf.html

case class QuerySpec(selectList: SelectList, fromClause: FromClause) {

    override def toString = {
        "select " + selectList.columnNames.mkString(", ") + " from " + fromClause.tableName
    }

}

case class SelectList(columnNames: Seq[String]) {

    def from(tableName: String) = QuerySpec(this, FromClause(tableName))

}

case class FromClause(tableName: String)

object Squala {

    def select(columnNames: String*) = SelectList(columnNames)

}