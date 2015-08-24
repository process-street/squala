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

Squala is easiest to use if you import its implicits and the functions from the Squala object.

```scala
scala> import st.process.squala.SqualaImplicits._
import st.process.squala.SqualaImplicits._

scala> import st.process.squala.Squala._
import st.process.squala.Squala._

scala> (select ("*") from "foo").sql
res0: String = select * from foo
```

Squala can do more than just select from a table. Among other features, it supports table aliases, literals, and where
clauses.

```scala
scala> (select ("*") from "foo" as "f" where ("f.bar" === 42)).sql
res1: String = select * from foo f where (f.bar = 42)

scala> (select ("*") from "users" as "u" where ("u.login_count" === 42 and "u.email_verified" === false)).sql
res2: String = select * from users u where ((u.login_count = 42) and (u.email_verified = 0))
```

Squala also supports joins and sub-queries:

```scala
scala> (select ("*") from "users" as "u" innerJoin ("identities", "i") on ("i.id" === "u.identity_id")).sql
res3: String = select * from users u inner join identities i on (i.id = u.identity_id)

scala> val q1 = select ("*") from "foo"
res4: st.process.squala.From = select * from foo

scala> ((select ("*") from "bar").where exists q1).sql
res5: String = select * from bar where exists (select * from foo)
```

## Author

| [![twitter/cdmckay](https://gravatar.com/avatar/b181c028e6b51d408450e12ab68bf25c?s=70)](https://twitter.com/cdmckay "Follow @cdmckay on Twitter") |
|---|
| [Cameron McKay](https://cdmckay.org/) |

## License

This library is available under the [MIT](http://opensource.org/licenses/mit-license.php) license.
