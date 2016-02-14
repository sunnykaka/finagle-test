package com.akkafun.user.web

import java.net.InetSocketAddress

import com.akkafun.user.filter.{UserRequest, AuthFilter}
import com.twitter.finagle.Service
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder, Server}
import com.twitter.finagle.http.{Status, Request, Http, Response}
import com.twitter.util.Await
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter, FunSuite, Matchers}

/**
  * Created by liubin on 2016/2/14.
  */
class UserControllerTest extends FunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfter {

  var server: Server = _
  var client: Service[Request, Response] = _

  override protected def beforeAll(): Unit = {
    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(8081))
      .name("userserver-test")
      .build(s1)

    this.server = server

  }

  override protected def afterAll(): Unit = {
    Await.ready(server.close())
  }

  before {
    val client: Service[Request, Response] = ClientBuilder()
      .codec(Http())
      .hosts(new InetSocketAddress(8081))
      .hostConnectionLimit(1)
      .build()

    this.client = client
  }

  after {
    Await.ready(client.close())
  }



  test("normal freeze user balance") {

    val request = Request("/", ("userId", "1"), ("tradeId", "-1"), ("balance", "1000L"), ("remark", "remark"))

    val response = Await.result(client(request))
    response.status shouldBe Status.Ok
    response.contentString shouldBe "true"
  }

  test("freeze user balance failed for not enough balance ") {

    val request = Request("/", ("userId", "1"), ("tradeId", "-1"), ("balance", "2000000L"), ("remark", "remark"))

    val response = Await.result(client(request))
    response.status shouldBe Status.Ok
    response.contentString shouldBe "false"
  }


}
