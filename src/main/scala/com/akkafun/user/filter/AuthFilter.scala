package com.akkafun.user.filter

import com.akkafun.user.User
import com.twitter.finagle.http.{Status, Version, Request, Response}
import com.twitter.finagle.{Filter, Service}
import com.twitter.util.Future

/**
  * Created by Administrator on 2016/1/28.
  */
class AuthFilter extends Filter[Request, Response, UserRequest, Response] {
  import User._

  override def apply(request: Request, service: Service[UserRequest, Response]): Future[Response] = {
    val userId = request.getLongParam("userId")
    userMap.get(userId).fold {
      val response = Response(Version.Http11, Status.BadRequest)
      response.contentString = s"unknown user id: $userId"
      Future.value(response)
    }(u => service(UserRequest(request, u)))
  }
}

case class UserRequest(request: Request, user: User){

}