package st.process

import org.scalatest._
import st.process.squala.Squala._

class SqualaSpec extends FlatSpec with Matchers {

    "a select * from foo" should "generate corresponding SQL" in {
        select("*").from("foo").toString should
            be ("select * from foo")
    }

    "a select x, y, z from foo" should "generate corresponding SQL" in {
        select("x", "y", "z").from("foo").toString should
            be ("select x, y, z from foo")
    }

    "a select x, y, z from foo f" should "generate corresponding SQL" in {
        select("x", "y", "z").from("foo").as("f").toString should
            be ("select x, y, z from foo f")
    }

    "a select * from foo where bar = 42" should "generate corresponding SQL" in {
        (select ("*") from "foo" where((Symbol("x.y.z") === "bar") and ('baz === 44))).toString should
            be ("select * from foo where ((x.y.z = 'bar') and (baz = 44))")
    }

}
