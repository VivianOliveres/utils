# Notes from training [DanScala](https://github.com/mchataigner/dan-scala)

# Type

```scala
type FunctorType = (LocalDate, HolidayCalendar, Int, Boolean) => LocalDate
```

# Case classes

+ hashcode, equals, toString
+ copy
```scala
case class MyDog(name: String, breed: String)
val d1 = MyDog("Scooby", "Doberman")
val d2 = d1.copy(name = "Scooby Doo") // copy the value with a new name but same breed
```
+ Named parameters
```scala
case class Person(firstname: String, lastname: String, age: Int = 0, phone: String = "")
val p3 = Person(lastname = "Professor", firstname = "Moriarty", phone = "01-XX-XX-XX-XX")
val p4 = p3.copy(age = 23) // copy using named parameters
```

# For loops

+ Range
```scala
val someNumbers = Range(0, 10)
val someNumbers = 0 until 10
val someNumbers = 0 to 9
```
+ Simple loop
```scala
val someNumbers = 0 until 10
var sum = 0
for (i <- someNumbers) {
  if (i % 2 == 0) {
    sum += i
  }
}
```
+ For->yield
```scala
val aList =
for {
    i <- someNumbers
    if (i % 2) == 0
}
yield i
```
+ Multiple for/yield
```scala
val xValues = 1 until 5
val yValues = 1 until 3
val coordinates = for {
  x <- xValues
  y <- yValues
}
yield (x, y)
// ie: foreach X { foreach Y {publish (x, y)}} => 8 cases
```

# Lists

```scala
val a = List(1, 3, 5, 7, 9)
a(2) should equal(5)
a.head should equal(1)
a.tail should equal(List(3, 5, 7, 9))
a.length should equal(5)
a.reverse should equal(List(9, 7, 5, 3, 1))
a.map {_ * 3} should equal(List(3, 9, 15, 21, 27))
a.filter {_ % 3 == 0} should equal(List(3, 9))
c.filterNot(_ % 5==0) should equal(List(1, 2, 8, 9))
```

# Maps

```scala
val myMap = Map("PA" -> "Paris", "BE" -> "Besançon", "BL" -> "Belfort")
myMap.head should be("PA" -> "Paris")
myMap("PA") should be("Paris")
val aNewMap = myMap + ("BL" -> "Belfort")
val anotherMap = myMap - "PA"
val aNewOtherMap = myMap -- List("BE", "BL")
```

# Sets

```scala
val mySet = Set("Sud", "Est", "Sud")
val aNewSet = mySet + "Nord"
val aNewSetBis = aNewSet - "Nord"
val aNewOtherSet = aNewSet -- List("Ouest", "Nord")
```

# Option

+ None
```scala
val optional: Option[String] = None
optional.isEmpty should be(true)
optional should be(None)
```
+ Some
```scala
val optional: Option[String] = Some("Some Value")
(optional == None) should be(false)
optional.isEmpty should be(false)
```
+ getOrElse
```scala
val optional: Option[String] = None
optional.getOrElse("No Value") should be("No Value")
```

# Higher order functions

+ functions that can take functions as parameters and/or return functions.
+ can return functions
```scala
def add(x: Int) = (y: Int) => x + y
```
+ can take functions as parameters
+ Currying is a technique transforming a function with multiple parameters into a function with one parameter
```scala
def multiply(x: Int, y: Int) = x * y
val multiplyCurried = (multiply _).curried // '_' compiler should not apply the function but referencing it
multiply(4, 5) should be(20)
multiplyCurried(4)(5) should be(20)
```
```scala
// Other exemple
def customFilter(f: Int => Boolean)(xs: List[Int]) = xs filter f
def onlyEven(x: Int) = x % 2 == 0
val xs = List(12, 11, 5, 20, 3, 13, 2)
customFilter(onlyEven)(xs) should be(List(12, 20, 2))

val onlyEvenFilter = customFilter(onlyEven) _
onlyEvenFilter(xs) should be(List(12, 20, 2))
```

# Extractor

+ An extractor is the opposite of a constructor
+ A companion object is as a singleton with the same name as the class and can be considered as a toolbox holding the static code of a class
```scala
class Email(val value:String)
object Email { def unapply(email:Email): Option[String] = Option(email.value) }
val mailstring = "foo@bar.com"
val email = new Email(mailstring)
val Email(extractedString) = email
(extractedString == mailstring) should be(true)
```
+ case class automatically defines one extractor for this case class

# Pattern Matching

+ Generalisation of switch case blocks
+ e match {case p1 => e1 ... case pn => en }
+ *match* is an expression, thus it always returns a value
+ If no pattern matching the input a MatchError is raised
```scala
val actual = "B" match {
  case "A" => "stringA"
  case "B" => "stringB"
  case "C" => "stringC"
  case _ => "DEFAULT"
}
```

