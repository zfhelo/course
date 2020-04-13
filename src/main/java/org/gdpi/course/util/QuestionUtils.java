package org.gdpi.course.util;

import org.gdpi.course.entity.SingleQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author zhf
 */
public class QuestionUtils {
    public static void upsetSingle(List<SingleQuestion> singles, Integer rule) {
        Random random = new Random(rule);
        ArrayList<String> list = new ArrayList<>(Collections.nCopies(4, ""));
        for (SingleQuestion single : singles) {
            list.set(0, single.getChoose1());
            list.set(1, single.getChoose2());
            list.set(2, single.getChoose3());
            list.set(3, single.getChoose4());

            Collections.shuffle(list, new Random(random.nextInt()));

            single.setChoose1(list.get(0));
            single.setChoose2(list.get(1));
            single.setChoose3(list.get(2));
            single.setChoose4(list.get(3));
        }
    }
}
