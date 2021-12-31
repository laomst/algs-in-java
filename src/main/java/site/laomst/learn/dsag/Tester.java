package site.laomst.learn.dsag;

import org.junit.jupiter.api.Test;
import site.laomst.learn.dsag.adt.list.Array;

import java.util.ArrayList;

public class Tester {

    @Test
    public void arrayTest() {
        Array<String> stringArray = new Array<>();
        stringArray.add("123");
        System.out.println(stringArray);
    }
}
