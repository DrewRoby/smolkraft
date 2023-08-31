package controllers

import models.{InMemoryModel, UserData}
//import play.api.Database._
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc._

import javax.inject._

@Singleton
class HomeController @Inject()(val cc: ControllerComponents) extends AbstractController(cc) {

  def withJsonBody[A](f: A => Result)(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match {
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Redirect(routes.HomeController.index())
      }
    }.getOrElse(Redirect(routes.HomeController.index()))
  }

  def validate = Action { implicit request =>
    withJsonBody[UserData] { ud =>
      if (InMemoryModel.validateUser(ud.username, ud.password)) {
        Ok(Json.toJson(true))
          .withSession("username" -> ud.username, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
      } else {
        Ok(Json.toJson(false))
      }
    }
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def home() = Action { implicit request: Request[AnyContent] =>
    val ingredients = List("Kaolin Clay", "fragrance", "peppermint essential oil")
    Ok(views.html.home(ingredients))
  }

  def addIngredient() = Action { implicit request =>
    Ok(views.html.todo())
  }
}
