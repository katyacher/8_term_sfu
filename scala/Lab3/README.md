Пояснение:

    Типы данных:

        Suit (масть) и его 4 варианта

        Card с двумя ветвями: MinorCard и PictureCard

        Картинки разделены на конкретные типы: Jack, Queen, King, Ace

    Функции:

        isMinor: проверяет тип карты

        sameSuit: сравнивает масти карт

        beats/beats2: реализуют логику сравнения карт

        beatsList: фильтрует карты по условию

        blackjackSum: рекурсивно вычисляет все возможные суммы

    Особенности реализации:

        Использование sealed traits для ограничения возможных значений

        Рекурсивная обработка тузов в blackjackSum

        Валидация значений для MinorCard

Как использовать:

    Сохраните код в файл Cards.scala

    Запустите в REPL:

bash

scala
:load Cards.scala

    Тестируйте функции:

scala

CardFunctions.beats(MinorCard(7, Hearts), MinorCard(5, Hearts))
CardFunctions.blackjackSum(List(Ace(Hearts), Ace(Diamonds)))

Для работы с большими данными можно добавить:

    Потоковую обработку для beatsList

    Мемоизацию для blackjackSum

    Параллельные коллекции для операций с большими списками карт
