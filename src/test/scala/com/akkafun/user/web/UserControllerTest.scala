package com.akkafun.user.web

import java.net.InetSocketAddress

import com.akkafun.user.filter.AuthFilter
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Http, ListeningServer, Service}
import com.twitter.util.Await
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite, Matchers}

/**
  * Created by liubin on 2016/2/14.
  */
class UserControllerTest extends FunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfter {

  var server: ListeningServer = _
  var client: Service[Request, Response] = _

  override protected def beforeAll(): Unit = {
    val s1: Service[Request, Response] = new AuthFilter andThen new UserController.FreezeBalance

    val server = Http.server.
      withLabel("userserver-test").
      serve(new InetSocketAddress(8081), s1)

    this.server = server

  }

  override protected def afterAll(): Unit = {
    Await.result(server.close())
  }

  before {
    val client: Service[Request, Response] = Http.client
      .withLabel("userserver-test-client")
      .newService("127.0.0.1:8081")

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
