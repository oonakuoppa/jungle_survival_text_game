package o1.selviytyjaPeli

/** Luokka "Adventure" edustaa tekstiseikkailupeliä. Seikkailu koostuu pelaajasta ja
  * useista alueista, jotka muodostavat pelimaailman.
  *
 */
class Adventure:

  /** pelin nimi*/
  val title = "Selviytyjä"

  private val aukio            = Area("Aukio", "Olet aukiolla. Näet puron idässä ja tiheän bambumetsän etelässä. Maassa lojuu jotain.")
  private val puro             = Area("Puro", "Olet kirkkaan, vilkkaasti virtaavan puron äärellä.")
  private val eucalyptusmetsa1 = Area("Eucalyptusmetsä", "Olet eucalyptusmetsässä. Kasvusto on niin tiheää, ettet näe nilkkojasi. Kuulet lännestä puron solinaa.")
  private val eucalyptusmetsa2 = Area("Eucalyptusmetsä", "Olet eucalyptusmetsässä. Kuulet kauempana puron solinaa. Idässä näet paikan jossa heräsit.")
  private val eucalyptusmetsa3 = Area("Eucalyptusmetsä", "Olet eucalyptusmetsässä. Puroa ei enää kuulu.")
  private val bambumetsa       = Area("Bambumetsä", "Olet matalassa bambumetsässä.\nKuulet kauempana puron solinaa. Astut jonkin kahisevan päälle.")
  private val heraamispaikka   = Area("Heräämispaikka", "Ympärilläsi on valtavia Ficus puita. Idässä näet bambutiheikön.\nKuulet puron solinaa. Jalkojen juuressa on jotain.")
  private val viidakko1        = Area("Viidakko", "Ympärilläsi on valtavia Ficus puita. \nPohjoisessa on bambumetsää. Puroa ei enää kuulu.")
  private val viidakko2        = Area("Viidakko", "Ympärilläsi on valtavia Ficus puita. \nPohjoisessa on paikka josta heräsit. Puroa ei enää kuulu.")


  /** suunnat, joihin voi mistäkin sijainnista mennä*/
  aukio             .setNeighbors(Vector("pohjoiseen" -> aukio,            "itään" -> puro,             "etelään" -> bambumetsa                            ))
  puro              .setNeighbors(Vector(                                  "itään" -> eucalyptusmetsa1, "etelään" -> heraamispaikka,    "länteen" -> aukio))
  eucalyptusmetsa1  .setNeighbors(Vector("pohjoiseen" -> eucalyptusmetsa1, "itään" -> eucalyptusmetsa1, "etelään" -> eucalyptusmetsa2,  "länteen" -> puro))
  bambumetsa        .setNeighbors(Vector("pohjoiseen" -> aukio,            "itään" -> heraamispaikka,   "etelään" -> viidakko1 ,        "länteen" -> bambumetsa))
  heraamispaikka    .setNeighbors(Vector("pohjoiseen" -> puro,             "itään" -> eucalyptusmetsa2, "etelään" -> viidakko2,         "länteen" -> bambumetsa))
  eucalyptusmetsa2  .setNeighbors(Vector("pohjoiseen" -> eucalyptusmetsa1, "itään" -> eucalyptusmetsa2, "etelään" -> eucalyptusmetsa3,  "länteen" -> heraamispaikka))
  viidakko1         .setNeighbors(Vector("pohjoiseen" -> bambumetsa,       "itään" -> viidakko2 ,       "etelään" -> viidakko1,         "länteen" -> viidakko1))
  viidakko2         .setNeighbors(Vector("pohjoiseen" -> heraamispaikka,   "itään" -> eucalyptusmetsa3, "etelään" -> viidakko2,         "länteen" -> viidakko1))
  eucalyptusmetsa3  .setNeighbors(Vector("pohjoiseen" -> eucalyptusmetsa2, "itään" -> eucalyptusmetsa3, "etelään" -> eucalyptusmetsa3,  "länteen" -> viidakko2))


  /** Viidakkoon joutunut tyyppi, jota pelaaja ohjailee. */
  val player = Player(heraamispaikka)

  /** Kellonaika nyt, tämä luku pienenee jokaisen komennon jälkeen yhdellä*/
  var kellonaika = 5
  /** kellonaika kun aika loppuu kesken. Aurinko laskee viideltä illalla */
  val timeLimit = 19

  /**Muutama def joiden avulla saadaan tietoja UI:lle.*/

  def kellonaikaNyt: Int = this.kellonaika

  def aikaNestehukkaan: Int = this.player.nesteTilanne

  /** Jos pelaaja voitti pelin: */
  def isComplete: Boolean =
    this.player.nukkuuEucalyptuksessa && this.player.majaValmis

  /** Peli loppuu jos maja on rakennettu, pelaaja on lopettanut, kello on umpi tai nestehukka iski */
  def isOver =
    this.player.majaValmis || this.kellonaika == this.timeLimit || this.player.nesteTilanne == 0 || this.player.hasQuit


  //viestit:

    /** Viesti pelin alussa. */
  def welcomeMessage = "\n\nHeräät yksin viidakosta. Et tiedä miten sinne päädyit, mutta yritä pysyä hengissä.\n\nYmpärilläsi näet valtavia puita, kammottavan näköisiä ötököitä ja jossain kauempana ääntelee apina.\n\nTaskustasi löytyy puukko ja narua. \nRakenna itsellesi puusta ja narusta nukkumapaikka ennen pimeän tuloa, tai villipedot syövät sinut!\nPuuta saat hakkaamalla tarpeeksi pienirunkoista puuta puukolla.\nMoneltakohan aurinko laskee? Hmmmm.\n Ranteessa on kello."


  /** Teksti pelin lopussa. Teksti vaihtelee sen mukaan, selvisikö pelaaja. Jos ei selvinnyt, syy ilmenee myös tässä. */
  def goodbyeMessage =
    if this.player.nukkuuEucalyptuksessa && this.player.majaValmis then                                                 //jos selvisi hengissä
      "\nHuh, selvisit hengissä ensimmäisestä päivästä!\nValitsit nukkumapaikan viisaasti ja selviät myös yön hengissä."
    else if this.kellonaika == this.timeLimit then                          //jos aika loppui
      "\nAurinko laski ja nälkäinen tiikeri söi sinut!"
    else if this.player.nesteTilanne == 0 then                              //jos nestehukka iski
      "\nHups! Et juonut ajoissa ja pökerryit! Tajuttomana ollessasi aurinko ehti laskea ja nälkäinen tiikeri söi sinut!"
    else if this.player.majaValmis then                                     //jos rakensi majan väärään paikkaan, paikkakohtaiset kommentit
      val alku = "\nO-ou, pääsit kyllä nukkumaan mutta et ikinä herää...\n"
      val hamahakit = "Viidakon hengenvaaralliset hämähäkit söivät sinut!\n"
      val korkeus = "Olisit voinut välttää ne nukkumalla niin korkealla kuin voit!"
      if this.player.location.name == "Puro" then
        alku + "Myrkylliset käärmeet viihtyvät veden lähellä. Käärme söi sinut!"
      else if this.player.location.name == "Aukio" then
        alku + hamahakit + korkeus
      else if this.player.location.name == "Bambumetsä" then
        alku + hamahakit + korkeus + "Bambumetsä on liian matala."
      else if this.player.location.name == "Viidakko" || this.player.location.name == "Heräämispaikka" then
        alku + hamahakit + "Korkealla nukkuminen oli hyvä veto, mutta harmi vaan suuret Ficus-puut ovat hämähäkkipesäke.\nEucalyptuspuissa olisi ollut hämähäkkejä karkottavia ominaisuuksia."
      else
        "\nHuh, selvisit hengissä ensimmäisestä päivästä!\nValitsit nukkumapaikan viisaasti ja selviät myös yön hengissä."
    else                                                                  // jos luovutti
      "\nLuovuttaja!"


  /**
Pelaa vuoroa suorittamalla annetun pelin sisäisen komennon, kuten "mene itään".
Palauttaa tekstiraportin tapahtuneesta tai virheilmoituksen, jos komento oli tuntematon.
Jälkimmäisessä tapauksessa vuoroja ei kulu. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.kellonaika += 1
      this.player.aikaaKuluu()
    outcomeReport.getOrElse(s"""Tunnistamaton komento: "$command".""")

end Adventure

