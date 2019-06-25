package controllers

import com.google.inject.Inject
import io.swagger.annotations.{Api, ApiOperation}
import javax.inject.Singleton
import models.Announcement
import play.api.libs.json.{JsValue, Writes}
import play.api.mvc._
import repositories.AnnouncementRepository
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
@Api
class AnnouncementController @Inject()
(repo:AnnouncementRepository,cc: ControllerComponents)
(implicit ec:ExecutionContext) extends AbstractController(cc)
{
  implicit val announcementModelWrites: Writes[Announcement] = (o: Announcement) => Json.obj(
    "id" -> o.id,
    "title" -> o.title,
    "content" -> o.content,
    "authorId" -> o.authorId,
    "year" -> o.year
  )

  implicit val announcementReads: Reads[Announcement] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "title").read[String] and
      (JsPath \ "content").read[String] and
      (JsPath \ "authorId").read[Long] and
      (JsPath \ "year").read[Int]
    )(Announcement.apply _)

  @ApiOperation(
    value = "Retrieves all announcements available in system"
  )
  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    repo.all().map(announcement => Ok(Json.toJson(announcement)))
  }

  def create(): Action[JsValue] = Action.async(parse.json) { request =>
    val announcementResult = request.body.validate[Announcement]
    announcementResult.map { announcement =>
      repo.insert(announcement).map {
        _ => Created(Json.obj("status" -> "success"))
      }.recoverWith {
        case e => Future { InternalServerError("ERROR: " + e )}
      }
    }.recoverTotal {
      e => Future { BadRequest( Json.obj("status" -> "fail" )) }
    }
  }
}