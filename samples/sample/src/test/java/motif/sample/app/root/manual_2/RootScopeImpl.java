package motif.sample.app.root.manual_2;

import demo.Bar;

public class RootScopeImpl implements RootScope {

  private final RootScope.Objects objects = new Objects();

  private final Dependencies dependencies;

  private volatile Object bar;

  private volatile Object string;

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
    Object _bar = bar;
    if (_bar == null) {
      synchronized (this) {
        if (bar == null) {
          _bar = new Bar();
          if (_bar == null) {
            throw new NullPointerException("Factory method cannot return null");
          }
          bar = _bar;
        }
      }
    }
    return (Bar) _bar;
  }

  String string() {
    Object _string = string;
    if (_string == null) {
      synchronized (this) {
        if (string == null) {
          _string = objects.foo(integer());
          if (_string == null) {
            throw new NullPointerException("Factory method cannot return null");
          }
          string = _string;
        }
      }
    }
    return (String) _string;
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
    public Bar bar() {
      throw new UnsupportedOperationException();
    }
  }
}
