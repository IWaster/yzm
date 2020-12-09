package io.ouka.yzm.test;

import org.apache.commons.lang.math.RandomUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ouka
 * Test
 */
public class Test {

    @org.junit.Test
    public void test() {
        URL resource = this.getClass().getResource("/image/3f59039d4f4c.png");
        System.out.println(resource.getPath());
    }

    @org.junit.Test
    public void test2() {

        List<Integer> allList = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        int face1 = RandomUtils.nextInt(4);
        int face2;
        //使凸出1 与 凸出2不在同一个方向
        do {
            face2 = RandomUtils.nextInt(4);
        } while (face1 == face2);
        List<Integer> firstList = Arrays.asList(face1, face2);
        allList.removeAll(firstList);
        System.out.println(allList);
    }
}
