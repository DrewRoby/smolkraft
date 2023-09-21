package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "redirect to /login from / in the absence of a CSRF token" in {

    }

    "redirect to /home from / in the presence of a CSRF token" in {

    }

    "display the Home dashboard when directed to /home in the presence of a CSRF token" in {

    }

    "list all upcoming events for a user on the Home dashboard" in {

    }

    "list all scheduled goods for upcoming events on the Home dashboard" in {

    }

    "list all outstanding orders on the Home dashboard" in {

    }

    "display finished goods inventory on the Home dashboard" in {

    }

    "display ingredient stock on the Home dashboard" in {

    }

    "display the user's list of recipes on the homne dashboard" in {

    }

    "remain logged in (not redirect to the login) on page refresh" in {

    }
  }
}
