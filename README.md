# Squala

A SQL DSL in Scala that generates SQL in a predictable way.

Squala is different from other SQL DSLs in that it doesn't try to be type-safe or map Scala objects to SQL tables.
Instead, it tries to offer the same control as embedded SQL but without the messiness and error-proneness of
string concatenation.

It was built with PostgreSQL in mind, but should work with most databases.

Squala is a work in progress, and contributions and ideas are very welcome.

## Installation

This will be expanded later.

## Notes

```scala
scala> import st.process.squala.Squala._
import st.process.squala.Squala._

scala> select("x", "y", "z").from("foo").sql
res1: String = select x, y, z from foo
```

## Author

| [![twitter/cdmckay](https://gravatar.com/avatar/b181c028e6b51d408450e12ab68bf25c?s=70)](https://twitter.com/cdmckay "Follow @cdmckay on Twitter") |
|---|
| [Cameron McKay](https://cdmckay.org/) |

## License

This library is available under the [MIT](http://opensource.org/licenses/mit-license.php) license.
