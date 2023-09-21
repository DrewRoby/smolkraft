package controllers

import models.{PostgresModel, UserData}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc._
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, val cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{

  private val model = new PostgresModel(db)

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]): Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.HomeController.index()))
      }
    }.getOrElse(Future.successful(Redirect(routes.HomeController.index())))
  }

  def withSessionUsername(f: String => Future[Result])(implicit request: Request[AnyContent]): Future[Result]  = {
    request.session.get("username").map(f).getOrElse(Future.successful(Ok(Json.toJson(Seq.empty[String]))))
  }

  def validate = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
        model.validateUser(ud.username, ud.password).map { userExits =>
          if (userExits) {
          Ok(Json.toJson(true))
            .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
        } else {
          Ok(Json.toJson(false))
        }
      }
    }
  }

  def index = TODO
//    Action.async { implicit request: Request[AnyContent] =>
  //    //if user is logged in, redirect to home
  //    //if user is not logged in, redirect to login
//    val usernameOption = request.session.get("username")
//    usernameOption.map { username =>
//      Ok(routes.HomeController.home())(username -> username)
//    }.getOrElse(Redirect(routes.HomeController.validate()))
//    }

  def home() = Action.async { implicit request: Request[AnyContent] =>
    Ok(views.html.home())
  }

  def logout() = Action { implicit request =>
    Ok(Json.toJson(true)).withNewSession
  }

  def createUser() = Action.async { implicit request =>
    withJsonBody[UserData] { ud => model.createUser(ud.username, ud.password).map { userCreated =>
      if (userCreated) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      } else {
        Ok(Json.toJson(false))
      }
    } }
  }

}
