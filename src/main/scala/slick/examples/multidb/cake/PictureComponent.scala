package slick.examples.multidb.cake

import scala.concurrent.ExecutionContext.Implicits.global

import slick.lifted.ProvenShape

/** A Picture contains an ID and a URL pointing to an image file */
case class Picture(url: String, id: Option[Int] = None)

/** PictureComponent provides database definitions for Picture objects */
//#outline
trait PictureComponent { this: ProfileComponent =>
  import profile.api.*
//#outline

  class Pictures(tag: Tag) extends Table[Picture](tag, "PICTURES") {
    def id                       = column[Option[Int]]("PIC_ID", O.PrimaryKey, O.AutoInc)
    private def url              = column[String]("PIC_URL")
    def * : ProvenShape[Picture] =
      (url, id) <> (Picture.tupled, Picture.unapply)
  }

  val pictures = TableQuery[Pictures]

  private val picturesAutoInc = pictures returning pictures.map(_.id)

  def insert(picture: Picture): DBIO[Picture] =
    (picturesAutoInc += picture).map(id => picture.copy(id = id))
}
