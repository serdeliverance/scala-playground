package part2effects

object Effects:
    // pure functional programming
    // substitution
    def combine(a: Int, b: Int): Int = a + b

    val five = combine(2, 3)
    val five_v2 = 2 + 3
    val five_v3 = 5

    // referential transparency = can replace an expression with its value
    // without changing the meaning of the program

    // example: printing to the console
    val printing: Unit = println("Hello, Cats Effect!")

    val printing_v2: Unit = ()

    // if we have a program that prints to the console and we want to replace any
    // occurrence of print by the value it evaluates to (Unit in this case)
    // we can see that the meaning of the program is not the same,
    // So it is not referential transparent

    // another example: changing variable
    var anInt = 0
    val changingVar: Unit = (anInt += 1)
    val changingVar_v2: Unit = ()   // not the same meaning

    // side effects are inevitable in almost any program

    /*
    Effect type:

    Properties:
        - type signature describes the kind of calculation that will be performed
        - type signature describes the VALUE that will be calculated
        - when side effects are needed, effect construction is separated from effect execution
    */

    /*
    example: Option is an Effect Type
    - describes a possibly absence of value
    - computes a value of type A, if it exists
    - side effects are not needed
    */
    val anOption: Option[Int] = Option(42)