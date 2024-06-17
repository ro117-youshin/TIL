package sec05.chap02.ex03;

public class Chicken {
    int no;
    String name;
    ChickenMenu[] menus;

    Chicken (int no, String name, ChickenMenu[] menus) {
        this.no = no;
        this.name = name;
        this.menus = menus;
    }

    ChickenMenu orderMenu (String name) {
        for (ChickenMenu menu : menus) {
            if (menu.name.equals(name)) { // 🔴
                return menu;
            }
        }
        return null;
    }
}