package maven.example

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class Resident(residentName: String, residentAge: Int, residentRole: Option[String])

object Resident {

  def setRole(age: Int): Option[String] = {
    age match {
      case a if a > 40 => Some("Manager")
      case _ => None
    }
  }

  def validateApply(residentName: String, residentAge: Int): Resident = new Resident(residentName, residentAge, setRole(residentAge))

  implicit val residentReads: Reads[Resident] = (
    (JsPath \ "name").read[String](minLength[String](2)) and
      (JsPath \ "age").read[Int](min(0) keepAnd max(150))
    )(Resident.validateApply _)

  implicit val residentWrites: Writes[Resident] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "age").write[Int] and
      (JsPath \ "role").writeNullable[String]
    )(unlift(Resident.unapply))
}
