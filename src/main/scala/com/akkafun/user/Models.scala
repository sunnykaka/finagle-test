package com.akkafun.user

import java.util.concurrent.ConcurrentHashMap

import scala.collection.JavaConverters._

/**
  * Created by Administrator on 2016/1/29.
  */
case class User(id: Long,
                name: String,
                balance: Long,
                frozenBalance: Long = 0L,
                version: Int = 0) {
}

object User {
  val userMap = new ConcurrentHashMap[Long, User](Map(
    1L -> User(id = 1, name = "foo", balance = 2000000),
    2L -> User(id = 2, name = "Carrie", balance = 10000000000L),
    3L -> User(id = 3, name = "Geralt", balance = 20000L)
  ).asJava).asScala
}