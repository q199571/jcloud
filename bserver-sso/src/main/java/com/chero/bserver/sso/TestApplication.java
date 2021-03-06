package com.chero.bserver.sso;

import com.chero.bserver.sso.convert.MenuPO2MenuDTOConverter;
import com.chero.bserver.sso.model.pojo.domain.MenuDO;
import com.chero.bserver.sso.model.pojo.po.MenuPO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxh on 2018/4/4.
 */
public class TestApplication {

    public static void main(String[] args) {

        MenuPO menuPO = new MenuPO();
        menuPO.setComponent("das");
        List<MenuPO> menuPOList = new ArrayList<>();
        menuPOList.add(menuPO);
        MenuPO menuPO2 = new MenuPO();

        menuPO2.setComponent("e黄虚怀");
        menuPOList.add(menuPO2);
        MenuDO menuDO = MenuPO2MenuDTOConverter.INSTANCE.convert(menuPO);
        System.out.println("1单个、" + menuDO);

        MenuPO a = MenuPO2MenuDTOConverter.INSTANCE.reverse().convert(menuDO);
        List<MenuDO> menuDOList = MenuPO2MenuDTOConverter.INSTANCE.convert(menuPOList);
        System.out.println("2列表、" + menuDOList);
        System.out.println("3反向单个、" + a);
        List<MenuPO> test = MenuPO2MenuDTOConverter.INSTANCE.reverseConvert(menuDOList);
        System.out.println("4反向列表、" + test);

//        System.out.println(MenuPO2MenuDTOConverter.INSTANCE.getI() == MenuPO2MenuDTOConverter.INSTANCE.getI());
//        System.out.println(new MenuPO() == new MenuPO());
//        System.out.println(MenuPO2MenuDTOConverter.INSTANCE.getI());
//        MenuPO2MenuDTOConverter.INSTANCE.convertAll(menuPOList).forEach(single -> System.out.println(single));

    }
}
