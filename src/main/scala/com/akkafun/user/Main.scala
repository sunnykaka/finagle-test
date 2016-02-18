package com.akkafun.user

import java.net.InetSocketAddress

import com.akkafun.user.filter.AuthFilter
import com.akkafun.user.web.UserController
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ServerBuilder, Server}
import com.twitter.finagle.http.{Http, Response, Request}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.tracing.ConsoleTracer
import com.twitter.finagle.zipkin.thrift.ZipkinTracer

/**
  * Created by Administrator on 2016/1/14.
  */
object Main {

  def main(args: Array[String]): Unit = {

    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server: Server = ServerBuilder()
      .codec(Http().enableTracing(enable = true))
      .bindTo(new InetSocketAddress(8081))
      .name("userserver")
      .tracer(ZipkinTracer.mk("192.168.99.100", 9410, DefaultStatsReceiver, 1.0f))
//      .tracer(ConsoleTracer)
      .build(s1)


  }

}
