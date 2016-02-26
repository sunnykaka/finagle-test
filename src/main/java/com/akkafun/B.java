package com.akkafun;

import scala.runtime.AbstractFunction0;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;

/**
 * problem: https://groups.google.com/forum/#!topic/scala-user/3cxrabsFmAY
 * Created by liubin on 2016/2/26.
 */
public class B {

    public static void main(String[] args) {
        A a = new A();
        a.ensure1(new AbstractFunction0<BoxedUnit>() {
            @Override
            public BoxedUnit apply() {
                System.out.println("invoke ensure1 success");
                return BoxedUnit.UNIT;
            }
        });

        a.ensure2(new AbstractFunction1<BoxedUnit, BoxedUnit>() {
            @Override
            public BoxedUnit apply(BoxedUnit v1) {
                System.out.println("invoke ensure2 success");
                return BoxedUnit.UNIT;
            }
        });
    }

}
