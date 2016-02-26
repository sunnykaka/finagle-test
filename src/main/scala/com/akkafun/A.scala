package com.akkafun

/**
  * Created by liubin on 2016/2/26.
  */
class A {
  def ensure1(f: => Unit): Unit = println("ensure1")

  def ensure2(f: Unit => Unit): Unit = {
    println("before invoke f in ensure2")
    f()
  }

}
