package motif.sample.app.root.manual_1;

import demo.Bar;
import motif.internal.None;

public class RootScopeImpl implements RootScope {

  private final RootScope.Objects objects = new Objects();

  private final Dependencies dependencies;

  private volatile Object bar = None.NONE;

  private volatile Object string = None.NONE;

  public RootScopeImpl(Dependencies dependencies) {
    this.dependencies = dependencies;
  }

  @Override
  public String foo() {
    return string();
  }

  RootScope rootScope() {
    return this;
  }

  Bar bar() {
    if (bar == None.NONE) {
      synchronized (this) {
        if (bar == None.NONE) {
          bar = new Bar();
        }
      }
    }
    return (Bar) bar;
  }

  String string() {
    if (string == None.NONE) {
      synchronized (this) {
        if (string == None.NONE) {
          string = objects.foo(integer());
        }
      }
    }
    return (String) string;
  }

  int integer() {
    return dependencies.integer();
  }

  public interface Dependencies {

    /**
     * <ul>
     * Requested from:
     * <li>{@link demo.RootScope.Objects#foo(int)}</li>
     * </ul>
     */
    int integer();
  }

  private static class Objects extends RootScope.Objects {

    @Override
    Bar bar() {
      throw new UnsupportedOperationException();
    }
  }
}
