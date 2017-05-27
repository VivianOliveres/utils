# Notes from training [Twitter - Effective Scala](http://twitter.github.io/effectivescala/)

## Variables

```scala
val name = "toto"
var age = 18
```

## Functions

```scala
def addOne(m: Int) = m + 1
```

## Anonymous Functions

```scala
(x: Int) => x + 1

{ i: Int => 
	println("Hellow World")
	i * 2
}
```

## Partial Application
```scala
def adder(m: Int, n: Int) = n + m
val add2 = adder(2, _:Int)
```

## Curried Functions

+ Definition
```scala
def multiply(n: Int)(m: Int) = n * m
```

+ With partial application
```scala
val timesTwo = multiply(2) _
```

+ From multiple parameter to curried
```scala
def adder(m: Int, n: Int) = n + m
val curriedAdd = (adder _).curried
	curriedAdd: Int => (Int => Int) = <function1>
```

## Variable length arguments

```scala
def capitalizeAll(args: String*) = args.map(arg => arg.capitalize)
```

## Classes

```scala
class Calculator {
   val brand: String = "HP"
   def add(m: Int, n: Int): Int = m + n
}
```

## Classe constructor

```scala
class Calculator(brand: String) {
	val color: String = if (brand == "TI") "blue" else if (brand == "HP") "black" else "white"
	def add(m: Int, n: Int): Int = m + n
}
```

## Inheritance

```scala
class ScientificCalculator(brand: String) extends Calculator(brand) {
  def log(m: Double, base: Double) = math.log(m) / math.log(base)
}
```

## Abstract Class

```scala
abstract class Shape {
	def getArea():Int
}
class Circle(r: Int) extends Shape {
	def getArea():Int = { r * r * 3 }
}
```

## Traits

```scala
trait Car {
  val brand: String
}
trait Shiny {
  val shineRefraction: Int
}
class BMW extends Car {
  val brand = "BMW"
}
class BMW extends Car with Shiny {
  val brand = "BMW"
  val shineRefraction = 12
}
```

## Type

+ Type class (i.e generics)

```scala
trait Cache[K, V] {
  def get(key: K): V
  def put(key: K, value: V)
  def delete(key: K)
}
```

+ Type method

```scala
def remove[K](key: K)
```

## Apply

+ Apply method

```scala
object FooMaker {
   def apply() = new Foo
}
val newFoo = FooMaker()
```

## Singleton

```scala
object Timer {
	var count = 0
	def currentCount(): Long = {
		count += 1
		count
	}
}

Timer.currentCount()
```

## Companion Object

```scala
class Bar(foo: String)
object Bar {
  def apply(foo: String) = new Bar(foo)
}
```

## Function

```scala
class AddOne extends Function1[Int, Int] {
  def apply(m: Int): Int = m + 1
}
class AddOne extends (Int => Int) {
  def apply(m: Int): Int = m + 1
}
```

## Pattern Matching

```scala
val times = 1

times match {
  case 1 => "one"
  case 2 => "two"
  case _ => "some other number"
}

times match {
  case i if i == 1 => "one"
  case i if i == 2 => "two"
  case _ => "some other number"
}
```

## Matching on type

```scala
def bigger(o: Any): Any = {
  o match {
    case i: Int if i < 0 => i - 1
    case i: Int => i + 1
    case d: Double if d < 0.0 => d - 0.1
    case d: Double => d + 0.1
    case text: String => text + "s"
  }
}
```

## Matching on class members

```scala
def calcType(calc: Calculator) = calc match {
  case _ if calc.brand == "HP" && calc.model == "20B" => "financial"
  case _ if calc.brand == "HP" && calc.model == "48G" => "scientific"
  case _ if calc.brand == "HP" && calc.model == "30B" => "business"
  case _ => "unknown"
}
```

## Case Classes

+ Declaration

```scala
	case class Calculator(brand: String, model: String)
	val hp20b = Calculator("HP", "20b")
	val hp20B = Calculator("HP", "20b")
	hp20b == hp20B
```

+ `hashcode`, `equals` and `toString` are defined by default

## Case Classes with pattern matching

+ case classes are designed to be used with pattern matching

```scala
def calcType(calc: Calculator) = calc match {
  case Calculator("HP", "20B") => "financial"
  case Calculator("HP", "48G") => "scientific"
  case Calculator("HP", "30B") => "business"
  case Calculator(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
}

case Calculator(_, _) => "Calculator of unknown type"
case _ => "Calculator of unknown type"
case c@Calculator(_, _) => "Calculator: %s of unknown type".format(c)
```

## Exceptions

```scala
try {
  remoteCalculatorService.add(1, 2)
} catch {
  case e: ServerIsDownException => log.error(e, "the remote calculator service is unavailable")
} finally {
  remoteCalculatorService.close()
}
```

