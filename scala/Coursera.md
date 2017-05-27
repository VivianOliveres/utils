# Notes from training [Functional Programming Principles in Scala](https://fr.coursera.org/learn/progfun1)

## Declarations
```scala
def square(x: Double) = x * x
def square(x: Int) = x * x

def value: Int = 123
```scala

## By name
```scala
def constOne(x: Int, y: => Int) = 1 // y is called by name (no evaluation until needed)

def and(x: Boolean, y: => Boolean) = if (x) y else false // With "by name" then it is a "&&"" function
```

## Recusrion
Tail recursion: iterative process
Tail-calls: recursive call

```scala
@tailrec
def gcd(a: int, b: Int): Int = ...

(x: Int, y: Int) => x + y

def sum(f: Int => Int)(Int, Int) => Int = {
  def sumF(a: Int, b: Int): Int = 
    if (a > b) 0
    else f(a) + sumF(a + 1, b)

  sumF
}
```

## Currying
```scala
def sum(f: Int => Int)(a: Int, b: Int): Int = 
	if (a > b) 0 else f(a) + sum(f)(a + 1, b)
```

## Types
Int, Double, Byte, Short, Char, Long, Float

## Call by value/name
call-by-value (x: Int)
call-by-name (y: => Double)

## Infix notation
```scala
	r add s
	r.add(s)
```

## Singleton and class
```scala
object Empty extends IntSet // singleton
class Empty extends Intset // class object
```

## Imports
```scala
import week3.Rational
import week3._
import week3.{Rational, Hello}
```

## Trait
```scala
class Square extends Shape with Planar with Movable
```

## Top Types
+ *Any* : Base of all
+ *AnyRef* : Base of references (alias of java.lang.Object)
+ *AnyVal* : Base of primitives

## Type Bounds
```scala
def assertAllPos[S <: IntSet](r: S) : S = __
S<:T   			// S is a subtype of T
S>:T 			// S is a supertype of T
[S >: T <: U]	// S is between T and U
```

## Variance Type
```scala
class C[+A] {}	// C is covariant 		(i.e. A<:B => C[A]<:C[B])
class C[-A] {}	// C is contravariant 	(i.e. A<:B => C[A]>:C[B])
class C[A] {}	// C is nonvariant 		(i.e. A<:B => )
```

## Pattern Matching
```scala
case class Number(n: Int) extends Expr
e match {
	case pattern_1 => expr_1
	...
	case pattern_N => expr_N
}
```
Patterns are constructors, variables, wildcard, constants

## List
+ length
+ last
+ init 			-> all elements except the last one
+ take(n)		-> a list of the first n elements
+ drop(n)		-> the list after removing the first n elements

+ xs ++ ys		-> concatenation of 2 lists
+ reverse
+ updated(n, x)	-> replace element at inedx n by the element x
+ indexOf(x)
+ contains(x)

+ reduceLeft((x, y) => x * y) 	=> Reduce a list by first elements
+ foldLeft(op)((x, y) => x * y)	=> Reduce a list with the op first element
+ reduceRight & reduceRight 		=> Same but accumulation starts with last elements of list

+ flatten							=> List(List(T)) -> List(T)

## Sort
+ Order
+ Ordering.Int & Ordering.Stringt

## Implicit parameter
+ keyword: implicit
+ where: in arguments of a method/function
+ will use the "compagnion" instance associated to the T parameterized type

## Syntaxic sugar
+ (x, y) => x * y
+ replaced by
+ (* _ *)

## Ranges
```scala
val r: Range = 1 until 5		// 1, 2, 3, 4
val r: Range = 1 to 5 			// 1, 2, 3, 4, 5
1 to 10 by 3					// 1, 4, 7, 10
```

## For-expression
```scala
for (p <- persons if p.age > 20) yield p.name
persons filter (p => p.age > 20) map (p => p.name)
```

## Stream (i.e List)
```scala
Stream.empty()
Stream.cos(element, stream)
(1 to 100).toStream
x #:: xs == Stream.cons(x, xs)
def cons[T](hd: T, tl: => Stream[T]) {...} means tl will not be evaluated until it is called
```

## Lazy
```scala
lazy val x = expr
lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
```
