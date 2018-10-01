package maven.example

import play.api.libs.json._

object jsonParser extends App {

  override def main(args: Array[String]): Unit = {
    val jValue = """[{"name":"Manager","age":45}, {"name":"Boss","age":50},{"age":25, "name":"Nothing"}]"""
    println(parserTest(jValue))
  }

  def parserTest(jValue: String): Option[String] = {
    val json = Json.parse(jValue)

    val sortedResidents = json.validate[Seq[Resident]] match {
      case JsSuccess(residents, _) => Some(residents.sortWith(_.residentName < _.residentName))
      case _ => None
    }

    sortedResidents match {
      case Some(rs) => Some(Json.toJson(rs).toString())
      case _ => None
    }
  }
}




