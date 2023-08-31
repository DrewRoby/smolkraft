package controllers

import org.joda.time.DateTime
import play.api.mvc._

import javax.inject._



@Singleton
class PlanningController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def addEvent(dateStart: DateTime, dateEnd: DateTime) = Action { implicit request =>
    Ok(views.html.todo())
  }

  def removeEvent(eventId: Int)= Action { implicit request =>
    Ok(views.html.todo())
  }

  def scheduleProductOffer(eventId: Int, productId: Int, amount: Int) = Action { implicit request =>
    Ok(views.html.todo())
  }

  def unscheduleProductOffer(eventId: Int, productId: Int)= Action { implicit request =>
    Ok(views.html.todo())
  }

  def addOrder(productId: Int, amount: Int, generatingUserId: Int, customerId: Int) = Action { implicit request =>
    Ok(views.html.todo())
  }

  def removeOrder(orderId: Int) = Action { implicit request =>
    Ok(views.html.todo())
  }

  def calculateShortfall(orderId: Int) = Action { implicit request =>
    Ok(views.html.todo())
  }


}
