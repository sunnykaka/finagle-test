package com.akkafun.user

import java.net.InetSocketAddress

import com.akkafun.user.filter.AuthFilter
import com.akkafun.user.finagle.UserController
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ServerBuilder, Server}
import com.twitter.finagle.http.{Http, Response, Request}

/**
  * Created by Administrator on 2016/1/14.
  */
object Main {

  def main(args: Array[String]): Unit = {

    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(8081))
      .name("userserver")
      .build(s1)


  }

}
