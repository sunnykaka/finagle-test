package com.akkafun.user.service

import scala.util.Random

/**
  * Created by Administrator on 2016/1/13.
  */
class UserService {

  def freezeBalance(userId: Long, tradeId: Long, balance: Long, remark: String): Boolean = {
    println(s"调用freezeBalance, userId: $userId, tradeId: $tradeId, balance: $balance, remark: $remark")
    new Random().nextBoolean()
  }

}
