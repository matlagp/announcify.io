package controllers

import com.google.inject.Inject
import io.swagger.annotations._
import javax.inject.Singleton
import models.{AnnouncementTag, Model}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.mvc._
import repositories.AnnouncementTagsRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
@Api
class AnnouncementTagsController @Inject()
  (repo: AnnouncementTagsRepository, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val announcementTagModelWrites = new Writes[Model[AnnouncementTag]] {
    override def writes(o: Model[AnnouncementTag]): JsValue = Json.obj (
      "id" -> o.id,
      "name" -> o.obj.name,
      "description" -> o.obj.description
    )
  }

  implicit val announcementTagReads: Reads[AnnouncementTag] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "description").read[String]
    )(AnnouncementTag.apply _)

  @ApiOperation(
    value = "Retrieves all announcement tags available in system"
  )
  def index() = Action.async { implicit request: Request[AnyContent] =>
    repo.all().map(announcementTag => Ok(Json.toJson(announcementTag)))
  }

  def create() = Action.async(parse.json) { request =>
    val announcementTagResult = request.body.validate[AnnouncementTag]
    announcementTagResult.map { announcementTag =>
      repo.insert(announcementTag).map {
        _ => Created(Json.obj("status" -> "success"))
      }.recoverWith {
        case e => Future { InternalServerError("ERROR: " + e )}
      }
    }.recoverTotal {
      e => Future { BadRequest( Json.obj("status" -> "fail" )) }
    }
  }
}