## Arrays

```scala
scala> val numbers = Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
numbers: Array[Int] = Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

scala> numbers(3) = 10
```

## List

```scala
scala> val numbers = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
numbers: List[Int] = List(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)

scala> numbers(3) = 10
<console>:9: error: value update is not a member of List[Int]
              numbers(3) = 10
```

## Sets

```scala
scala> val numbers = Set(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
numbers: scala.collection.immutable.Set[Int] = Set(5, 1, 2, 3, 4)
```

## Tuple

+ groups logical collections of items without a class

```scala
scala> val hostPort = ("localhost", 80)
hostPort: (String, Int) = (localhost, 80)
```
+ No named accessor

```scala
scala> hostPort._1
res0: String = localhost
scala> hostPort._2
res1: Int = 80
```

+ Fit with pattern matching

```scala
hostPort match {
  case ("localhost", port) => ...
  case (host, port) => ...
}
```

+ Sugar syntax

```scala
scala> 1 -> 2
res0: (Int, Int) = (1,2)
```

## Maps

```scala
Map(1 -> 2)
Map("foo" -> "bar")
```

## Option

```scala
val result = res1.getOrElse(0) * 2
```

## Functional Combinators

+ map

```scala
val numbers = List(1, 2, 3, 4)
numbers.map((i: Int) => i * 2)
// res0: List[Int] = List(2, 4, 6, 8)
```

+ foreach

++ Returns nothing and is intended to side-effects

```scala
numbers.foreach((i: Int) => i * 2)
```

+ filter

```scala
numbers.filter((i: Int) => i % 2 == 0)
```

+ zip

++ aggregates the contents of two lists into a single list of pairs.

```scala
	List(1, 2, 3).zip(List("a", "b", "c"))
	// res0: List[(Int, String)] = List((1,a), (2,b), (3,c))
```

+ partition

++ splits a list based on a predicate

```scala
	val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
	numbers.partition(_ % 2 == 0)
	// res0: (List[Int], List[Int]) = (List(2, 4, 6, 8, 10),List(1, 3, 5, 7, 9))
```

+ find

++ returns the first element of a collection that matches a predicate function

```scala
numbers.find((i: Int) => i > 5)
// res0: Option[Int] = Some(6)
```

+ drop & dropWhile

++ drop drops the first i elements

```scala
numbers.drop(5)
// res0: List[Int] = List(6, 7, 8, 9, 10)
```

++ dropWhile removes the first elements that match a predicate function

```scala
numbers.dropWhile(_ % 2 != 0)
// res0: List[Int] = List(2, 3, 4, 5, 6, 7, 8, 9, 10)
```

+ foldLeft & foldRight

++ Zero is starting value and m is an accumulator

```scala
numbers.foldLeft(0)((m: Int, n: Int) => m + n)
// res0: Int = 55
```

+ flatten

++ collapses one level of nested structure

```scala
List(List(1, 2), List(3, 4)).flatten
// res0: List[Int] = List(1, 2, 3, 4)
```

+ flatMap

++ Mapping & Flatening

```scala
val nestedNumbers = List(List(1, 2), List(3, 4))
nestedNumbers.flatMap(x => x.map(_ * 2))
// res0: List[Int] = List(2, 4, 6, 8)
```

+ Generalized functional combinators

++ Everything can be done with foldXXX methods. Exemple with map:

```scala
def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
  numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
    fn(x) :: xs // concat
  }
}
```

+ Use pattern matching as filter

```scala
val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200)
extensions.filter({case (name, extension) => extension < 200})
```

# Functions

+ Function Composition

++ compose

```scala
scala> def f(s: String) = "f(" + s + ")"
scala> def g(s: String) = "g(" + s + ")"
scala> val fComposeG = f _ compose g _
	fComposeG: (String) => java.lang.String = <function>
```

++ andThen

```scala
scala> val fAndThenG = f _ andThen g _
fAndThenG: (String) => java.lang.String = <function>
```

+ PartialFunction

++ Functions defined for some values

```scala	
scala> val one: PartialFunction[Int, String] = { case 1 => "one" }
	one: PartialFunction[Int,String] = <function1>

scala> one.isDefinedAt(1)
	res0: Boolean = true

scala> one.isDefinedAt(2)
	res1: Boolean = false
```

++ Can be composed

```scala
scala> val two: PartialFunction[Int, String] = { case 2 => "two" }
scala> val three: PartialFunction[Int, String] = { case 3 => "three" }
scala> val wildcard: PartialFunction[Int, String] = { case _ => "something else" }
scala> val partial = one orElse two orElse three orElse wildcard
	partial: PartialFunction[Int,String] = <function1>
```
