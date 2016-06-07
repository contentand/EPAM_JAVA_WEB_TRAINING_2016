#Design Patterns
Implementing design patterns

##Abstract Factory
***Film Distribution.* Task:** Разработать систему Кинопрокат. Пользователь может 
      выбрать определенную киноленту, при заказе киноленты указывается язык 
      звуковой дорожки, который совпадает с языком файла субтитров. Система 
      должна поставлять фильм с требуемыми характеристиками, причем при смене 
      языка звуковой дорожки должен меняться и язык файла субтитров и наоборот.

+ *Abstract Factory* : [FilmFactory](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/FilmFactory.java)
+ *Concrete Factory* : [MainFilmFactory](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/MainFilmFactory.java)
+ *Abstract Product* : [Film](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/Film.java)
+ *Concrete Product* : [FateIronyFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/FateIronyFilm.java), [ShrekFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/ShrekFilm.java), [TheLordOfTheRingsFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/TheLordOfTheRingsFilm.java)
+ *Client* : [FilmDistributionIntegrationTest](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/test/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/test/FilmDistributionIntegrationTest.java)

##Prototype
***Wikipedia Article File* Task:** Существует набор статей в википедии. Реализовать процесс 
                                   раздачи статей потребованию для изменения, сохраняя исходный
                                   вариант для возможного восстановления статьи висходном виде.

+ *Prototype* : java.lang.Cloneable
+ *Concrete Prototype* : [WikipediaArticle](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/prototype/wikipedia/WikipediaArticle.java)
+ *Client* : [WikipediaFile](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/prototype/wikipedia/WikipediaArticleFile.java)

*Service Client* : [WikipediaArticleFileIntegrationTest](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/test/java/com/daniilyurov/training/patterns/prototype/wikipedia/WikipediaArticleFileIntegrationTest.java)

##Factory Method (Virtual Constructor)
***Tetris Figure Generator* Task:** Фигуры игры «тетрис». Реализовать процесс случайного
                                    выбора фигуры из конечного набора фигур. Предусмотреть 
                                    появление супер-фигур с большим числом клеток, чем обычные.

+ *Product* : [Figure](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/fabricmethod/tetris/Figure.java)
+ *Concrete Product* : [Figure I...Z, SuperFigure](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/fabricmethod/tetris/figures)
+ *Creator* : [FigureCreator](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/fabricmethod/tetris/FigureCreator.java)
+ *Concrete Creator* : [RandomFigureCreator](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/fabricmethod/tetris/creators/RandomFigureCreator.java)
+ *(Client)* : [TetrisComponent](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/fabricmethod/tetris/TetrisComponent.java)

##Strategy
***Fantasy Game* Task:** Разработать модель игровой системы. Предусмотреть 
наличие фентезийных персонажей: орки, тролли, пегасы, эльфы, вампиры, гарпии и др. 
Учесть, что некоторые персонажи ходят, другие— летают, третьи— и ходят и летают. 
Летать также может группа персонажей с помощью магии.

+ *Strategy* : [FlyingProperty](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/properties/FlyingProperty.java), [WalkingProperty](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/properties/WalkingProperty.java)
+ *Concrete Strategy* : [MagicFlyingProperty, ...](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/properties/flying), [UsualWalkingProperty, ...](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/properties/walking)
+ *Context* : [Character](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/Character.java) ([Orc, Elf, Troll, ...](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/strategy/game/characters))

##Observer
***Post Office* Task:** Разработать систему Почтовое отделение. Из издательства в почтовое 
отделение поступают издаваемые газеты и журналы. Почтовое отделение отправляет полученные печатные 
издания соответствующим подписчикам.

**!Note:** due to the nature of the task, the implementation of Subject does not contain state.

+ *Subject* : [PostOffice](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/observer/postoffice/PostOffice.java)
+ *Concrete Subject* : [KyivPostOffice](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/observer/postoffice/KyivPostOffice.java)
+ *Observer* : [Subscriber](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/observer/postoffice/Subscriber.java)
+ *Concrete Observer* : [KyivSubscriber](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/observer/postoffice/KyivSubscriber.java)

##State
***Educational Grant Application* Task:** Заказ на получение гранта для обучения может находиться в 
нескольких состояниях: создан, рассматривается, отложен, отклонен, подтвержден, отозван и т. д. 
Определить логику изменения состояний и разработать модель системы.

**!Note:** memory usage efficiency can possibly be improved by turning the Concrete States into Singletons.

+ *State* : [GrantState](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/state/educationalgrant/GrantState.java)
+ *Concrete State* : [CancelledGrantState, ConfirmedGrantState, ...](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/state/educationalgrant/grant_states)
+ *Context* : [GrantApplication](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/state/educationalgrant/GrantApplication.java)

##Chain Of Responsibility
***Bank Transfer* Task:** Прохождение платежа через банковскую систему сопровождается 
целым рядом действий: фиксирующих, контролирующих, снимающих процент банка и прочие вычеты и действия. 
Построить цепочки для различного вида платежей (обычных, льготных, государственных, 
внутрибанковских) в соответствии с предметной областью и разработать модель системы.

+ *Handler* : [TransactionStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/TransactionStep.java)
+ *Concrete Handler* : [AccountLockStep, AccountUnlockStep, ...](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/tree/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction)
+ *Client* : [MainTransactionChain](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/MainTransaction.java), [SubTransactionChain](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/SubTransaction.java)

***Chain flow and model description:***

> [**MainTransaction**](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/MainTransaction.java) : main chain invoked by client.

> ***Step 1.*** [PaymentTypeSupportValidationStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/PaymentTypeSupportValidationStep.java) : check source Bank and destination Bank and source and destination Account support the payment type.
>
> ***Step 2.*** [AccountLockStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/AccountLockStep.java) : lock source Account and destination Account

>> [**SubTransaction**](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/SubTransaction.java) : nested chain invoked by main chain in case lock up is successful.

>> ***Step 2.1.*** [TransferExpensesCalculationStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/TransferExpensesCalculationStep.java) : add commissions and taxes of source Bank and destination Bank to the amount
>>
>> ***Step 2.2.*** [SufficientFundsValidationStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/SufficientFundsValidationStep.java) : check source Account has enough funds to cover account and additional costs.
>>
>> ***Step 2.3.*** [FundWithdrawalStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/FundWithdrawalStep.java) : withdraw and transfer money to all parties involved.

> ***Step 3.*** [AccountUnlockStep](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/chain_of_responsibility/bank_transactions/transaction/AccountUnlockStep.java) : unlock source and destination Account

##Memento
***TicTacToe Game* Task:** Реализовать алгоритм игры «крестики-нолики». Реализовать возможность «взять назад ход»

+ *Caretaker* : [FieldMementoCaretaker](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/memento/tictactoe/FieldMementoCaretaker.java)
+ *Memento* : [FieldMemento](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/memento/tictactoe/FieldMemento.java)
+ *Originator* : [Field](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/memento/tictactoe/Field.java)

##Flyweight
***Bacteria Colony* Task:** Разработать систему учета процессов размножения колонии бактерий.

+ *Flyweight* : [BacteriaCore]()
+ *Concrete Flyweight* : [TerribleBacteriaCore]()
+ *Flyweight Factory* : [BacteriaCoreFactory]()
+ *Client* : [TerribleBacteria]()