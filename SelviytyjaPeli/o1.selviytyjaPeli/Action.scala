
package o1.selviytyjaPeli

/** Tässä luokassa on komennot, joita pelaaja voi käyttää pelin aikana.
  *
  * @param input  pelin aikana annettu komento, kuten "mene pohjoiseen", "käytä puuta" tai "juo".*/
class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  /** Alla on komennot, joita käyttämällä voi edetä pelissä. Jos syöttää väärän komennon, ei tapahdu mitään.*/
  def execute(actor: Player) = this.verb match
    case "mene"        => Some(actor.go(this.modifiers))
    case "juo"         => Some(actor.juo()) //toimii vain purolla
    case "käytä"       => Some(actor.kayta(this.modifiers)) //yritä käyttää puukkoa
    case "tutki"       => Some(actor.tutki()) // kertoo onko ymppäristössä sivuja kirjasta
    case "apua"        => Some("\"Mene pohjoiseen\"-komennolla liikut pohjoiseen. Sama idea pätee muihinkin ilmansuuntiin.\n\"Juo\": juo vettä.\n\"Tutki\"-komennolla tutkit lähiympäristöä. \nJoskus näet asioita maassa heti, joskus ne ovat kasvuston seassa ja näet ne vasta tutkimisen jälkeen. \n\"Käytä puukkoa\" -komennolla voit yrittää leikata puuta majapaikkaa varten. \n\"Käytä puuta\" -komennolla rakennat majan (jos sinulla on puuta). Jos olet metsässä, maja rakennetaan aina puuhun. \n\"Lopeta\": poistu pelistä.\n")
    case "lopeta"      => Some(actor.quit()) //lopeta
    case other         => None
    


  /** Debuggausta varten. */
  override def toString = s"$verb (modifiers: $modifiers)"

end Action
