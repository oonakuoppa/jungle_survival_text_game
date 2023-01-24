package o1.selviytyjaPeli.ui

import o1.selviytyjaPeli.*
import scala.io.StdIn.*


/**
Yksittäinen objekti `SelviytyjaTextUI` edustaa täysin tekstipohjaista versiota Selviytyjä-pelisovelluksesta.
Objekti toimii pelin sisääntulopisteenä, ja sitä voidaan käyttää tekstikonsolissa toimivan käyttöliittymän käynnistämiseen */
object AdventureTextUI extends App:

  private val game = Adventure()
  private val player = game.player
  this.run()

  /** Ajaa peliä. Ensin tulostetaan tervetuloviesti, sitten pelaajalla on mahdollisuus pelata niin kauan, kunnes aurinko laskee tai nestehukka
    * pökerryttää pelaajan. Lopuksi tulostetaan jäähyväiset. */
  private def run() =
    println(this.game.welcomeMessage)
    while !this.game.isOver do
      this.printAikaInfo()
      this.printJanoInfo()
      this.printAreaInfo()
      this.playTurn()
    println("\n" + this.game.goodbyeMessage)



  /** Tulostaa kuvauksen pelaajahahmon nykyisestä sijainnista. */
  private def printAreaInfo() =
    val area = this.player.location
    println("\n" + area.name)
    println("-" * area.name.length)
    println(area.fullDescription + "\n")

  /** Tulostaa kellonajan jokaisen kierroksen alussa. Aurinko laskee klo 18. */
  private def printAikaInfo() =
    val kellonaika = this.game.kellonaikaNyt
    println("Katsot kelloa. Se on " + kellonaika + ".")

  /** Tulostaa varoitustekstin, jos pelaaja lähestyy nestehukkaa.
    * Kun on enää yksi vuoro jäljellä, tulostuu uusi teksti */
  private def printJanoInfo() =
    if this.game.aikaNestehukkaan == 1 then
      println("JUO JUO JUO!!!")
    else if this.game.aikaNestehukkaan <= 4 then
      println("\nSinulla alkaa olla jano. Juo ettet pökerry!")
    else
      println(" ")


  /** Pyytää komennon pelaajalta, pelaa pelikierroksen vastaavasti
    *  ja tulostaa raportin tapahtuneesta. */
  private def playTurn() =
    println()
    val command: String = readLine("Mitä teet seuraavaksi? Anna komento: ")
    val turnReport = this.game.playTurn(command)
    println("\n" + turnReport)

end AdventureTextUI

