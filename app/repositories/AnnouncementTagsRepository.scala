package repositories

import javax.inject.{Inject, Singleton}
import models.{Model, AnnouncementTag}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AnnouncementTagsRepository @Inject()
  (dbConfigProvider: DatabaseConfigProvider)
  (implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class AnnouncementTagsTable(tag: Tag) extends Table[Model[AnnouncementTag]](tag, "announcements_tags") {
    def id = column[Long]("tag_id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.Unique)
    def description = column[String]("description", O.Unique)

    // TODO: maybe there is some nice functional way to do it?
    def * = (id, name, description) <> (
      i => Model(i._1, AnnouncementTag(i._2, i._3)),
      (m: Model[AnnouncementTag]) => Some(m.id, m.obj.name, m.obj.description)
    )
  }

  private val announcementTags = TableQuery[AnnouncementTagsTable]

  def all(): Future[Seq[Model[AnnouncementTag]]] = db.run {
    announcementTags.result
  }
}
