package motif.sample.app;

@motif.Scope
interface ParentScope {
    ChildScope childScope();

    interface Dependencies {
        String name();
    }
}
