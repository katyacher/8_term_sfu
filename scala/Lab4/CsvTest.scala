import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Try, Success, Failure, Either, Left, Right}

// Трейты для сериализации/десериализации
trait CsvEncoder[T] {
  def encode(entity: T): String
}

trait CsvDecoder[T] {
  def decode(csv: String): Either[String, T]
}

// Объекты-компаньоны с инстансами type classes
object CsvCodecs {
  // Масть
  implicit object SuitCsvCodec extends CsvEncoder[Suit] with CsvDecoder[Suit] {
    def encode(suit: Suit): String = suit match {
      case Clubs => "Clubs"
      case Diamonds => "Diamonds"
      case Hearts => "Hearts"
      case Spades => "Spades"
    }
    
    def decode(csv: String): Either[String, Suit] = csv match {
      case "Clubs" => Right(Clubs)
      case "Diamonds" => Right(Diamonds)
      case "Hearts" => Right(Hearts)
      case "Spades" => Right(Spades)
      case _ => Left(s"Unknown suit: $csv")
    }
  }

  // Карта
  implicit object CardCsvCodec extends CsvEncoder[Card] with CsvDecoder[Card] {
    def encode(card: Card): String = card match {
      case MinorCard(v, s) => s"Minor,$v,${implicitly[CsvEncoder[Suit]].encode(s)}"
      case Jack(s) => s"Picture,Jack,${implicitly[CsvEncoder[Suit]].encode(s)}"
      case Queen(s) => s"Picture,Queen,${implicitly[CsvEncoder[Suit]].encode(s)}"
      case King(s) => s"Picture,King,${implicitly[CsvEncoder[Suit]].encode(s)}"
      case Ace(s) => s"Picture,Ace,${implicitly[CsvEncoder[Suit]].encode(s)}"
    }
    
    def decode(csv: String): Either[String, Card] = {
      val parts = csv.split(",")
      if(parts.length < 3) return Left("Invalid CSV format")
      
      implicitly[CsvDecoder[Suit]].decode(parts(2)).flatMap { suit =>
        parts(0) match {
          case "Minor" => 
            Try(parts(1).toInt).toEither.left.map(_.getMessage)
              .flatMap(v => if(v >= 2 && v <= 10) Right(MinorCard(v, suit)) 
                          else Left("Invalid minor card value"))
          case "Picture" => parts(1) match {
            case "Jack" => Right(Jack(suit))
            case "Queen" => Right(Queen(suit))
            case "King" => Right(King(suit))
            case "Ace" => Right(Ace(suit))
            case _ => Left(s"Unknown picture card: ${parts(1)}")
          }
          case _ => Left(s"Unknown card type: ${parts(0)}")
        }
      }
    }
  }
}

// Расширение для работы с файлами
object CsvFileUtils {
  def writeToCsv[T: CsvEncoder](filename: String, data: List[T]): Try[Unit] = Try {
    val writer = new PrintWriter(new File(filename))
    try {
      data.foreach(e => writer.println(implicitly[CsvEncoder[T]].encode(e)))
    } finally {
      writer.close()
    }
  }

  def readFromCsv[T: CsvDecoder](filename: String): Either[String, List[T]] = {
    Try(Source.fromFile(filename)).toEither.left.map(_.getMessage).flatMap { source =>
      try {
        val lines = source.getLines().toList
        lines.zipWithIndex.foldLeft(Right(List.empty[T]): Either[String, List[T]]) {
          case (acc, (line, idx)) => acc.flatMap(list =>
            implicitly[CsvDecoder[T]].decode(line).left.map(err => s"Line ${idx+1}: $err").map(_ :: list)
          )
        }.map(_.reverse)
      } finally {
        source.close()
      }
    }
  }
}

// Демонстрация работы
object CsvTest extends App {
  import CsvCodecs._
  import CsvFileUtils._
  import CardFunctions._

  // Тестовые данные
  val cards = List(
    MinorCard(5, Hearts),
    Jack(Diamonds),
    Queen(Clubs),
    King(Spades),
    Ace(Hearts),
    MinorCard(10, Diamonds)
  )

  // Сохраняем в файл
  val filename = "cards.csv"
  writeToCsv(filename, cards) match {
    case Success(_) => println(s"Successfully saved to $filename")
    case Failure(ex) => System.err.println(s"Save failed: ${ex.getMessage}")
  }

  // Загружаем из файла
  readFromCsv[Card](filename) match {
    case Right(loadedCards) =>
      println("\nLoaded cards:")
      loadedCards.foreach(println)
      
      println("\nValidation:")
      println("Original cards: " + cards)
      println("Loaded cards: " + loadedCards)
      println("Match: " + (cards == loadedCards))
    
    case Left(error) => System.err.println(s"Load failed: $error")
  }
}
