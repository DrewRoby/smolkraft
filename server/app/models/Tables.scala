package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Batch.schema, CurrentStock.schema, Event.schema, EventScheduledGoods.schema, Instruction.schema, InstructionSet.schema, Material.schema, MaterialOrderHeader.schema, MaterialOrderLineItem.schema, RecipeHeader.schema, RecipeLineItem.schema, SubstituteSet.schema, Users.schema, Vendor.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Batch
   *  @param batchId Database column batch_id SqlType(serial), AutoInc, PrimaryKey
   *  @param recipeHeaderId Database column recipe_header_id SqlType(int4), Default(None)
   *  @param userId Database column user_id SqlType(int4), Default(None)
   *  @param batchDate Database column batch_date SqlType(timestamp without time zone), Default(None)
   *  @param packageDate Database column package_date SqlType(timestamp without time zone), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class BatchRow(batchId: Int, recipeHeaderId: Option[Int] = None, userId: Option[Int] = None, batchDate: Option[java.sql.Timestamp] = None, packageDate: Option[java.sql.Timestamp] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching BatchRow objects using plain SQL queries */
  implicit def GetResultBatchRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[java.sql.Timestamp]]): GR[BatchRow] = GR{
    prs => import prs._
    BatchRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table batch. Objects of this class serve as prototypes for rows in queries. */
  class Batch(_tableTag: Tag) extends profile.api.Table[BatchRow](_tableTag, "batch") {
    def * = (batchId, recipeHeaderId, userId, batchDate, packageDate, createdAt, updatedAt) <> (BatchRow.tupled, BatchRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(batchId), recipeHeaderId, userId, batchDate, packageDate, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> BatchRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column batch_id SqlType(serial), AutoInc, PrimaryKey */
    val batchId: Rep[Int] = column[Int]("batch_id", O.AutoInc, O.PrimaryKey)
    /** Database column recipe_header_id SqlType(int4), Default(None) */
    val recipeHeaderId: Rep[Option[Int]] = column[Option[Int]]("recipe_header_id", O.Default(None))
    /** Database column user_id SqlType(int4), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column batch_date SqlType(timestamp without time zone), Default(None) */
    val batchDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("batch_date", O.Default(None))
    /** Database column package_date SqlType(timestamp without time zone), Default(None) */
    val packageDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("package_date", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Batch */
  lazy val Batch = new TableQuery(tag => new Batch(tag))

  /** Entity class storing rows of table CurrentStock
   *  @param currentStockId Database column current_stock_id SqlType(serial), AutoInc, PrimaryKey
   *  @param batchId Database column batch_id SqlType(int4), Default(None)
   *  @param unitCount Database column unit_count SqlType(int4), Default(None)
   *  @param userId Database column user_id SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class CurrentStockRow(currentStockId: Int, batchId: Option[Int] = None, unitCount: Option[Int] = None, userId: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching CurrentStockRow objects using plain SQL queries */
  implicit def GetResultCurrentStockRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[java.sql.Timestamp]]): GR[CurrentStockRow] = GR{
    prs => import prs._
    CurrentStockRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table current_stock. Objects of this class serve as prototypes for rows in queries. */
  class CurrentStock(_tableTag: Tag) extends profile.api.Table[CurrentStockRow](_tableTag, "current_stock") {
    def * = (currentStockId, batchId, unitCount, userId, createdAt, updatedAt) <> (CurrentStockRow.tupled, CurrentStockRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(currentStockId), batchId, unitCount, userId, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> CurrentStockRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column current_stock_id SqlType(serial), AutoInc, PrimaryKey */
    val currentStockId: Rep[Int] = column[Int]("current_stock_id", O.AutoInc, O.PrimaryKey)
    /** Database column batch_id SqlType(int4), Default(None) */
    val batchId: Rep[Option[Int]] = column[Option[Int]]("batch_id", O.Default(None))
    /** Database column unit_count SqlType(int4), Default(None) */
    val unitCount: Rep[Option[Int]] = column[Option[Int]]("unit_count", O.Default(None))
    /** Database column user_id SqlType(int4), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table CurrentStock */
  lazy val CurrentStock = new TableQuery(tag => new CurrentStock(tag))

  /** Entity class storing rows of table Event
   *  @param eventId Database column event_id SqlType(serial), AutoInc, PrimaryKey
   *  @param ownerUserId Database column owner_user_id SqlType(int4), Default(None)
   *  @param eventName Database column event_name SqlType(varchar), Length(255,true), Default(None)
   *  @param eventLocation Database column event_location SqlType(varchar), Length(255,true), Default(None)
   *  @param eventDate Database column event_date SqlType(timestamp without time zone), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class EventRow(eventId: Int, ownerUserId: Option[Int] = None, eventName: Option[String] = None, eventLocation: Option[String] = None, eventDate: Option[java.sql.Timestamp] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching EventRow objects using plain SQL queries */
  implicit def GetResultEventRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[EventRow] = GR{
    prs => import prs._
    EventRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table event. Objects of this class serve as prototypes for rows in queries. */
  class Event(_tableTag: Tag) extends profile.api.Table[EventRow](_tableTag, "event") {
    def * = (eventId, ownerUserId, eventName, eventLocation, eventDate, createdAt, updatedAt) <> (EventRow.tupled, EventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(eventId), ownerUserId, eventName, eventLocation, eventDate, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> EventRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column event_id SqlType(serial), AutoInc, PrimaryKey */
    val eventId: Rep[Int] = column[Int]("event_id", O.AutoInc, O.PrimaryKey)
    /** Database column owner_user_id SqlType(int4), Default(None) */
    val ownerUserId: Rep[Option[Int]] = column[Option[Int]]("owner_user_id", O.Default(None))
    /** Database column event_name SqlType(varchar), Length(255,true), Default(None) */
    val eventName: Rep[Option[String]] = column[Option[String]]("event_name", O.Length(255,varying=true), O.Default(None))
    /** Database column event_location SqlType(varchar), Length(255,true), Default(None) */
    val eventLocation: Rep[Option[String]] = column[Option[String]]("event_location", O.Length(255,varying=true), O.Default(None))
    /** Database column event_date SqlType(timestamp without time zone), Default(None) */
    val eventDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("event_date", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Event */
  lazy val Event = new TableQuery(tag => new Event(tag))

  /** Entity class storing rows of table EventScheduledGoods
   *  @param eventScheduledGoodsId Database column event_scheduled_goods_id SqlType(serial), AutoInc, PrimaryKey
   *  @param eventId Database column event_id SqlType(int4), Default(None)
   *  @param currentStockId Database column current_stock_id SqlType(int4), Default(None)
   *  @param recipeHeaderId Database column recipe_header_id SqlType(int4), Default(None)
   *  @param unitsPlanned Database column units_planned SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class EventScheduledGoodsRow(eventScheduledGoodsId: Int, eventId: Option[Int] = None, currentStockId: Option[Int] = None, recipeHeaderId: Option[Int] = None, unitsPlanned: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching EventScheduledGoodsRow objects using plain SQL queries */
  implicit def GetResultEventScheduledGoodsRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[java.sql.Timestamp]]): GR[EventScheduledGoodsRow] = GR{
    prs => import prs._
    EventScheduledGoodsRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table event_scheduled_goods. Objects of this class serve as prototypes for rows in queries. */
  class EventScheduledGoods(_tableTag: Tag) extends profile.api.Table[EventScheduledGoodsRow](_tableTag, "event_scheduled_goods") {
    def * = (eventScheduledGoodsId, eventId, currentStockId, recipeHeaderId, unitsPlanned, createdAt, updatedAt) <> (EventScheduledGoodsRow.tupled, EventScheduledGoodsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(eventScheduledGoodsId), eventId, currentStockId, recipeHeaderId, unitsPlanned, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> EventScheduledGoodsRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column event_scheduled_goods_id SqlType(serial), AutoInc, PrimaryKey */
    val eventScheduledGoodsId: Rep[Int] = column[Int]("event_scheduled_goods_id", O.AutoInc, O.PrimaryKey)
    /** Database column event_id SqlType(int4), Default(None) */
    val eventId: Rep[Option[Int]] = column[Option[Int]]("event_id", O.Default(None))
    /** Database column current_stock_id SqlType(int4), Default(None) */
    val currentStockId: Rep[Option[Int]] = column[Option[Int]]("current_stock_id", O.Default(None))
    /** Database column recipe_header_id SqlType(int4), Default(None) */
    val recipeHeaderId: Rep[Option[Int]] = column[Option[Int]]("recipe_header_id", O.Default(None))
    /** Database column units_planned SqlType(int4), Default(None) */
    val unitsPlanned: Rep[Option[Int]] = column[Option[Int]]("units_planned", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table EventScheduledGoods */
  lazy val EventScheduledGoods = new TableQuery(tag => new EventScheduledGoods(tag))

  /** Entity class storing rows of table Instruction
   *  @param instructionId Database column instruction_id SqlType(serial), AutoInc, PrimaryKey
   *  @param instructionSetId Database column instruction_set_id SqlType(int4), Default(None)
   *  @param instructionText Database column instruction_text SqlType(varchar), Length(255,true), Default(None)
   *  @param instructionOrdinalNumber Database column instruction_ordinal_number SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class InstructionRow(instructionId: Int, instructionSetId: Option[Int] = None, instructionText: Option[String] = None, instructionOrdinalNumber: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching InstructionRow objects using plain SQL queries */
  implicit def GetResultInstructionRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[InstructionRow] = GR{
    prs => import prs._
    InstructionRow.tupled((<<[Int], <<?[Int], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table instruction. Objects of this class serve as prototypes for rows in queries. */
  class Instruction(_tableTag: Tag) extends profile.api.Table[InstructionRow](_tableTag, "instruction") {
    def * = (instructionId, instructionSetId, instructionText, instructionOrdinalNumber, createdAt, updatedAt) <> (InstructionRow.tupled, InstructionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(instructionId), instructionSetId, instructionText, instructionOrdinalNumber, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> InstructionRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column instruction_id SqlType(serial), AutoInc, PrimaryKey */
    val instructionId: Rep[Int] = column[Int]("instruction_id", O.AutoInc, O.PrimaryKey)
    /** Database column instruction_set_id SqlType(int4), Default(None) */
    val instructionSetId: Rep[Option[Int]] = column[Option[Int]]("instruction_set_id", O.Default(None))
    /** Database column instruction_text SqlType(varchar), Length(255,true), Default(None) */
    val instructionText: Rep[Option[String]] = column[Option[String]]("instruction_text", O.Length(255,varying=true), O.Default(None))
    /** Database column instruction_ordinal_number SqlType(int4), Default(None) */
    val instructionOrdinalNumber: Rep[Option[Int]] = column[Option[Int]]("instruction_ordinal_number", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Instruction */
  lazy val Instruction = new TableQuery(tag => new Instruction(tag))

  /** Entity class storing rows of table InstructionSet
   *  @param instructionSetId Database column instruction_set_id SqlType(serial), AutoInc, PrimaryKey
   *  @param instructionId Database column instruction_id SqlType(int4), Default(None)
   *  @param materialId Database column material_id SqlType(int4), Default(None)
   *  @param ingredientUsageSet Database column ingredient_usage_set SqlType(int4), Default(None)
   *  @param instructionNumber Database column instruction_number SqlType(int4), Default(None)
   *  @param ownerUserId Database column owner_user_id SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class InstructionSetRow(instructionSetId: Int, instructionId: Option[Int] = None, materialId: Option[Int] = None, ingredientUsageSet: Option[Int] = None, instructionNumber: Option[Int] = None, ownerUserId: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching InstructionSetRow objects using plain SQL queries */
  implicit def GetResultInstructionSetRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[java.sql.Timestamp]]): GR[InstructionSetRow] = GR{
    prs => import prs._
    InstructionSetRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table instruction_set. Objects of this class serve as prototypes for rows in queries. */
  class InstructionSet(_tableTag: Tag) extends profile.api.Table[InstructionSetRow](_tableTag, "instruction_set") {
    def * = (instructionSetId, instructionId, materialId, ingredientUsageSet, instructionNumber, ownerUserId, createdAt, updatedAt) <> (InstructionSetRow.tupled, InstructionSetRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(instructionSetId), instructionId, materialId, ingredientUsageSet, instructionNumber, ownerUserId, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> InstructionSetRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column instruction_set_id SqlType(serial), AutoInc, PrimaryKey */
    val instructionSetId: Rep[Int] = column[Int]("instruction_set_id", O.AutoInc, O.PrimaryKey)
    /** Database column instruction_id SqlType(int4), Default(None) */
    val instructionId: Rep[Option[Int]] = column[Option[Int]]("instruction_id", O.Default(None))
    /** Database column material_id SqlType(int4), Default(None) */
    val materialId: Rep[Option[Int]] = column[Option[Int]]("material_id", O.Default(None))
    /** Database column ingredient_usage_set SqlType(int4), Default(None) */
    val ingredientUsageSet: Rep[Option[Int]] = column[Option[Int]]("ingredient_usage_set", O.Default(None))
    /** Database column instruction_number SqlType(int4), Default(None) */
    val instructionNumber: Rep[Option[Int]] = column[Option[Int]]("instruction_number", O.Default(None))
    /** Database column owner_user_id SqlType(int4), Default(None) */
    val ownerUserId: Rep[Option[Int]] = column[Option[Int]]("owner_user_id", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table InstructionSet */
  lazy val InstructionSet = new TableQuery(tag => new InstructionSet(tag))

  /** Entity class storing rows of table Material
   *  @param materialId Database column material_id SqlType(serial), AutoInc, PrimaryKey
   *  @param materialName Database column material_name SqlType(varchar), Length(60,true), Default(None)
   *  @param materialShortName Database column material_short_name SqlType(varchar), Length(30,true), Default(None)
   *  @param materialDescription Database column material_description SqlType(varchar), Length(255,true), Default(None)
   *  @param materialStageId Database column material_stage_id SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class MaterialRow(materialId: Int, materialName: Option[String] = None, materialShortName: Option[String] = None, materialDescription: Option[String] = None, materialStageId: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MaterialRow objects using plain SQL queries */
  implicit def GetResultMaterialRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[java.sql.Timestamp]]): GR[MaterialRow] = GR{
    prs => import prs._
    MaterialRow.tupled((<<[Int], <<?[String], <<?[String], <<?[String], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table material. Objects of this class serve as prototypes for rows in queries. */
  class Material(_tableTag: Tag) extends profile.api.Table[MaterialRow](_tableTag, "material") {
    def * = (materialId, materialName, materialShortName, materialDescription, materialStageId, createdAt, updatedAt) <> (MaterialRow.tupled, MaterialRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(materialId), materialName, materialShortName, materialDescription, materialStageId, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> MaterialRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column material_id SqlType(serial), AutoInc, PrimaryKey */
    val materialId: Rep[Int] = column[Int]("material_id", O.AutoInc, O.PrimaryKey)
    /** Database column material_name SqlType(varchar), Length(60,true), Default(None) */
    val materialName: Rep[Option[String]] = column[Option[String]]("material_name", O.Length(60,varying=true), O.Default(None))
    /** Database column material_short_name SqlType(varchar), Length(30,true), Default(None) */
    val materialShortName: Rep[Option[String]] = column[Option[String]]("material_short_name", O.Length(30,varying=true), O.Default(None))
    /** Database column material_description SqlType(varchar), Length(255,true), Default(None) */
    val materialDescription: Rep[Option[String]] = column[Option[String]]("material_description", O.Length(255,varying=true), O.Default(None))
    /** Database column material_stage_id SqlType(int4), Default(None) */
    val materialStageId: Rep[Option[Int]] = column[Option[Int]]("material_stage_id", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Material */
  lazy val Material = new TableQuery(tag => new Material(tag))

  /** Entity class storing rows of table MaterialOrderHeader
   *  @param materialOrderHeaderId Database column material_order_header_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int4), Default(None)
   *  @param vendorId Database column vendor_id SqlType(int4), Default(None)
   *  @param vendorOrderId Database column vendor_order_id SqlType(varchar), Length(100,true), Default(None)
   *  @param costSubtotal Database column cost_subtotal SqlType(numeric), Default(None)
   *  @param costTax Database column cost_tax SqlType(numeric), Default(None)
   *  @param costShipping Database column cost_shipping SqlType(numeric), Default(None)
   *  @param discountTotal Database column discount_total SqlType(numeric), Default(None)
   *  @param costTotal Database column cost_total SqlType(numeric), Default(None)
   *  @param itemCount Database column item_count SqlType(int4), Default(None)
   *  @param orderDate Database column order_date SqlType(timestamp without time zone), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class MaterialOrderHeaderRow(materialOrderHeaderId: Int, userId: Option[Int] = None, vendorId: Option[Int] = None, vendorOrderId: Option[String] = None, costSubtotal: Option[scala.math.BigDecimal] = None, costTax: Option[scala.math.BigDecimal] = None, costShipping: Option[scala.math.BigDecimal] = None, discountTotal: Option[scala.math.BigDecimal] = None, costTotal: Option[scala.math.BigDecimal] = None, itemCount: Option[Int] = None, orderDate: Option[java.sql.Timestamp] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MaterialOrderHeaderRow objects using plain SQL queries */
  implicit def GetResultMaterialOrderHeaderRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[scala.math.BigDecimal]], e4: GR[Option[java.sql.Timestamp]]): GR[MaterialOrderHeaderRow] = GR{
    prs => import prs._
    MaterialOrderHeaderRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[String], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table material_order_header. Objects of this class serve as prototypes for rows in queries. */
  class MaterialOrderHeader(_tableTag: Tag) extends profile.api.Table[MaterialOrderHeaderRow](_tableTag, "material_order_header") {
    def * = (materialOrderHeaderId, userId, vendorId, vendorOrderId, costSubtotal, costTax, costShipping, discountTotal, costTotal, itemCount, orderDate, createdAt, updatedAt) <> (MaterialOrderHeaderRow.tupled, MaterialOrderHeaderRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(materialOrderHeaderId), userId, vendorId, vendorOrderId, costSubtotal, costTax, costShipping, discountTotal, costTotal, itemCount, orderDate, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> MaterialOrderHeaderRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column material_order_header_id SqlType(serial), AutoInc, PrimaryKey */
    val materialOrderHeaderId: Rep[Int] = column[Int]("material_order_header_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4), Default(None) */
    val userId: Rep[Option[Int]] = column[Option[Int]]("user_id", O.Default(None))
    /** Database column vendor_id SqlType(int4), Default(None) */
    val vendorId: Rep[Option[Int]] = column[Option[Int]]("vendor_id", O.Default(None))
    /** Database column vendor_order_id SqlType(varchar), Length(100,true), Default(None) */
    val vendorOrderId: Rep[Option[String]] = column[Option[String]]("vendor_order_id", O.Length(100,varying=true), O.Default(None))
    /** Database column cost_subtotal SqlType(numeric), Default(None) */
    val costSubtotal: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("cost_subtotal", O.Default(None))
    /** Database column cost_tax SqlType(numeric), Default(None) */
    val costTax: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("cost_tax", O.Default(None))
    /** Database column cost_shipping SqlType(numeric), Default(None) */
    val costShipping: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("cost_shipping", O.Default(None))
    /** Database column discount_total SqlType(numeric), Default(None) */
    val discountTotal: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("discount_total", O.Default(None))
    /** Database column cost_total SqlType(numeric), Default(None) */
    val costTotal: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("cost_total", O.Default(None))
    /** Database column item_count SqlType(int4), Default(None) */
    val itemCount: Rep[Option[Int]] = column[Option[Int]]("item_count", O.Default(None))
    /** Database column order_date SqlType(timestamp without time zone), Default(None) */
    val orderDate: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("order_date", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table MaterialOrderHeader */
  lazy val MaterialOrderHeader = new TableQuery(tag => new MaterialOrderHeader(tag))

  /** Entity class storing rows of table MaterialOrderLineItem
   *  @param materialOrderLineItemId Database column material_order_line_item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param materialOrderHeaderId Database column material_order_header_id SqlType(int4), Default(None)
   *  @param matrialId Database column matrial_id SqlType(int4), Default(None)
   *  @param unitCount Database column unit_count SqlType(int4), Default(None)
   *  @param unitPrice Database column unit_price SqlType(numeric), Default(None)
   *  @param unitAmount Database column unit_amount SqlType(numeric), Default(None)
   *  @param uom Database column uom SqlType(varchar), Length(50,true), Default(None)
   *  @param sku Database column sku SqlType(varchar), Length(50,true), Default(None)
   *  @param upc Database column upc SqlType(int8), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class MaterialOrderLineItemRow(materialOrderLineItemId: Int, materialOrderHeaderId: Option[Int] = None, matrialId: Option[Int] = None, unitCount: Option[Int] = None, unitPrice: Option[scala.math.BigDecimal] = None, unitAmount: Option[scala.math.BigDecimal] = None, uom: Option[String] = None, sku: Option[String] = None, upc: Option[Long] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MaterialOrderLineItemRow objects using plain SQL queries */
  implicit def GetResultMaterialOrderLineItemRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[scala.math.BigDecimal]], e3: GR[Option[String]], e4: GR[Option[Long]], e5: GR[Option[java.sql.Timestamp]]): GR[MaterialOrderLineItemRow] = GR{
    prs => import prs._
    MaterialOrderLineItemRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<?[String], <<?[String], <<?[Long], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table material_order_line_item. Objects of this class serve as prototypes for rows in queries. */
  class MaterialOrderLineItem(_tableTag: Tag) extends profile.api.Table[MaterialOrderLineItemRow](_tableTag, "material_order_line_item") {
    def * = (materialOrderLineItemId, materialOrderHeaderId, matrialId, unitCount, unitPrice, unitAmount, uom, sku, upc, createdAt, updatedAt) <> (MaterialOrderLineItemRow.tupled, MaterialOrderLineItemRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(materialOrderLineItemId), materialOrderHeaderId, matrialId, unitCount, unitPrice, unitAmount, uom, sku, upc, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> MaterialOrderLineItemRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column material_order_line_item_id SqlType(serial), AutoInc, PrimaryKey */
    val materialOrderLineItemId: Rep[Int] = column[Int]("material_order_line_item_id", O.AutoInc, O.PrimaryKey)
    /** Database column material_order_header_id SqlType(int4), Default(None) */
    val materialOrderHeaderId: Rep[Option[Int]] = column[Option[Int]]("material_order_header_id", O.Default(None))
    /** Database column matrial_id SqlType(int4), Default(None) */
    val matrialId: Rep[Option[Int]] = column[Option[Int]]("matrial_id", O.Default(None))
    /** Database column unit_count SqlType(int4), Default(None) */
    val unitCount: Rep[Option[Int]] = column[Option[Int]]("unit_count", O.Default(None))
    /** Database column unit_price SqlType(numeric), Default(None) */
    val unitPrice: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("unit_price", O.Default(None))
    /** Database column unit_amount SqlType(numeric), Default(None) */
    val unitAmount: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("unit_amount", O.Default(None))
    /** Database column uom SqlType(varchar), Length(50,true), Default(None) */
    val uom: Rep[Option[String]] = column[Option[String]]("uom", O.Length(50,varying=true), O.Default(None))
    /** Database column sku SqlType(varchar), Length(50,true), Default(None) */
    val sku: Rep[Option[String]] = column[Option[String]]("sku", O.Length(50,varying=true), O.Default(None))
    /** Database column upc SqlType(int8), Default(None) */
    val upc: Rep[Option[Long]] = column[Option[Long]]("upc", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table MaterialOrderLineItem */
  lazy val MaterialOrderLineItem = new TableQuery(tag => new MaterialOrderLineItem(tag))

  /** Entity class storing rows of table RecipeHeader
   *  @param recipeHeaderId Database column recipe_header_id SqlType(serial), AutoInc, PrimaryKey
   *  @param ownerUserId Database column owner_user_id SqlType(int4)
   *  @param instructionSetId Database column instruction_set_id SqlType(int4), Default(None)
   *  @param recipeName Database column recipe_name SqlType(varchar), Length(60,true), Default(None)
   *  @param recipeDescription Database column recipe_description SqlType(varchar), Length(255,true), Default(None)
   *  @param recipeYieldTypeId Database column recipe_yield_type_id SqlType(int4), Default(None)
   *  @param cureTime Database column cure_time SqlType(int4), Default(None)
   *  @param cureTimeUnit Database column cure_time_unit SqlType(varchar), Length(20,true), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class RecipeHeaderRow(recipeHeaderId: Int, ownerUserId: Int, instructionSetId: Option[Int] = None, recipeName: Option[String] = None, recipeDescription: Option[String] = None, recipeYieldTypeId: Option[Int] = None, cureTime: Option[Int] = None, cureTimeUnit: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching RecipeHeaderRow objects using plain SQL queries */
  implicit def GetResultRecipeHeaderRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[String]], e3: GR[Option[java.sql.Timestamp]]): GR[RecipeHeaderRow] = GR{
    prs => import prs._
    RecipeHeaderRow.tupled((<<[Int], <<[Int], <<?[Int], <<?[String], <<?[String], <<?[Int], <<?[Int], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table recipe_header. Objects of this class serve as prototypes for rows in queries. */
  class RecipeHeader(_tableTag: Tag) extends profile.api.Table[RecipeHeaderRow](_tableTag, "recipe_header") {
    def * = (recipeHeaderId, ownerUserId, instructionSetId, recipeName, recipeDescription, recipeYieldTypeId, cureTime, cureTimeUnit, createdAt, updatedAt) <> (RecipeHeaderRow.tupled, RecipeHeaderRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(recipeHeaderId), Rep.Some(ownerUserId), instructionSetId, recipeName, recipeDescription, recipeYieldTypeId, cureTime, cureTimeUnit, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> RecipeHeaderRow.tupled((_1.get, _2.get, _3, _4, _5, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column recipe_header_id SqlType(serial), AutoInc, PrimaryKey */
    val recipeHeaderId: Rep[Int] = column[Int]("recipe_header_id", O.AutoInc, O.PrimaryKey)
    /** Database column owner_user_id SqlType(int4) */
    val ownerUserId: Rep[Int] = column[Int]("owner_user_id")
    /** Database column instruction_set_id SqlType(int4), Default(None) */
    val instructionSetId: Rep[Option[Int]] = column[Option[Int]]("instruction_set_id", O.Default(None))
    /** Database column recipe_name SqlType(varchar), Length(60,true), Default(None) */
    val recipeName: Rep[Option[String]] = column[Option[String]]("recipe_name", O.Length(60,varying=true), O.Default(None))
    /** Database column recipe_description SqlType(varchar), Length(255,true), Default(None) */
    val recipeDescription: Rep[Option[String]] = column[Option[String]]("recipe_description", O.Length(255,varying=true), O.Default(None))
    /** Database column recipe_yield_type_id SqlType(int4), Default(None) */
    val recipeYieldTypeId: Rep[Option[Int]] = column[Option[Int]]("recipe_yield_type_id", O.Default(None))
    /** Database column cure_time SqlType(int4), Default(None) */
    val cureTime: Rep[Option[Int]] = column[Option[Int]]("cure_time", O.Default(None))
    /** Database column cure_time_unit SqlType(varchar), Length(20,true), Default(None) */
    val cureTimeUnit: Rep[Option[String]] = column[Option[String]]("cure_time_unit", O.Length(20,varying=true), O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table RecipeHeader */
  lazy val RecipeHeader = new TableQuery(tag => new RecipeHeader(tag))

  /** Entity class storing rows of table RecipeLineItem
   *  @param recipeLineItemId Database column recipe_line_item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param recipeHeaderId Database column recipe_header_id SqlType(int4), Default(None)
   *  @param materialId Database column material_id SqlType(int4), Default(None)
   *  @param materialAmount Database column material_amount SqlType(numeric), Default(None)
   *  @param materialAmountUom Database column material_amount_uom SqlType(varchar), Length(20,true), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class RecipeLineItemRow(recipeLineItemId: Int, recipeHeaderId: Option[Int] = None, materialId: Option[Int] = None, materialAmount: Option[scala.math.BigDecimal] = None, materialAmountUom: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching RecipeLineItemRow objects using plain SQL queries */
  implicit def GetResultRecipeLineItemRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[scala.math.BigDecimal]], e3: GR[Option[String]], e4: GR[Option[java.sql.Timestamp]]): GR[RecipeLineItemRow] = GR{
    prs => import prs._
    RecipeLineItemRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[scala.math.BigDecimal], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table recipe_line_item. Objects of this class serve as prototypes for rows in queries. */
  class RecipeLineItem(_tableTag: Tag) extends profile.api.Table[RecipeLineItemRow](_tableTag, "recipe_line_item") {
    def * = (recipeLineItemId, recipeHeaderId, materialId, materialAmount, materialAmountUom, createdAt, updatedAt) <> (RecipeLineItemRow.tupled, RecipeLineItemRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(recipeLineItemId), recipeHeaderId, materialId, materialAmount, materialAmountUom, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> RecipeLineItemRow.tupled((_1.get, _2, _3, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column recipe_line_item_id SqlType(serial), AutoInc, PrimaryKey */
    val recipeLineItemId: Rep[Int] = column[Int]("recipe_line_item_id", O.AutoInc, O.PrimaryKey)
    /** Database column recipe_header_id SqlType(int4), Default(None) */
    val recipeHeaderId: Rep[Option[Int]] = column[Option[Int]]("recipe_header_id", O.Default(None))
    /** Database column material_id SqlType(int4), Default(None) */
    val materialId: Rep[Option[Int]] = column[Option[Int]]("material_id", O.Default(None))
    /** Database column material_amount SqlType(numeric), Default(None) */
    val materialAmount: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("material_amount", O.Default(None))
    /** Database column material_amount_uom SqlType(varchar), Length(20,true), Default(None) */
    val materialAmountUom: Rep[Option[String]] = column[Option[String]]("material_amount_uom", O.Length(20,varying=true), O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table RecipeLineItem */
  lazy val RecipeLineItem = new TableQuery(tag => new RecipeLineItem(tag))

  /** Entity class storing rows of table SubstituteSet
   *  @param substituteSetId Database column substitute_set_id SqlType(serial), AutoInc, PrimaryKey
   *  @param recipeHeaderId Database column recipe_header_id SqlType(int4), Default(None)
   *  @param recipeItemId Database column recipe_item_id SqlType(int4), Default(None)
   *  @param substituteMaterialId Database column substitute_material_id SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class SubstituteSetRow(substituteSetId: Int, recipeHeaderId: Option[Int] = None, recipeItemId: Option[Int] = None, substituteMaterialId: Option[Int] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching SubstituteSetRow objects using plain SQL queries */
  implicit def GetResultSubstituteSetRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[Option[java.sql.Timestamp]]): GR[SubstituteSetRow] = GR{
    prs => import prs._
    SubstituteSetRow.tupled((<<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table substitute_set. Objects of this class serve as prototypes for rows in queries. */
  class SubstituteSet(_tableTag: Tag) extends profile.api.Table[SubstituteSetRow](_tableTag, "substitute_set") {
    def * = (substituteSetId, recipeHeaderId, recipeItemId, substituteMaterialId, createdAt, updatedAt) <> (SubstituteSetRow.tupled, SubstituteSetRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(substituteSetId), recipeHeaderId, recipeItemId, substituteMaterialId, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> SubstituteSetRow.tupled((_1.get, _2, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column substitute_set_id SqlType(serial), AutoInc, PrimaryKey */
    val substituteSetId: Rep[Int] = column[Int]("substitute_set_id", O.AutoInc, O.PrimaryKey)
    /** Database column recipe_header_id SqlType(int4), Default(None) */
    val recipeHeaderId: Rep[Option[Int]] = column[Option[Int]]("recipe_header_id", O.Default(None))
    /** Database column recipe_item_id SqlType(int4), Default(None) */
    val recipeItemId: Rep[Option[Int]] = column[Option[Int]]("recipe_item_id", O.Default(None))
    /** Database column substitute_material_id SqlType(int4), Default(None) */
    val substituteMaterialId: Rep[Option[Int]] = column[Option[Int]]("substitute_material_id", O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table SubstituteSet */
  lazy val SubstituteSet = new TableQuery(tag => new SubstituteSet(tag))

  /** Entity class storing rows of table Users
   *  @param userId Database column user_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userDisplayName Database column user_display_name SqlType(varchar), Length(60,true)
   *  @param password Database column password SqlType(varchar), Length(255,true)
   *  @param firstName Database column first_name SqlType(varchar), Length(30,true)
   *  @param lastName Database column last_name SqlType(varchar), Length(30,true)
   *  @param emailPrimary Database column email_primary SqlType(varchar), Length(60,true)
   *  @param emailSecondary Database column email_secondary SqlType(varchar), Length(60,true), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone) */
  case class UsersRow(userId: Int, userDisplayName: String, password: String, firstName: String, lastName: String, emailPrimary: String, emailSecondary: Option[String] = None, createdAt: java.sql.Timestamp, updatedAt: java.sql.Timestamp)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<?[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (userId, userDisplayName, password, firstName, lastName, emailPrimary, emailSecondary, createdAt, updatedAt) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userId), Rep.Some(userDisplayName), Rep.Some(password), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(emailPrimary), emailSecondary, Rep.Some(createdAt), Rep.Some(updatedAt))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_id SqlType(serial), AutoInc, PrimaryKey */
    val userId: Rep[Int] = column[Int]("user_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_display_name SqlType(varchar), Length(60,true) */
    val userDisplayName: Rep[String] = column[String]("user_display_name", O.Length(60,varying=true))
    /** Database column password SqlType(varchar), Length(255,true) */
    val password: Rep[String] = column[String]("password", O.Length(255,varying=true))
    /** Database column first_name SqlType(varchar), Length(30,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(30,varying=true))
    /** Database column last_name SqlType(varchar), Length(30,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(30,varying=true))
    /** Database column email_primary SqlType(varchar), Length(60,true) */
    val emailPrimary: Rep[String] = column[String]("email_primary", O.Length(60,varying=true))
    /** Database column email_secondary SqlType(varchar), Length(60,true), Default(None) */
    val emailSecondary: Rep[Option[String]] = column[Option[String]]("email_secondary", O.Length(60,varying=true), O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(timestamp without time zone) */
    val updatedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_at")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))

  /** Entity class storing rows of table Vendor
   *  @param vendorId Database column vendor_id SqlType(serial), AutoInc, PrimaryKey
   *  @param vendorName Database column vendor_name SqlType(varchar), Length(255,true), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp without time zone), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp without time zone), Default(None) */
  case class VendorRow(vendorId: Int, vendorName: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching VendorRow objects using plain SQL queries */
  implicit def GetResultVendorRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[VendorRow] = GR{
    prs => import prs._
    VendorRow.tupled((<<[Int], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table vendor. Objects of this class serve as prototypes for rows in queries. */
  class Vendor(_tableTag: Tag) extends profile.api.Table[VendorRow](_tableTag, "vendor") {
    def * = (vendorId, vendorName, createdAt, updatedAt) <> (VendorRow.tupled, VendorRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(vendorId), vendorName, createdAt, updatedAt)).shaped.<>({r=>import r._; _1.map(_=> VendorRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column vendor_id SqlType(serial), AutoInc, PrimaryKey */
    val vendorId: Rep[Int] = column[Int]("vendor_id", O.AutoInc, O.PrimaryKey)
    /** Database column vendor_name SqlType(varchar), Length(255,true), Default(None) */
    val vendorName: Rep[Option[String]] = column[Option[String]]("vendor_name", O.Length(255,varying=true), O.Default(None))
    /** Database column created_at SqlType(timestamp without time zone), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp without time zone), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Vendor */
  lazy val Vendor = new TableQuery(tag => new Vendor(tag))
}
