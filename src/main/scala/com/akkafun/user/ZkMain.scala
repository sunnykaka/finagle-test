package com.akkafun.user

import java.net.InetSocketAddress

import com.akkafun.user.filter.AuthFilter
import com.akkafun.user.web.UserController
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.{Await, Return, Throw}

/**
  * Created by Administrator on 2016/1/14.
  */
object ZkMain {

  def main(args: Array[String]): Unit = {

    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server = Http.server.
      withLabel("userserver").
      withTracer(ZipkinTracer.mk("192.168.99.100", 9410, DefaultStatsReceiver, 1.0f)).
      //      withTracer(ConsoleTracer).
      serveAndAnnounce("zk!127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183!/f/users!0", new InetSocketAddress(8081), s1)

    Await.result(server)


//    val server: Server = ServerBuilder()
//      .codec(Http())
//      .bindTo(new InetSocketAddress("127.0.0.1", 8081))
//      .name("userserver")
//      .build(s1)
//
//    server.announce("zk!127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183!/f/users!0").respond {
//      case Return(a) => println(s"announcement: $a")
//      case Throw(e) => println(s"error: $e")
//    }

  }

}
