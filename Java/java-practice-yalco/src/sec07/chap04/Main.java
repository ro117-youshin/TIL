package sec07.chap04;

public class Main {

    public static void main(String[] args) {
        
        Swordman redSideSwordmanA = new Swordman(Side.RED);
        Knight redSideKnightA = new Knight(Side.RED);
        Knight redSideKnightB = new Knight(Side.RED);
        MagicKnight redSideMagicKnightA = new MagicKnight(Side.RED);

        Knight blueSideKnightA = new Knight(Side.BLUE);
        MagicKnight blueMagicKnightA = new MagicKnight(Side.BLUE);
        MagicKnight blueMagicKnightB = new MagicKnight(Side.BLUE);

        Horse<Swordman> avante = new Horse<>(40);
        Horse<Knight> sonata = new Horse<>(50);

        avante.setRider(redSideSwordmanA); // 🔴
        sonata.setRider(blueMagicKnightA);

        redSideSwordmanA.defaultAttack(blueSideKnightA); // 🔴
        redSideKnightA.defaultAttack(blueMagicKnightA);
        redSideKnightB.switchWeapon();
        redSideKnightB.defaultAttack(blueMagicKnightB);

        blueMagicKnightA.defaultAttack(redSideSwordmanA);
        blueMagicKnightB.lighteningAttack(new Unit[] {
                redSideKnightA,
                redSideKnightB,
                redSideMagicKnightA
        });
    }
}
