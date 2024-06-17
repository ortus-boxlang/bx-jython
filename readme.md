# ⚡︎ BoxLang Jython Support

```
|:------------------------------------------------------:|
| ⚡︎ B o x L a n g ⚡︎
| Dynamic : Modular : Productive
|:------------------------------------------------------:|
```

<blockquote>
	Copyright Since 2023 by Ortus Solutions, Corp
	<br>
	<a href="https://www.boxlang.io">www.boxlang.io</a> |
	<a href="https://www.ortussolutions.com">www.ortussolutions.com</a>
</blockquote>

<p>&nbsp;</p>

This module allows you to execute python in BoxLang via Jython.  This is a very powerful feature that allows you to execute python code in your BoxLang scripts.
It also allows you to create python modules and classes that can be used in your BoxLang scripts by using the `jythonEvalFile( path )` function.

## Usage

You can execute python code in your BoxLang scripts by using the `jythonEval( code )` function.  Here is an example:

```js
jythonEval( "print('Hello World')" );
```

You can also evaluate python code from a file by using the `jythonEvalFile( path )` function.  Here is an example:

```js
jythonEvalFile( "path/to/file.py" );
```

You can also create python modules and classes that can be used in your BoxLang scripts.  Here is an example:

```python
# mymodule.py
def hello():
	return "Hello World"
```

```js
jythonEvalFile( "path/to/mymodule.py" );
var result = jythonEval( "hello()" );
print( result );
```

You can also use the `<bx:jython>{python code}</bx:jython>` tag to execute python code in your BoxLang scripts.  Here is an example:

```js
<bx:jython variable="result">
print("Hello, World!")

# Addition
num1 = 5
num2 = 10
sum = num1 + num2
print("Sum:", sum)

# Subtraction
num3 = 8
num4 = 3
difference = num3 - num4
print("Difference:", difference)

# Multiplication
num5 = 4
num6 = 6
product = num5 * num6
print("Product:", product)

# Division
num7 = 15
num8 = 3
quotient = num7 / num8
print("Quotient:", quotient)

# Modulo
num9 = 17
num10 = 5
remainder = num9 % num10
print("Remainder:", remainder)

# Exponentiation
base = 2
exponent = 3
result = base ** exponent
print("Result:", result)
</bx:jython>
```

## Bindings

We will automatically bind all the variables in your `variables` scope into the engine bindings via JSR223.  You can then use them as native variables in your python code.

```js
variables.name = "Luis Majano";
jythonEval( "print(name)" );
```

## Result

Each execution produces a result which is a structure with the following keys:

- `engine` : A reference to the engine
- `globalScope` : A reference to the global scope of JSR223
- `engineScope` : A reference to the engine scope of JSR223


## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com).  Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more.  If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

 > "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
