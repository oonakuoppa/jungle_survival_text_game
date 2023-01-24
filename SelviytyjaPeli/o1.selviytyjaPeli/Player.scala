package o1.selviytyjaPeli

import scala.collection.mutable.Map

/** `Player` -luokka edustaa pelaajaa. Täältä löytyy jutut, joita pelaaja voi tehdä.
  * @param startingArea  pelaajan alkuperäinen sijainti */
class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  var loytyyPuuta = false
  var majaRakennettu = false
  var tunnitNestehukkaan = 10 //käytä tätä ja ota mukaan game overiin, pystyyhän tämä täyttyä?

    /** Yrittää liikuttaa pelaajaa annettuun suuntaan. Jos esim pohjoiseen liikkuminen ei onnistu, tulee teksti "et pääse POHJOISEEN" ." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then "Menet " + direction + "." else "Et pääse " + direction + "."

    /** pelaaja saa vatsan täyteen ja nestehukkaan on taas 10h */
  def juo() =
    if this.currentLocation.name == "Puro" then // tämä on adventuressa
      juoTayteen()
    else
      println("Voit juoda vain purolla!")
      val teksti = this.currentLocation.name
      println(teksti)

  def juoTayteen() =
    tunnitNestehukkaan = 10


  /** pelaaja voi käyttää joko puukkoa tai puuta, komennoin "käytä >esine<".
    * Puukkoa voi käyttää puun saamiseksi ja puuta majan rakentamiseksi. */
  def kayta(esine: String) =
    if esine == "puukkoa" then
      if this.currentLocation.name == "Aukio" && this.currentLocation.name == "Puro" then
        println("Täällä ei ole puita. Mene metsään.")
      else if this.currentLocation.name == "Bambumetsä" then
        println("Hyvä! Sait puukolla leikattua bambuja majaa varten. Komennolla \"rakenna\" voit rakentaa yösijan sille alueelle, jossa olet. Harkitse tarkkaan.")
        loytyyPuuta = true
      else
        println("Hyvä yritys, mutta nämä puut ovat liian paksuja puukolla leikattavaksi. Etsi toisenlaisia puita.")
    else if esine == "puuta" then
      if loytyyPuuta then
        majaRakennettu = true
        println("Rakensit bambusta ja köydestä majan. Hyvä! Kömmit helpottuneena nukkumaan.")
      else
        println("Tarvitset majan rakentamiseksi puuta.")
    else
      println(s"Hyvä yritys, mutta et voi käyttää $esine koska sitä ei ole. \n Käytä komentoa \"help\" jos tarvitset apua.")



  /** Tämä kertoo pelaajalle, onko ymppäristössä sivuja kirjasta
    * tutki-funktiota käytetään pelaajan kutsusta.*/
  def tutki() =
    val etsiminen1 = "\nTutkit huolellisesti maata ja kasvustoa, kunnes huomaat jotain.\n"
    val etsiminen2 = "\nHetken etsittyäsi löydät jotain!\n"
    if this.currentLocation.name == "Aukio" then
      println("\nAukiolta löytyy kirjasta revitty sivu, jossa lukee:")
      println("\"Eucalyptuspuissa on hämähäkkejä karkottavia ominaisuuksia.\"\nJätät sivun aukiolle.")
    else if this.currentLocation.name == "Bambumetsä" then
      println("\nKatsot jalkasi alle ja löydät kirjasta revityn sivun. Siinä lukee:")
      println("\"Viidakossa on hengenvaarallisia hämähäkkejä. Ne yrittävät oikeasti syödä sinut!\nRakenna maja isoon puuhun, jotta vältät hämähäkit. \nVältä kuitenkin suuria Ficus-puita, ne ovat hämähäkkipesäke jo itsessään.\nLaitat sivun takaisin.\n\n    Sivuhuomautus: jos olet alueella jossa on puita, maja rakennetaan automaattisesti puuhun.\"")
    else if this.currentLocation.name == "Viidakko" then
      println( etsiminen2 + "Löydät viidakosta kirjasta revityn sivun, jossa lukee:")
      println("\"Ihminen selviää ilman nestettä hieman olosuhteista riippuen muutaman vuorokauden.\nKuumassa voi kuolema kohdata hyvinkin nopeasti, jopa tunneissa. Juo vettä vähintään 8h välein!\"\nLaitat sivut takaisin maahan.")
    else if this.currentLocation.name == "Eucalyptusmetsä" then
      println( etsiminen1 + "Eucalyptusmetsästä löytyy kirjasta revitty sivu, jossa lukee:")
      println("\"Vaarallisia käärmeitä löytää todennäköisimmin vesistöjen läheltä.\nVältä siis nukkumista ja pitkää oleskelua veden luona.\"")
      println("Tarkemmin tarkasteltuasi löydät toisenkin sivun. Siinä lukee näin:\n\"Puukolla ei saa hakattua paksua puuta, mutta bambumetsän bambut ovat tarpeeksi pieniä.\"\nLaitat sivut takaisin.")
    else if this.currentLocation.name == "Heräämispaikka" then
      println("\nLöysit muistikirjan, mutta sivut on revitty irti. \nKäännät kirjan ja takakannessa on teksti:\n\"Amazonin tienoilla aurinko nousee taivaanrannasta kello viisi.\nAuringonlasku on kello 19.\"\nLaitat kirjan takaisin.")
    else
      println("\nTäältä ei löydy mitään mainitsemisen arvoista. Etsi muualta.")



  /** Määrittää, onko pelaaja ilmoittanut haluavansa lopettaa pelin. */
  def hasQuit = this.quitCommandGiven

    /** Signaali siitä, että pelaaja haluaa lopettaa pelin. Palauttaa kuvauksen tapahtuneesta
    * pelin sisällä tämän seurauksena (joka on tässä tapauksessa tyhjä merkkijono). */
  def quit() =
    this.quitCommandGiven = true

  /** Palauttaa pelaajan nykyisen sijainnin. */
  def location = this.currentLocation

  /** Palauttaa, onko maja valmis */
  def majaValmis: Boolean = majaRakennettu
  
  def nukkuuEucalyptuksessa:Boolean = this.currentLocation.name == "Eucalyptusmetsä" 
  
  /** Palauttaa, montako tuntia nestehukkaan eli pökertymiseen */
  def nesteTilanne = tunnitNestehukkaan

  /** Pelaaja on yhden tunnin lähempänä nestehukkaa. */
  def aikaaKuluu() = tunnitNestehukkaan -= 1

end Player