package helpers
import org.joda.time.{DateTime, DateTimeZone}

import java.sql.Timestamp

object helpers {
  val testUserDisplayName = "test"
  val testPassword = "myPass123"
  val testFirstName = "Janetest"
  val testLastName = "Doetest"
  val testEmailPrimary = "test@example.com"
  val testEmailSecondary = "noreply@mybusiness.com"
  val testIngredients = List("Kaolin Clay", "fragrance", "peppermint essential oil")

  def getCurrentTimestamp: Timestamp = {
    // Get the current date and time in UTC
    val now = DateTime.now(DateTimeZone.UTC)

    // Convert it to a Java Timestamp
    new Timestamp(now.getMillis)
  }

}
