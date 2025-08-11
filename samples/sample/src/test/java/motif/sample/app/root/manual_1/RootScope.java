package motif.sample.app.root.manual_1;

import demo.Bar;

public interface RootScope {

  String foo();

  @motif.Objects
  abstract class Objects {

   public abstract Bar bar();

    public String foo(int param) {
      return String.valueOf(param);
    }
  }
}
