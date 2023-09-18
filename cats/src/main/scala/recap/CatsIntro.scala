package recap

object CatsIntro {

    // Eq: Check at compile time if the types are identical
//    val aComparison = 2 == "a string"

    // part 1 - type class import
    import cats.Eq

    // part 2 - import Type Class Instances for the types you need
    import cats.instances.int._

    // part 3 - use the TC API
    val aTypeSafeComparison = Eq[Int].eqv(2, 3)

    // part 4 - use extension methods (if applicable)
    import cats.syntax.eq._

    val anotherTypeSafeComp = 2 === 3
    val yetAnotherOne = 2 =!= 3

    // part 5 - extending the TC to custom composite types

    val aListComparison = List(2) == List("2")

    case class ToyCar(model: String, price: Double)

    given toyCarEqInstance: Eq[ToyCar] = Eq.instance[ToyCar]({
        (car1: ToyCar, car2: ToyCar) => {
            car1.model == car2.model
        }
    })

    val result = ToyCar("Ferrari", 29.99) === ToyCar("Ferrari", 35.99)

    def main(args: Array[String]): Unit = {
        println(aListComparison)
        println(result)
    }
}
