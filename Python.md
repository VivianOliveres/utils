# Python

Intro to python from [Bro Code Youtube video](https://www.youtube.com/watch?v=XKHEtdqhLK8)
Python for GUI is skipped in this document.

## String functions

```python
name = "John Snow"

# Size of string: 9
print(len(name)) 

# First index of given str: 1
print(name.find("o"))

# Upper case of first letter: no change here
print(name.capitalize())

# Upper case: JOHN SNOW
print(name.upper())

# Lower case: john snow
print(name.lower())

# is this string a number?
print(name.isDigit()) # False
print("123".isDigit()) # True

# is this string an alphanumeric string?
print(name.isAlpha()) # True
print("123".isAlpha()) # True
print("Coucou!".isAlpha()) # False

# Count occurence
print(name.count("o")) # 2

# Replace occurence
print(name.replace("o", "a")) # Jahn Snaw

# Duplicate string
print(name*3) # John SnowJohn SnowJohn Snow
```

## Type cast

```python
age = 123
ageStr = "123"
print(int(ageStr)) #123
print(float(age)) #123.0
print(str(age)) #"123"
```

## Math  functions

```python
import math

pi = 3.14
print(round(pi)) #3
print(ceil(pi)) #4
print(floor(pi)) #3
print(abs(-123.1)) #123.1
print(pow(pi,2)) #9.8596
print(sqrt(420)) #20.49...
print(max(pi, 5, 12)) #12
print(min(pi, 5, 12)) #3.14

```

## String slicing

Slincing: Create a substring by extracting elements from another string (ie: `indexing[start:stop:step]` or `slice()`)

### Indexing

```python
name = "John Snow"

print(name[0]) #J
print(name[0:4]) #John (inclusive then exclusive)
print(name[:4]) #John (Same as before)

print(name[6:10]) #Snow (inclusive then exclusive)
print(name[6:]) #Snow (Same as before)

print(name[0:10:1]) #John Snow (everything)
print(name[0:10:2]) #Jh nw (Every two characters)
print(name[::2]) #Jh nw (Same as before)

print(name[::-1]) #wonS nhoJ (Reverse string)
```

### Slicing

```python
website = "http://google.com"

# Remove first 7 characters and last 4 characters
slice = slice(7, -4)
print(website[slice]) # google
```

## If-else statements

```python
age = 25
if age == 100:
    print("You are old!")
elif age >= 18:
    print("You are adult")
else:
    print("You are young")
```

## Logical operators

```python
temp = input("What is the temperature?")
if temp > 0 and temp < 30:
    print("Good")
elif temp < 0 or temp > 30:
    print("Bad")
```

## Loops

### While loops

```python
while something != True:
    something = doSothing()
```

### For loops

```python
for i in range(10):
    print(i)
    
for i in range(50, 100): # (inclusive, exclusive)
    print(i)
    
for i in range(50, 100, 2): # (inclusive, exclusive, step)
    print(i)
    
for i in "Bro Code":
    print(i)
```

### Breaking loops

Break, continue, pass

```python
while True:
    name = input("Enter your name:")
    if name != "":
        break

phone_number = "123-456-789"
for i in phone_number:
    if i == "-":
        continue
    print(i, end = "")
    
for i in range(1, 21):
    if i == 13:
        pass
    else:
        print(i, end = "")
```

## Lists

```python
food = ["pizza", "hamburger", "pasta"]
print(food)
print(food[1])

food[0] = "sushi"
print(food[0])

food.append("ice cream")
food.remove("pasta")
food.pop()
food.insert(0, "cake")

food.sort()
food.clear()
```

## Tupples

```python
student = ("John", 21, "male")

student.count("John") #1
student.index("male") #2

for x in student:
    print(x)
    
if "John" in student:
    print("It is find!")
```

## Sets

```python
utenseils = {"fork", "spoon", "knife"}

utensils.add("napkin")
utensils.remove("fork")
utensils.clear()
utensils.update({"bowl", "plate"}) # Add all
utensils.union({"bowl", "plate"}) # Add all
utensils.difference({"bowl", "plate"})
utensils.intersection({"bowl", "plate"})

for x in utenseils: # Not ordered
    print(x)

if "John" in student:
    print("It is find!")
```

## Dictionaries

```python
capitals = {"USA": "Whashington", "France":"Paris", "China":"Beijing"}

capitals["France"]
capitals.get("France") # None
capitals.pop("China") # get and remove
capitals.clear()

capitals.update({"Germany":"Berlin"}) # Add a nex value
capitals.update({"USA":"New York"}) # Update an old value

capitals.keys()
capitals.values()
capitals.items()

for key,value in capitals.items():
    print(key, value)
```

## Functions

```python
# Simple function
def hello(name):
    print("Hello " + name)

# Return function
def multiply(value1, value2):
    return value1 * value2

# Tuple argument (can be converted to list with the `list(args)` method)
def add(*args):
    sum = 0
    for i in args:
        sum += i
    return sum
print(add(1, 2, 3, 4))

# Dictionary argument
def hello(**kwargs):
    print("Hello " + kwargs["first"] + " " + kwargs["last"])
    for key,value in kwargs.items():
        print(value, end = " ")
hello(first="Bro", middle="dude", last="Code")
```

## String format

```python
animal = "cow"
item = "moon"
print("The {} jumped over the {}".format(animal, item))
print("The {0} jumped over the {1}".format(animal, item)) # positional argument
print("The {animal} jumped over the {item}".format(animal="cow", item="moon")) # Keyword argument

number = 1000
print("The number is {:.3f}".format(number)) # 1000.000 
print("The number is {:,}".format(number)) # 1,000 
print("The number is {:b}".format(number)) # Binary: 1111101000 
print("The number is {:o}".format(number)) # Octal: 1750 
print("The number is {:X}".format(number)) # Hexa: 3E8 
```

## Random numbers

```python
import random

x = random.randint(1, 6) 
y = random.random() # 0 or 1

myList = ["rock", "paper", "scissors"]
z = random.choice(myList)

cards = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, "J", "Q", "K", "A"]
w = random.shuffle(cards) 
```

## Exception handling

```python
try:
    numerator = int(input("Number to divide:")) 
    denominator = int(input("Number to divide by:")) 
    result = numerator / denominator
except ZeroDivisionError as e:
    print(e)
    print("Division by zero")
except ValueError as e:
    print(e)
    print("Only number and not strings")
except Exception as e:
    print(e)
    print("Something went wrong")
else:
    # Executed only if no exception are thrown
    print(result)
finally:
    # Always executed
    print("Done!")
```

## File

```python
import os

path = "/home/kensai/some_file.txt"
if os.file.exists(path):
    print("File exists")
if os.file.isfile(path):
    print("That is a file")
elif os.file.isDir(path):
    print("That is a directory")

# Read file
try:
    with open(path) as file:
        print(file.read())
except FileNotFoundError:
    print("File not found")

# Write file (overwrite)
try:
    with open(path, 'w') as file:
        file.write("Some text\nHello!")
except FileNotFoundError:
    print("File not found")
    
# Write file (append)
try:
    with open(path, 'a') as file:
        file.write("Some text at the end")
except FileNotFoundError:
    print("File not found")
```

```python
import shutil

from = "/home/kensai/some_file.txt"
to = "/home/kensai/copy.txt"

# Copy file
shutil.copyfile(from, to) # Copy content file
shutil.copy2(from, to) # Copy also metadata

# Move file
import os
try:
    os.replace(from, to)
except FileNotFoundError:
    print("File not found")
    
# Delete file
import os
import shutil
try:
    if os.file.isFile(path):
        os.remove(path)
    elif:
        os.rmdir(path)
        # shutil.rmtree(path)
except FileNotFoundError:
    print("File not found")
except PermissionError:
    print("Not enough permissions")
```

## Modules

```python
import custom_module
custom_module.hello()

# Import with nickname
import custom_module as cm
cm.hello()

# Import function
from custom_module import hello
hello()

# Import all functions
from custom_module import *
hello()
bye()
```

## Object Oriented Programming

```python
class Car:
    
    # Class variable
    wheels = 4
    
    def __init__(self, make, model, year, color):
        # Instance variables from constructor
        self.make = make
        self.model = model
        self.year = year
        self.color = color
    
    def drive(self):
        print(self.model + " is driving")    
    def stop(self):
        print("Car is stoped")
        
###

car_1 = Car("Chevy", "Corvette", 2021, "blue")
print(car_1)
car_1.drive()
car_1.stop()

car_2 = Car("Ford", "Mustang", 2022, "red")

# Update/Read class variable
Car.wheels = 2
print(Car.wheels)
```

## Inheritance

```python
class Animal:
    alive = True
    
    def eat(self):
        print("This animal is eating")
    def sleep(self):
        print("This animal is sleeping")
        
class Rabbit(Animal):
    def run(self):
        print("Rabbit is running")
        
class Fish(Animal):
    def swim(self):
        print("Fish is swiming")
    
class Hawk(Animal):
    def fly(self):
        print("Hawk is flying")
```

## Multiple inheritance

```python
class Prey:
    def flee(self):
        print("This animal flees")
    
class Predator:
    def hunt(self):
        print("This animal is hunting")
        
class Rabbit(Prey):
    pass
        
class Fish(Prey, Predator):
    pass
    
class Hawk(Predator):
    pass
```

## Method overwriting

```python
class Animal:
    def eat(self):
        print("This animal is eating")

class Rabbit(Animal):
    def eat(self):
        print("Rabbit is running")  
```

## Method chaining

```python
car = Car("Chevy", "Corvette", 2021, "blue")
car.turn_on() \
    .drive() \
    .brake() \
    .turn_off()
```

## Super function

```python
class Rectangle:
    def __init__(self, length, width):
        self.length = length
        self.width = width
    def area():
        return length * width

class Square(Rectangle):
    def __init__(self, size):
        super().__init__(size, size)

class Cube(Rectangle):
    def __init__(self, size, heigth):
        super().__init__(size)
        self.heigth = heigth
    def volume():
        super().area() * heigth
```

## Abstract class

```python
# abc = abstract
from abc import ABC, abstractmethod

# Inherit from ABC (ie abstract class)
class Vehicule(ABC):
    @abstractmethod
    def go(self):
        pass

class Car(Vehicule):
    def go(self):
        print("Go Car")

class Motorcycle(Vehicule):
    def go(self):
        print("Go Motorcycle")

```

## Walrus operator

From python 3.8
Assign a value to a variable and return this value.

```python
happy = True
print(happy) # True
print(happy := False) # False
```

## Functions as variables

```python
say = print
say("Hello")
```

## Higher order function

```python
# First, as function that accepts functions
def loud(text):
    return text.upper()
def quiet(text):
    return text.lower()
def hello(func):
    text = func("hello")
    print(text)
    
hello(loud)
hello(quiet)

# Then, as function that returns function
def divisor(x):
    def divided(y):
        y / x
    return divided

divide = divisor(2)
print(divide(10))
```

## Lambda

`lambda parameters:expression`

```python
double = lambda x: x * 2
print(double(5))

multiply = lambda x, y: x * y
print(multiply(5,6))

full_name = lambda first_name, last_name: first_name + " " + last_name

age_check = lambda age: True if age >= 18 else False
```

## Sort

```python
students = ["John", "Steve", "Anna"]

students.sort()
students.sort(reverse = True)

sorted_students = sorted(students)
sorted_students = sorted(students, reverse = True)
```

```python
students = [("John", 20), ("Steve", 19), ("Anna", 18)]
age = lambda nameAndAges:nameAndAges[1]

students.sort(key = age)
students.sort(key = age, reverse = True)

sorted_students = sorted(students, key = age)
sorted_students = sorted(students, key = age, reverse = True)
```

## Map

```python
store = [ ("shirt", 20.0), ("pants", 25.00), ("jacket", 100.0)]
to_euros = lambda data: (data[0], data[1] * 0.82)
store_euros = list(map(to_euros, store))
```

## Filter

```python
store = [ ("shirt", 20.0), ("pants", 25.00), ("jacket", 100.0)]
expensive = lambda data: data[1] > 50
expensive_store = list(filter(expensive, store))
```

## Reduce

```python
import functools
letters = ["H", "E", "L", "L", "O"]
word = functools.reduce(lambda x, y: x + y, letters)
```

## List comprehension

```python
squares = [ i * i for i in range(1, 11)]
# [1, 4, 9, 16, 25, 36, 49, 64, 81, 100]

students_grades = [("John", 5), ("Steve", 19), ("Anna", 12)]
passed_students = [data for data in students_grades if data[1] >= 12]
# [('Steve', 19), ('Anna', 12)]
```

## Dictionary comprehension

dictionary = {key: expression for (key, value) in iterable}
dictionary = {key: expression for (key, value) in iterable if conditional}
dictionary = {key: (if/else) expression for (key, value) in iterable}
dictionary = {key: function(value) for (key, value) in iterable}

```python
cities_in_F = {"New York": 32, "Boston": 75, "Los Angeles": 100, "Chicago": 50}
cities_in_C = {key: (value-32)*(5/9) for (key, value) in cities_in_F.items()}

weather = {"New York": "snowing", "Boston": "sunny", "Los Angeles": "sunny", "Chicago": "cloudy"}
sunny_weather = {key: value for (key, value) in weather.items() if value == "sunny"}

desc_cities = {key: ("warm" if value>40 else "cold") for (key, value) in cities_in_F.items()}

check_temp = lambda value: "warm" if value>40 else "cold"
desc_cities = {key: check_temp(value) for (key, value) in cities_in_F.items()}
```

## Zip function

Aggregate two or more iterables

```python
user_names = ["John", "Bob", "Lucy]
passwords = ["123", "abc", "lol"]
users = zip(user_names, passwords)

login_dates = ["1/1/1970", "3/3/2000", "7/7/2022"]
users2 = zip(user_names, passwords, login_dates)
```

## Main function

`__name__` is a special variable available for every module (ie: python file). 
It is set to `__main__` if this module is the initial launched. 

```python
def main():
    print("hello")
    
if __name__ = "__main__":
    main()
```

## Time module

```python
import time

print(time.ctime(0))
# Epoch date reference (ie: Thu Jan  1 01:00:00 1970)


print(time.ctime(1000))
# 1000 ms past epoch time (ie: Thu Jan  1 01:16:40 1970)

print(time.time())
# Current time since epoch

print(time.localtime())
# time.struct_time(tm_year=2023, tm_mon=9, tm_mday=27, tm_hour=15, tm_min=26, tm_sec=32, tm_wday=2, tm_yday=270, tm_isdst=1)

localTime = time.localtime()
format = "%Y %b %d %H:%m:%S"
print(time.strftime(format, localTime))
# 2023 Sep 27 15:09:39

time_str = "20 April"
time_object = time.strptime(time_str, "%d %B")

# time tuple: (year, month, day, hours, minutes, secs, #day of the week, #day of the year, dst)
tuple = (2020, 4, 4, 20, 0, 0, 0, 0, 0)
time_object = time.mktimetime(tuple)
# Number of seconds since epoch
```

## Threads

No real multithreading as at each moment, only thread is executed. (It turns between active/unterminated threads).

```python
import threading
import time

# Count active threads in this program
threading.active_count()

# List active threads
threading.enumerate()

def eat():
    time.sleep(3)
    print("Eat done")

# Start thread
eat_1 = threading.Thread(target = eat, args = ())
eat_1.start()

# Wait thread is done
eat_1.join()
```

## Daemon Threads

Execute in background but main can terminate when daemons are not terminated.

```python
import threading
import time

def timer():
    count = 0
    while True:
        sleep(1)
        count += 1
        print("Connected since " + count + " seconds")
        
daemon = threading.thread(target = timer, args = (), daemon = True=
daemon.start()
```

## Multiprocessing

Create multiple process

```python
from multiprocessing import Process, cpu_count
import time

def count(num):
    count = 0
    while count < num:
        count += 1

def main():
    print(cpu_count()) # Number of cpus on this computer (ie max number of process to launch in parralell, else they sleep)
     
    a = Process(target = count, args = (1000,)) # Comma in args/tuple is necessary
    a.start()
    a.join()
    print("Finished in " + time.perf_counter() + " seconds") 

if __name__ == "__main__":
    main()
```

## Pip

Package manager from [pypi](http://pypi.org)

Default commands:
 - Help: `pip`
 - Installed version: `pip --version`
 - List packages: `pip list`
 - Install a package: `pip install package_name`

```python

```

