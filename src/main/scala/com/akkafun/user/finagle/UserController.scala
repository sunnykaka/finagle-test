package com.akkafun.user.finagle

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Status, Version, Response, Request}
import com.twitter.util.Future

import com.akkafun.user.service.UserService

/**
  * Created by Administrator on 2016/1/14.
  */
object UserController {

  val userService = new UserService

  class FreezeBalance extends Service[Request, Response] {
    override def apply(request: Request): Future[Response] = {
      val userId = request.getLongParam("userId")
      val tradeId = request.getLongParam("tradeId")
      val balance = request.getLongParam("balance")
      val remark = request.getParam("remark")

      val result = userService.freezeBalance(userId, tradeId, balance, remark)
      val response = Response(Version.Http11, Status.Ok)
      response.contentString = result.toString
      Future(response)
    }
  }

}
