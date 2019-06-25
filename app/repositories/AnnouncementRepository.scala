package repositories

import javax.inject.{Inject, Singleton}
import models.Announcement
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class AnnouncementRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext){
private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private class AnnouncementTable(tag: slick.lifted.Tag) extends Table[Announcement](tag,"announcements")
  {
    def id = column[Long]("id")
    def title = column[String]("title")
    def content = column[String]("content")
    def authorId = column[Long]("authorId")
    def year = column[Int]("year")

    def * = (id.?, title, content, authorId,year) <> (Announcement.tupled,Announcement.unapply)
  }
  private val announcements = TableQuery[AnnouncementTable]

  def all(): Future[Seq[Announcement]] = db.run {
    announcements.result
  }

  def insert(announcement: Announcement): Future[Int] = db.run(announcements += announcement)

}
