# Squala

A direct SQL DSL in Scala.

## Installation

This will be expanded later.

## Notes

```scala
scala> import st.process.squala.Squala
import st.process.squala.Squala

scala> Squala.select("x", "y", "z").from("foo").toString
res1: String = select x, y, z from foo
```

## Author

| [![twitter/cdmckay](https://gravatar.com/avatar/b181c028e6b51d408450e12ab68bf25c?s=70)](https://twitter.com/cdmckay "Follow @cdmckay on Twitter") |
|---|
| [Cameron McKay](https://cdmckay.org/) |

## License

This library is available under the [MIT](http://opensource.org/licenses/mit-license.php) license.
