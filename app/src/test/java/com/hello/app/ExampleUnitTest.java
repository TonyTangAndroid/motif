package com.hello.app;

import static com.google.common.truth.Truth.assertThat;

import motif.ScopeFactory;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

  @Test
  public void addition_isCorrect() {
    RootScope rootScope = ScopeFactory.create(RootScope.class, new RootDependencies() {
      @Override
      public String string() {
        return "test";
      }
    });
    assertThat(rootScope).isNotNull();
  }
}