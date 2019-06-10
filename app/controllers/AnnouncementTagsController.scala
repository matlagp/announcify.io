package controllers

import com.google.inject.Inject
import javax.inject.Singleton
import models.{AnnouncementTag, Model}
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.mvc._
import repositories.AnnouncementTagsRepository

import scala.concurrent.ExecutionContext

@Singleton
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

  def index() = Action.async { implicit request: Request[AnyContent] =>
    repo.all().map(announcementTag => Ok(Json.toJson(announcementTag)))
  }
}
