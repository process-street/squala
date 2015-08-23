package st.process

import org.scalatest._
import st.process.squala.Squala

class SqualaSpec extends FlatSpec with Matchers {

    "a select * from foo" should "generate corresponding SQL" in {
        Squala.select("*").from("foo").toString should be ("select * from foo")
    }

    "a select x, y, z from foo" should "generate corresponding SQL" in {
        Squala.select("x", "y", "z").from("foo").toString should be ("select x, y, z from foo")
    }

}
