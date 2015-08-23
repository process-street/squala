package st.process

import org.scalatest._
import st.process.squala.Squala._
import st.process.squala.SqualaImplicits._

class SqualaSpec extends FlatSpec with Matchers {

    "a select * from a table" should "generate correct SQL" in {
        select("*").from("foo").sql should be ("select * from foo")
    }

    "a select with a list of columns from a table" should "generate correct SQL" in {
        select("x", "y", "z").from("foo").sql should be ("select x, y, z from foo")
    }

    "a select with a list of columns from a table with an alias" should "generate correct SQL" in {
        select("x", "y", "z").from("foo").as("f").sql should be ("select x, y, z from foo f")
    }

    "a select * from a table where a field equals an int literal" should "generate correct SQL" in {
        (select ("*") from "foo" where("bar" === 42)).sql should be ("select * from foo where (bar = 42)")
    }

    "a select * from a table where a field equals a string literal" should "generate correct SQL" in {
        (select ("*") from "foo" where("bar" === literal("x"))).sql should be ("select * from foo where (bar = 'x')")
    }

    "a select * from a table where two fields equal two int literals" should "generate correct SQL" in {
        (select ("*") from "foo" where("bar" === 42 and "baz" === 44)).sql should
            be ("select * from foo where ((bar = 42) and (baz = 44))")
    }

}
