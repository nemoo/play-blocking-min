package controllers

import javax.inject.Inject
import models.{ProjectRepo, TaskRepo}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import com.github.takezoe.slick.blocking.BlockingPostgresDriver.blockingApi._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext

class Application @Inject()(
                            projectRepo: ProjectRepo,
                            taskRepo: TaskRepo,
                            val controllerComponents: ControllerComponents)
                           (protected val dbConfigProvider: DatabaseConfigProvider, 
                            val ex: ExecutionContext) extends BaseController {
  val DB = dbConfigProvider.get[JdbcProfile].db
//  def addTaskToProject(color: String, projectId: Long) = Action.async { implicit rs =>
//    projectRepo.addTask(color, projectId)
//      .map{ _ =>  Redirect(routes.Application.projects(projectId)) }
//  }
//
//  def modifyTask(taskId: Long, color: Option[String]) = Action.async { implicit rs =>
//    taskRepo.partialUpdate(taskId, color, None, None).map(i =>
//    Ok(s"Rows affected : $i"))
//  }
//  def createProject(name: String)= Action.async { implicit rs =>
//    projectRepo.create(name)
//      .map(id => Ok(s"project $id created") )
//  }

  def listProjects: Action[AnyContent] = Action { implicit rs =>
      DB.withSession{ implicit s =>
//        given com.github.takezoe.slick.blocking.BlockingPostgresDriver.blockingApi.Session = s
        given Session = s
        val projects = projectRepo.all
        Ok(views.html.projects(projects))
      }
  }
//
//  def projects(id: Long) = Action.async { implicit rs =>
//    for {
//      Some(project) <-  projectRepo.findById(id)
//      tasks <- taskRepo.findByProjectId(id)
//    } yield Ok(views.html.project(project, tasks))
//  }
//
//  def delete(name: String) = Action.async { implicit rs =>
//    projectRepo.delete(name).map(num => Ok(s"$num projects deleted"))
//  }

}
