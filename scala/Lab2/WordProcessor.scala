import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.{FileNotFoundException, IOException}

object WordProcessor {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println("Ошибка: Необходимо указать путь к файлу и букву для фильтрации")
      System.exit(1)
    }

    val fileName = args(0)
    val filterChar = args(1)

    if (filterChar.length != 1 || !filterChar.head.isLetter) {
      System.err.println("Ошибка: Второй параметр должен быть одной буквой")
      System.exit(1)
    }

    val result = processFile(fileName, filterChar.head)
    
    result match {
      case Success(words) =>
        words.sorted.foreach(println)
      case Failure(ex) =>
        System.err.println(s"Ошибка обработки файла: ${ex.getMessage}")
        System.exit(1)
    }
  }

  private def processFile(fileName: String, char: Char): Try[List[String]] = Try {
    val source = Source.fromFile(fileName)
    try {
      source.getLines()
        .flatMap(_.split("\\s+"))
        .filter(_.nonEmpty)
        .filter(_.head == char)
        .toList
    } finally {
      source.close()
    }
  }
}
