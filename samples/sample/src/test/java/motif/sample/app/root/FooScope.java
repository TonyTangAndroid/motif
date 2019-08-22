package motif.sample.app.root;

@motif.Scope
interface FooScope {
    String name();

    interface Dependencies {
        String name();
    }
}
