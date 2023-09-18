package recap

object TypeClasses {

    class Person(val name: String, val age: Int)

    // 1- Define the class
    trait JsonSerializer[T] {
        def toJson(value: T): String
    }


    // 2- Define the instances
    implicit object StringSerializer extends JsonSerializer[String] {
        override def toJson(value: String): String = value
    }

    implicit object IntSerializer extends JsonSerializer[Int] {
        override def toJson(value: Int): String = value.toString
    }

    given JsonSerializer[Person] with {
        override def toJson(person: Person): String = s"""
            {
               name: ${person.name},
               age: ${person.age}
            }
        """.stripMargin
    }

    // 3- Powerful API
    def listToJson[T](value: List[T])(implicit serializer: JsonSerializer[T]): String = {
        value.map(x => serializer.toJson(x)).mkString("[", ",", "]")
    }

    // 4- Syntax sugar
    object JsonSyntax {
        import recap.TypeClasses.JsonSerializer
        implicit class JsonSerializerSyntax[T](value: T)(implicit serializer: JsonSerializer[T]) {
            def toJson: String = serializer.toJson(value)
        }
    }

    def main(args: Array[String]): Unit = {
        import JsonSyntax._

        val jim = Person("jim", 24)
        val bob = Person("bob", 36)
        val alex = Person("alex", 83)

        println(jim.toJson)

        val listOfPersons = List(jim, bob, alex)

        println(listToJson(listOfPersons))
    }
}