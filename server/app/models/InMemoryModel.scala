package models

import scala.collection.mutable

object InMemoryModel {
  private val users = mutable.Map[String,String]("Drew" -> "pass")
  private val items = mutable.Map[String, List[String]]("Drew" -> List("Kaolin Clay", "fragrance", "peppermint essential oil"))

  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map(_ == password).getOrElse(false)
  }

  def createUser(username: String, password: String): Boolean = {
    if (users.contains(username)) false else {
      users(username) = password
      true
    }
  }

  def getTasks(username: String): Seq[String] = {
    items.get(username).getOrElse(Nil)
  }

  def addTask(username: String, task: String): Unit = {
    items(username) = task :: items.get(username).getOrElse(Nil)
  }

  def removeTask(username: String, index: Int): Boolean = {
    if (index < 0 || items.get(username).isEmpty || index >= items(username).length) false
    else {
      items(username) = items(username).patch(index, Nil, 1)
      true
    }
  }

}
