package motif.sample.app;

@motif.Scope
public interface ParentScopeBuilder {
    ParentScope create(@motif.Expose String name);
}
