package hr.tvz.mindcheck.mindcheckapp.service;

public interface EntityService<T, V> {
    public T save(V entity);
}
