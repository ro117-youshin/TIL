package sec06.chap04.ex03;

public class YalcoChicken {
    static YalcoChickenMenu[] menus = YalcoChickenMenu.values();

    public void takeOrder (String menuName) {
        YalcoChickenMenu ordered = null;

        for (YalcoChickenMenu menu : menus) {
            if(menu.getName().equals(menuName)) {
                ordered = menu;
            }
        }

        if (ordered == null) {
            System.out.println("해당 메뉴가 없습니다.");
            return;
        }

        System.out.println(ordered.getPrice() + "원 입니다.");
    }
}