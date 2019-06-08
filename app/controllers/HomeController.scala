package controllers

import javax.inject._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext
import io.swagger.annotations.{Api, ApiParam, ApiResponse, ApiResponses}


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
@Api
class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
                               cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {


  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid name supplied"),
    new ApiResponse(code = 404, message = "Name not found")))
  def sayHello(@ApiParam(value = "Name to say hello") name: String) = Action {
    Ok(s"hello $name")
  }
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
