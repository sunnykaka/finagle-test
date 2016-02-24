package com.akkafun.user

import java.net.InetSocketAddress

import com.akkafun.user.filter.AuthFilter
import com.akkafun.user.web.UserController
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await

/**
  * Created by Administrator on 2016/1/14.
  */
object Main {

  def main(args: Array[String]): Unit = {

    val useZk = true

    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server = Http.server.
      withLabel("userserver").
      withTracer(ZipkinTracer.mk("192.168.99.100", 9410, DefaultStatsReceiver, 1.0f))

    if(useZk) {
      Await.result(server.serveAndAnnounce(
        "zk!127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183!/f/users!0",
        new InetSocketAddress(8081),
        s1))

    } else {
      Await.result(server.serve(
        new InetSocketAddress(8081),
        s1))
    }

//    val server: Server = ServerBuilder()
//      .codec(Http().enableTracing(enable = true))
//      .bindTo(new InetSocketAddress(8081))
//      .name("userserver")
//      .tracer(ZipkinTracer.mk("192.168.99.100", 9410, DefaultStatsReceiver, 1.0f))
////      .tracer(ConsoleTracer)
//      .build(s1)


  }

}
