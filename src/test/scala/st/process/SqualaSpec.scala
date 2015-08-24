package st.process

import org.scalatest.Matchers.{not => scalaTestNot, _}
import org.scalatest._
import st.process.squala.Squala._
import st.process.squala.SqualaImplicits._

class SqualaSpec extends FlatSpec {

    "a select * from a table" should "generate correct SQL" in {
        select("*").from("foo").sql should be("select * from foo")
    }

    "a select with a list of columns from a table" should "generate correct SQL" in {
        select("x", "y", "z").from("foo").sql should be("select x, y, z from foo")
    }

    "a select with a list of columns from a table with an alias" should "generate correct SQL" in {
        select("x", "y", "z").from("foo").as("f").sql should be("select x, y, z from foo f")
    }

    "a select * from a table where a field equals a bool literal" should "generate correct SQL" in {
        (select("*") from "foo" where ("bar" === true)).sql should be("select * from foo where (bar = true)")
    }

    "a select * from a table where a field equals an int literal" should "generate correct SQL" in {
        (select("*") from "foo" where ("bar" === 42)).sql should be("select * from foo where (bar = 42)")
    }

    "a select * from a table where a field equals a string literal" should "generate correct SQL" in {
        (select("*") from "foo" where ("bar" === literal("x"))).sql should be("select * from foo where (bar = 'x')")
    }

    "a select * from a table where a qualified field equals an int literal" should "generate correct SQL" in {
        (select("*") from "foo" as "f" where (("f", "bar") === 42)).sql should
            be("select * from foo f where (\"f\".bar = 42)")
    }

    "a select * from a table where two fields equal two int literals" should "generate correct SQL" in {
        (select("*") from "foo" where ("bar" === 42 and "baz" === 44)).sql should
            be("select * from foo where ((bar = 42) and (baz = 44))")
    }

    "a select * from a table where exists a sub-query" should "generate correct SQL" in {
        val q1 = select("*") from "foo"
        val q2 = (select("*") from "bar").where exists q1
        q2.sql should be("select * from bar where exists (select * from foo)")
    }

    "a select * from a table where not exists a sub-query" should "generate correct SQL" in {
        val q1 = select("*") from "foo"
        val q2 = (select("*") from "bar").where.not.exists(q1)
        q2.sql should be("select * from bar where not (exists (select * from foo))")
    }

    "a select * from a table joined with another table" should "generate correct SQL" in {
        val q = select("*") from "foo" as "f" innerJoin("bar", "b") on ("b.id" === "f.bar_id")
        q.sql should be("select * from foo f inner join bar b on (b.id = f.bar_id)")
    }

    "a select * from a table joined with 2 tables" should "generate correct SQL" in {
        val q = select("*").from("foo").as("f")
            .innerJoin("bar", "b").on("b.id" === "f.bar_id")
            .innerJoin("baz", "z").on("z.id" === "b.baz_id")
        q.sql should be("select * from foo f inner join bar b on (b.id = f.bar_id) inner join baz z on (z.id = b.baz_id)")
    }

}
