package com.akkafun;

import scala.Function1;
import scala.runtime.AbstractFunction1;
import scala.runtime.BoxedUnit;

/**
 * Created by liubin on 2016/2/26.
 */
public class B {

    public static void main(String[] args) {
        A a = new A();
//        a.ensure1(new scala.Function0<scala.runtime.BoxedUnit>() {
//            @Override
//            public scala.runtime.BoxedUnit apply() {
//                return scala.runtime.BoxedUnit.UNIT;
//            }
//        });

        a.ensure2(new AbstractFunction1<BoxedUnit, BoxedUnit>() {
            @Override
            public BoxedUnit apply(BoxedUnit v1) {
                System.out.println("invoke ensure2 success");
                return BoxedUnit.UNIT;
            }
        });
    }

}
