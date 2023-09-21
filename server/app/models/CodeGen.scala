package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://db-postgresql-nyc1-72843-do-user-5038101-0.b.db.ondigitalocean.com:25060/smolkraft_dev?user=smolkraft_svc_dev&password=AVNS_BLdmqcNPwkPlcRl97GR",
    "/home/crow/code/smolkraft/app/",
    "models", None, None, true, false
  )
}
// To run:
// in sbt: runMain models.CodeGen