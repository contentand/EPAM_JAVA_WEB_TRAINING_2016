#Design Patterns
Implemnting design patterns

##Abstract Factory
***Film Distribution.* Task:** Разработать систему Кинопрокат. Пользователь мо-
      жет выбрать определенную киноленту, при заказе киноленты указывается язык 
      звуковой дорожки, который совпадает с языком файла субтитров. Система 
      должна поставлять фильм с требуемыми характеристиками, причем при смене 
      языка звуковой дорожки должен меняться и язык файла субтитров и наоборот.

*Abstract Factory* : [FilmFactory]()
*Concrete Factory* : [MyFilmFactory]()
*Abstract Product* : [Film]()
*Concrete Product* : [FateIronyFilm](), [ShrekFilm](), [TheLordOfTheRingsFilm]()
*Client* : [FilmDistributionIntegrationTest]()

