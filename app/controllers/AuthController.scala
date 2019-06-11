package controllers

import akka.actor.ActorSystem
import javax.inject._
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc._
import play.api.libs.json._
import utils.IIETAccountsAPI

import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               actorSystem: ActorSystem,
                               ws: WSClient)(implicit ex: ExecutionContext) extends AbstractController(cc) {

  def login = Action {
    Redirect(IIETAccountsAPI.authURL)
  }

  def callback(authorizationCode: String) = Action.async {
    getToken(authorizationCode).map(response => Ok(response.body))
  }

  def getPublicUserData(userToken: String) = Action.async {
    IIETAccountsAPI.getPublicDataRequest(ws, userToken)
      .get()
      .map(response => Ok(response.body))
  }

  def getExtendedUserData(userToken: String) = Action.async {
    IIETAccountsAPI.getExtendedDataRequest(ws, userToken)
      .get()
      .map(response => Ok(response.body))
  }

  private def getToken(authorizationCode: String) = {
    import IIETAccountsAPI.ClientAttribute._

    val body = Json.obj(
      "client_id" -> IIETAccountsAPI.clientDetails(ClientID),
      "client_secret" -> IIETAccountsAPI.clientDetails(Secret),
      "grant_type" -> "authorization_code",
      "code" -> authorizationCode,
      "redirect_uri" -> IIETAccountsAPI.clientDetails(CallbackURL)
    )
    val request: WSRequest = IIETAccountsAPI.getTokenRequest(ws)
    request.post(body)
  }
}
