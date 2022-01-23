/*
 * Copyright (c) 2018 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package motif.sample.app.application;

import com.google.common.truth.Truth;
import motif.sample.app.root.GreetingScopeImpl;
import motif.sample.app.root.GreetingScopeImpl.Dependencies;
import motif.sample.app.root.PersonEntity;
import org.junit.Test;

public class FoundationTest {

  @Test
  public void testIt() {
    Truth.assertThat(new GreetingScopeImpl(() -> new PersonEntity("tony")).greetingManager().greeting()).isEqualTo("Hello tony");

  }
}