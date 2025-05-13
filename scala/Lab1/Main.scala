import scala.annotation.tailrec
import scala.util.{Try, Success, Failure}

object Main extends App {
  if (args.length != 4) {
    println("Использование: program Xнач Xкон dx e")
    sys.exit(1)
  }

  private case class Params(
    xStart: Double,
    xEnd: Double,
    dx: Double,
    epsilon: Double
  )

  private val paramsTry = Try {
    Params(
      args(0).toDouble,
      args(1).toDouble,
      args(2).toDouble,
      args(3).toDouble
    )
  }

  paramsTry match {
    case Failure(_) =>
      println("Ошибка: все параметры должны быть числами с плавающей точкой")
      sys.exit(1)

    case Success(params) =>
      validateParams(params) match {
        case Left(errors) =>
          println("Ошибки валидации:\n" + errors.mkString("\n"))
          sys.exit(1)

        case Right(_) =>
          val xs = generateXValues(params.xStart, params.xEnd, params.dx)
          printResults(xs, params.epsilon)
      }
  }

  private def validateParams(p: Params): Either[List[String], Unit] = {
    val errors = List.newBuilder[String]

    if (math.abs(p.xStart) <= 1) errors += "|Xнач| должен быть > 1"
    if (math.abs(p.xEnd) <= 1) errors += "|Xкон| должен быть > 1"
    if (p.epsilon <= 0) errors += "Точность e должна быть > 0"
    
    if (p.dx == 0) {
      errors += "Шаг dx не может быть нулевым"
    } else {
      val expectedDirection = math.signum(p.xEnd - p.xStart)
      if (math.signum(p.dx) != expectedDirection && expectedDirection != 0) {
        errors += "Шаг dx имеет неверное направление"
      }
    }

    val errorList = errors.result()
    if (errorList.isEmpty) Right(()) else Left(errorList)
  }

  // Исправлено: LazyList -> Stream
  private def generateXValues(start: Double, end: Double, step: Double): Stream[Double] = {
    Stream.iterate(start)(_ + step).takeWhile { x =>
      (step > 0 && x <= end) || (step < 0 && x >= end)
    }
  }

  private def calculateArth(x: Double, epsilon: Double): (Double, Int) = {
    @tailrec
    def loop(n: Int, sum: Double): (Double, Int) = {
      val exponent = 2 * n + 1
      val term = 1.0 / (exponent * math.pow(x, exponent))
      val newSum = sum + term
      if (math.abs(term) < epsilon) (newSum, n + 1)
      else loop(n + 1, newSum)
    }
    loop(0, 0.0)
  }

  // Исправлено: LazyList -> Stream
  private def printResults(xs: Stream[Double], epsilon: Double): Unit = {
    val header = "|     x     |   arth(x)   | Итерации |"
    val separator = "-" * header.length
    
    println("\n" + header)
    println(separator)

    xs.foreach { x =>
      val (arth, iterations) = calculateArth(x, epsilon)
      println(f"| ${x}%9.4f | ${arth}%10.6f | ${iterations}%8d |")
    }
  }
}
