package com.akkafun.user.service

import com.akkafun.user.User
import com.twitter.finagle.tracing.Trace
import com.twitter.util.Future

/**
  * Created by Administrator on 2016/1/13.
  */
class UserService {

  /**
    * 解冻金额,当user.balance >= user.frozenBalance + balance时解冻成功,返回true, 否则返回false
    * @param user
    * @param tradeId
    * @param balance
    * @param remark
    * @return
    */
  def freezeBalance(user: User, tradeId: Long, balance: Long, remark: String): Future[Boolean] = {
    Trace.record("接收UserService.freezeBalance 请求")
    Future {
      println(s"调用freezeBalance, userId: ${user.id}, tradeId: $tradeId, balance: $balance, remark: $remark")
      Trace.record("发送UserService.freezeBalance 响应")
      if(balance > 0L && user.balance - user.frozenBalance >= balance) {
        val newUser = user.copy(frozenBalance = user.frozenBalance + balance)
        setUser(newUser)
      } else false
    }
  }

  /**
    * save user to user map
    * @param user
    * @return
    */
  private def setUser(user: User): Boolean = synchronized {

    import User._
    val newUser = Option(userMap.get(user.id).fold(user){oldUser =>
      // optimistic locking
      if(user.version + 1 <= oldUser.version) null
      else user
    }).map(u => {
      val versionedUser = u.copy(version = u.version + 1)
      println(s"save user: $versionedUser")
      userMap.put(versionedUser.id, versionedUser)
      versionedUser
    })

    newUser.isDefined
  }

}
