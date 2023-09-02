package controllers
import play.api.mvc._

import javax.inject._

@Singleton
class InventoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def addIngredient() = Action { implicit request =>
    Ok(views.html.todo())
  }
}
