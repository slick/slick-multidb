package slick.examples.multidb.basic

/** Common functionality that needs to work with types from the DAO but in a
  * DAO-independent way.
  */
//#daohelper
class DAOHelper(val dao: DAO) {
  import dao.profile.api.*

  def restrictKey[C[_]](
      s: String,
      q: Query[DAO#Props, ?, C]
  ): Query[DAO#Props, ?, C] =
    q.filter(_.key === s)
}
//#daohelper
