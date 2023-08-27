package controllers

import play.api.db._
import play.api.mvc._

import javax.inject._

@Singleton
class HomeController @Inject()(db: Database, val cc: ControllerComponents) extends AbstractController(cc) {


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
