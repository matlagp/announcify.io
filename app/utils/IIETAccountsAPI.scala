package utils

import play.api.libs.ws.ahc.AhcCurlRequestLogger
import play.api.libs.ws.{WSClient, WSRequest}
import scala.concurrent.duration._

object IIETAccountsAPI {
  object ClientAttribute extends Enumeration {
    type ClientAttribute = Value
    val ClientID, Secret, CallbackURL = Value
  }
  import ClientAttribute._

  val clientDetails: Map[ClientAttribute, String] = Map(
    ClientID    -> sys.env("IIET_CLIENT_ID"),
    Secret      -> sys.env("IIET_CLIENT_SECRET"),
    CallbackURL -> "https://localhost:9000/callback"
  )

  private val accountsTimeout = 10000.millis
  val accountsHost: String = "accounts.iiet.pl"
  val accountsBaseURL: String = "https://" + accountsHost

  // OAuth endpoints
  val authURL: String = accountsBaseURL + "/oauth/authorize?" +
                        "response_type=code&" +
                        "scope=public%20extended&" +
                        "client_id=" + clientDetails(ClientID) + "&" +
                        "redirect_uri=" + clientDetails(CallbackURL)
  val tokenURL: String = accountsBaseURL + "/oauth/token"
  val publicURL: String = accountsBaseURL + "/oauth/v1/public"
  val extendedURL: String = accountsBaseURL + "/oauth/v1/extended"

  def getTokenRequest(ws: WSClient): WSRequest = {
    ws.url(tokenURL)
      .withHttpHeaders(
        "Host" -> accountsHost,
        "Content-Type" -> "application/json")
      .withRequestTimeout(accountsTimeout)
      .withRequestFilter(AhcCurlRequestLogger())
  }

  def getPublicDataRequest(ws: WSClient, token: String): WSRequest = {
    ws.url(publicURL)
      .withHttpHeaders(
        "Host" -> accountsHost,
        "Authorization" -> "Bearer ".concat(token))
      .withRequestTimeout(accountsTimeout)
  }

  def getExtendedDataRequest(ws: WSClient, token: String): WSRequest = {
    ws.url(extendedURL)
      .withHttpHeaders(
        "Host" -> accountsHost,
        "Authorization" -> "Bearer ".concat(token))
      .withRequestTimeout(accountsTimeout)
  }
}
