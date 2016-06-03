#Design Patterns
Implementing design patterns

##Abstract Factory
***Film Distribution.* Task:** Разработать систему Кинопрокат. Пользователь мо-
      жет выбрать определенную киноленту, при заказе киноленты указывается язык 
      звуковой дорожки, который совпадает с языком файла субтитров. Система 
      должна поставлять фильм с требуемыми характеристиками, причем при смене 
      языка звуковой дорожки должен меняться и язык файла субтитров и наоборот.

+ *Abstract Factory* : [FilmFactory](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/FilmFactory.java)
+ *Concrete Factory* : [MyFilmFactory](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/MyFilmFactory.java)
+ *Abstract Product* : [Film](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/Film.java)
+ *Concrete Product* : [FateIronyFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/FateIronyFilm.java), [ShrekFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/ShrekFilm.java), [TheLordOfTheRingsFilm](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/main/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/films/TheLordOfTheRingsFilm.java)
+ *Client* : [FilmDistributionIntegrationTest](https://github.com/contentand/EPAM_JAVA_WEB_TRAINING_2016/blob/master/Homework_DesignPatterns/src/test/java/com/daniilyurov/training/patterns/abstractfactory/filmdistribution/test/FilmDistributionIntegrationTest.java)

