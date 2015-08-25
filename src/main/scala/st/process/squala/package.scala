package st.process

package object squala {

    def singleQuote(value: String) = "'%s'".format(value.replaceAllLiterally("'", "''"))

    def doubleQuote(value: String) = "\"%s\"".format(value.replaceAllLiterally("\"", "\"\""))

}
