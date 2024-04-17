package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio
import slick.dbio.Effect.Read
import slick.jdbc.{JdbcBackend, JdbcProfile}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.github.takezoe.slick.blocking.BlockingPostgresDriver.blockingApi.*
import slick.lifted.TableQuery.Extract

case class Project(id: Long, name: String)

class ProjectRepo @Inject()(taskRepo: TaskRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {
  
  private[models] val Projects = TableQuery[ProjectsTable]

  def findById(id: Long)(using session: JdbcBackend#Session): Option[Project] =
    Projects.filter(_.id === id)
      .firstOption

//  private def _findByName(name: String): Query[ProjectsTable, Project, List] =
//    Projects.filter(_.name === name).to[List]
//
//  def findById(id: Long): Future[Option[Project]] =
//    db.run(_findById(id))
//
//  def findByName(name: String): Future[List[Project]] =
//    db.run(_findByName(name).result)
//
  def all(using session: JdbcBackend#Session): List[Project] =
    Projects.list

  def create(name: String)(using session: JdbcBackend#Session): Long = {
    val project = Project(0, name)
    (Projects returning Projects.map(_.id)).insert(project)
  }

//  def delete(name: String): Future[Int] = {
//    val query = _findByName(name)
//
//    val interaction = for {
//      projects        <- query.result
//      _               <- DBIO.sequence(projects.map(p => taskRepo._deleteAllInProject(p.id)))
//      projectsDeleted <- query.delete
//    } yield projectsDeleted
//
//    db.run(interaction.transactionally)
//  }
//
//  def addTask(color: String, projectId: Long): Future[Long] = {
//    val interaction = for {
//      Some(project) <- _findById(projectId)
//      id <- taskRepo.insert(Task(0, color, TaskStatus.ready, project.id))
//    } yield id
//
//    db.run(interaction.transactionally)
//  }
//

  private[models] class ProjectsTable(tag: Tag) extends Table[Project](tag, "project") {

    def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
    def name = column[String]("name")

    def * = (id, name) <> ((Project.apply _).tupled, Project.unapply)

  }
}