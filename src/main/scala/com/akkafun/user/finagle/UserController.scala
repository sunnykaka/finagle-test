package com.akkafun.user.finagle

import com.akkafun.user.filter.UserRequest
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Status, Version, Response, Request}
import com.twitter.util.Future

import com.akkafun.user.service.UserService

/**
  * Created by Administrator on 2016/1/14.
  */
object UserController {

  val userService = new UserService

  class FreezeBalance extends Service[UserRequest, Response] {
    override def apply(userRequest: UserRequest): Future[Response] = {
      val request = userRequest.request
      val tradeId = request.getLongParam("tradeId")
      val balance = request.getLongParam("balance")
      val remark = request.getParam("remark")

      userService.freezeBalance(userRequest.user, tradeId, balance, remark).map {r =>
        println(s"invoke freezeBalance result: $r" )
        val response = Response(Version.Http11, Status.Ok)
        response.contentString = r.toString
        response
      } rescue {
        case e =>
          println(s"invoke freezeBalance found error: $e" )
          val response = Response(Version.Http11, Status.InternalServerError)
          response.contentString = "false"
          Future.value(response)
      }
    }
  }

}
