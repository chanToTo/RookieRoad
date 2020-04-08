package com.orange.designPattern.factoryPattern;

import com.orange.utils.ClassUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author orangeC
 * @date 2019/10/18 17:42
 * @desc
 */
public class HumanFactory {
    private static HashMap<String, Human> humans = new HashMap<String, Human>();

    public static Human createHuman(Class clazz) {
        Human human = null;
        try {
//            human = (Human) Class.forName(clazz.getName()).newInstance();
            if (humans.containsKey(clazz.getSimpleName())) {
                human = humans.get(clazz.getSimpleName());
            } else {
                human = (Human) Class.forName(clazz.getName()).newInstance();      // 放到 MAP 中
                humans.put(clazz.getSimpleName(), human);

            }
        } catch (InstantiationException e) {
            System.out.println("必须指定人类的颜色");
        } catch (IllegalAccessException e) {
            System.out.println("人类定义错误！");
        } catch (ClassNotFoundException e) {
            System.out.println("指定的人类找不到！");
        }
        return human;
    }

    public static Human createHuman() {
        Human human = null;
        List<Class> conCreateHumanList = ClassUtils.getAllClassByInterface(Human.class);
        Random random = new Random();
        int rand = random.nextInt(conCreateHumanList.size());
        human = createHuman(conCreateHumanList.get(rand));
        return human;
    }
}
