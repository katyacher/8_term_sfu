// Определение алгебраических типов данных
sealed trait Suit
case object Clubs extends Suit
case object Diamonds extends Suit
case object Hearts extends Suit
case object Spades extends Suit

sealed trait Card {
  val suit: Suit
}

case class MinorCard(value: Int, suit: Suit) extends Card {
  require(value >= 2 && value <= 10, "Младшая карта должна быть от 2 до 10")
}

sealed trait PictureCard extends Card
case class Jack(suit: Suit) extends PictureCard
case class Queen(suit: Suit) extends PictureCard
case class King(suit: Suit) extends PictureCard
case class Ace(suit: Suit) extends PictureCard

object CardFunctions {
  // a. Проверка на младшую карту
  def isMinor(card: Card): Boolean = card match {
    case _: MinorCard => true
    case _ => false
  }

  // b. Проверка одинаковой масти
  def sameSuit(cards: Card*): Boolean = {
    cards.map(_.suit).distinct.size == 1
  }

  // c. Проверка, бьет ли первая карта вторую (без козырей)
  def beats(card1: Card, card2: Card): Boolean = {
    card1.suit == card2.suit && (cardValue(card1) > cardValue(card2)
  }

  // d. Проверка с учетом козырной масти
  def beats2(card1: Card, card2: Card, trump: Suit): Boolean = {
    (card1.suit == trump && card2.suit != trump) ||
    (card1.suit == card2.suit && cardValue(card1) > cardValue(card2))
  }

  // e. Фильтрация списка карт
  def beatsList(cards: List[Card], target: Card, trump: Suit): List[Card] = {
    cards.filter(c => beats2(c, target, trump))
  }

  // f. Расчет возможных сумм для Blackjack
  def blackjackSum(cards: List[Card]): List[Int] = {
    def processAces(values: List[Int], aces: Int): List[Int] = {
      if (aces == 0) values
      else processAces(values.flatMap(v => List(v + 1, v + 11)), aces - 1)
    }
    
    val (baseSum, aces) = cards.foldLeft((0, 0)) {
      case ((sum, aces), card) => card match {
        case MinorCard(v, _) => (sum + v, aces)
        case Jack(_) | Queen(_) | King(_) => (sum + 10, aces)
        case Ace(_) => (sum, aces + 1)
      }
    }
    
    processAces(List(baseSum), aces).distinct.sorted
  }

  private def cardValue(card: Card): Int = card match {
    case MinorCard(v, _) => v
    case Jack(_) => 11
    case Queen(_) => 12
    case King(_) => 13
    case Ace(_) => 14
  }
}

// Демонстрация работы
object Main extends App {
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

  println("Тест isMinor:")
  println(cards.map(isMinor)) // [true, false, false, false, false, true]

  println("\nТест sameSuit:")
  println(sameSuit(MinorCard(2, Hearts), Ace(Hearts))) // true

  println("\nТест beats:")
  println(beats(MinorCard(7, Hearts), MinorCard(5, Hearts))) // true

  println("\nТест beats2 (козырь Diamonds):")
  println(beats2(Jack(Diamonds), Ace(Hearts), Diamonds)) // true

  println("\nТест beatsList:")
  val candidates = List(MinorCard(8, Hearts), Jack(Diamonds), Ace(Hearts))
  println(beatsList(candidates, MinorCard(5, Hearts), Hearts)) // [MinorCard(8,Hearts)]

  println("\nТест blackjackSum:")
  val hand = List(MinorCard(5, Hearts), Ace(Hearts), Ace(Diamonds))
  println(blackjackSum(hand)) // List(7, 17, 27, 37)
}
