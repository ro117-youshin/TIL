package sec07.chap01.ex02;

import java.util.Objects;

public class Click {
    int x;
    int y;
    int timestamp;

    public Click(int x, int y, int timestamp) {
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
    }

//      @Override
//      public boolean equals(Object obj) {
//          if (!(obj instanceof Click)) return false;
//          return this.x == ((Click) obj).x && this.y == ((Click) obj).y;
//      }
}
