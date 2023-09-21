package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import models.Tables._
import helpers.helpers._
import org.mindrot.jbcrypt.BCrypt

class PostgresModel(db: Database)(implicit ec: ExecutionContext) {
  def validateUser(username: String, password: String): Future[Boolean] = {
    val matches = db.run(Users.filter(usersRow => usersRow.userDisplayName === username).result)
    //This doesn't make sense, but it compiles.  Should just be userRow.password (string vice option[string])...
    matches.map(userRows => userRows.filter(userRow => BCrypt.checkpw(password, userRow.password)).nonEmpty)
  }

// TODO: add a default user if none exists in the database (for dev purposes, if I have to nuke the db)
  def createUser(username: String, password: String): Future[Boolean] = {
    //...and this should be just String but compiler says it needs Option[String]
    db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt()), "blurp", "flurp", "shmurp",Some("plurp"),getCurrentTimestamp,getCurrentTimestamp))
      .map(addCount => addCount > 0)
  }

}
