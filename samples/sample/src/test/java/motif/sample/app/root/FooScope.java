package motif.sample.app.root;

import motif.ScopeFactory;

@motif.Scope
interface FooScope {
    String name();

    interface Dependencies {
        String name();
    }

    class FooScopeFactory extends ScopeFactory<FooScope, Dependencies> {
    }
}
