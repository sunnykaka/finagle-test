package com.akkafun.user.service

import com.akkafun.user.User
import com.twitter.util.Await
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

/**
  * Created by liubin on 2016/2/14.
  */
class UserServiceTest extends FunSuite with Matchers with BeforeAndAfter {

  var userService: UserService = _

  before {
    userService = new UserService
  }


  test("normal freeze user balance") {

    val user1 = User(1L, "张三", 10000L, 5000L)
    val user2 = User(2L, "张三", 10000L, 0L)

    Await.result(userService.freezeBalance(user1, -1L, 5000L, "")) shouldBe true
    Await.result(userService.freezeBalance(user2, -1L, 5000L, "")) shouldBe true
  }

  test("freeze user balance failed for not enough balance ") {

    val user1 = User(1L, "张三", 10000L, 5000L)
    val user2 = User(2L, "张三", 0L, 0L)

    Await.result(userService.freezeBalance(user1, -1L, 6000L, "")) shouldBe false
    Await.result(userService.freezeBalance(user2, -1L, 6000L, "")) shouldBe false
  }



}
