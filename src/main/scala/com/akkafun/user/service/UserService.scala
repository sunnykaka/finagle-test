package com.akkafun.user.service

import com.akkafun.user.User
import com.twitter.util.Future

/**
  * Created by Administrator on 2016/1/13.
  */
class UserService {

  def freezeBalance(user: User, tradeId: Long, balance: Long, remark: String): Future[Boolean] = {
    Future {
      println(s"调用freezeBalance, userId: ${user.id}, tradeId: $tradeId, balance: $balance, remark: $remark")
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
