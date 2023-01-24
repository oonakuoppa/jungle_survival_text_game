package o1.selviytyjaPeli

import scala.collection.mutable.Map

/**
Luokka "Area" edustaa paikkoja tekstiseikkailupelimaailmassa.
Pelimaailma koostuu viidakossa olevista alueista, jotka on määritelty luokassa Adventure.
Eri alueilla on yhteistä se, että pelaaja voi sijoittua niille ja niissä on uloskäynnit muille,
viereisille alueille. Alueella on myös nimi ja kuvaus.
  * @param name         alueen nimi
  * @param description  peruskuvaus alueesta */
class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()

  /** Palauttaa alueen, joka voidaan saavuttaa tältä alueelta liikkumalla annettuun suuntaan.
    * Vastaus on Option-tyyppinen. None palautetaan, jos haluttuun suuntaan ei pääse. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Lisää poistumiskohdan tältä alueelta annetulle alueelle.
    * Naapurialueelle päästään liikkumalla tältä alueelta määrättyyn suuntaan. */
  def setNeighbor(direction: String, neighbor: Area): Unit =
    this.neighbors += direction -> neighbor

  /** Lisää poistumiset tältä alueelta annetuille alueille.
    * Tämän menetelmän kutsuminen vastaa "setNeighbor"-menetelmän
    * kutsumista jokaisessa annetussa suunta-alue-parissa.*/
  def setNeighbors(exits: Vector[(String, Area)]): Unit =
    this.neighbors ++= exits


  /** Palauttaa monirivisen kuvauksen alueesta sellaisena kuin pelaaja sen näkee.
    * Tämä sisältää alueen peruskuvauksen sekä tiedot uloskäynneistä
    * Alueet joihin voi mennä, on erotettu pilkulla ja välilyönnillä.*/
  def fullDescription =
    val exitList = "\n\nVoit mennä suuntiin: " + this.neighbors.keys.mkString(", ") + "\nVoit myös tutkia, käyttää esineitä, ja juoda. \nLisätietoa löydät komennolla \"apua\"."
    this.description + exitList


  /** Debuggausta varten. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)



end Area


